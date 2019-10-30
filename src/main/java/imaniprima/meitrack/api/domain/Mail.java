package imaniprima.meitrack.api.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Mail {
    private String mailSubject;
    private String mailTemplate;
    private String mailFrom;
    private String mailFromName;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailContent;
    private String mailDate;
    private String mailContentProject;
    private String mailPassword;
    private String mailParamUsername;
    private String mailParamPassword;
    private boolean mailFromCustom;

}
