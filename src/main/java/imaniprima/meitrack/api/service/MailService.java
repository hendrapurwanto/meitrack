package imaniprima.meitrack.api.service;

import imaniprima.meitrack.api.domain.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private Environment env;

    @Autowired
    public MailService(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    private TemplateEngine templateEngine;

    public Boolean send (Mail mail) {
        MimeMessagePreparator messagePreparator = msg -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
            /*msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("Content-Transfer-Encoding", "8bit");*/
            //Set Alias from name
            if(mail.getMailFromName()!=null){
                if(mail.getMailFrom()==null){
                    mail.setMailFrom(env.getProperty("smtp-username"));
                }
                messageHelper.setFrom(mail.getMailFrom(),mail.getMailFromName());
            }else{
                messageHelper.setFrom(env.getProperty("smtp-username"),env.getProperty("smtp-fromname"));
            }
            String mailCc = mail.getMailCc()!=null?mail.getMailCc():"";
            String mailBcc = mail.getMailBcc()!=null?mail.getMailBcc():"";
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getMailTo()));
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailCc));
            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailBcc));
            msg.setSubject(mail.getMailSubject(),"UTF-8");

            String content = build(mail);
            messageHelper.setText(content, true);
        };

        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            String smtpUsername = env.getProperty("smtp-username");
            String smtpPassword = env.getProperty("smtp-password");
            if(mail.isMailFromCustom()){
                smtpUsername = mail.getMailFrom();
                smtpPassword = mail.getMailPassword();
            }

            mailSender.setHost(env.getProperty("smtp-host"));
            mailSender.setPort(Integer.parseInt(env.getProperty("smtp-port")));
            mailSender.setUsername(smtpUsername);
            mailSender.setPassword(smtpPassword);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            mailSender.send(messagePreparator);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String build (Mail mailContent) {
        Context context = new Context();
        context.setVariable("content", mailContent);
        return templateEngine.process(mailContent.getMailTemplate(), context);
    }
}
