package imaniprima.meitrack.api.service;


import imaniprima.meitrack.api.domain.Mail;
import imaniprima.meitrack.api.domain.Users;
import imaniprima.meitrack.api.dto.ChangePasswordDTO;
import imaniprima.meitrack.api.exception.ResourceNotFoundException;
import imaniprima.meitrack.api.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;


@Service
@Slf4j
public class ChangePasswordService {

    @Autowired
    private UsersRepository userRepo;


    @Autowired
    private Environment env;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private OtherService otherService;

    @Autowired
    private MailService mailService;

    @Transactional
    public ResponseEntity<ChangePasswordDTO> updatePassword (ChangePasswordDTO request, Long id){
        Optional<Users> resource = userRepo.findById(id);
        if (resource.isPresent()){
            Boolean isMatchPassword = bCryptPasswordEncoder.matches(request.getOldPassword(),resource.get().getPassword());
            System.err.println();
            if(isMatchPassword){
                Users user = resource.get();
                user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
                userRepo.save(user);
                request.setMessage("Change Password is succesfully");
                request.setNewPassword("*************");
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(request);
            }else{
                request.setMessage("Old Password is not match");
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(request);
            }
        }else{
            throw new ResourceNotFoundException("User", "Id", id);
        }
    }

    @Transactional
    public Users resetPassword (String email){
        Optional<Users> response = userRepo.findByEmail(email);
        if(response.isPresent()){
            String randomPass = alphaNumericString(6);
            response.get().setPassword(bCryptPasswordEncoder.encode(randomPass));
            userRepo.save(response.get());
            if(response.get().getId()!=null){
                Mail mailContent = new Mail();
                mailContent.setMailTo(email);
                mailContent.setMailSubject("Meitrack - Reset Password Verification");
                mailContent.setMailTemplate("mailResetPasswordTemplate");
                mailContent.setMailParamUsername(response.get().getUsername());
                mailContent.setMailParamPassword(randomPass);
                mailService.send(mailContent);
                return response.get();
            }else{
                return null;
            }

        }else{
            throw new ResourceNotFoundException("Users", "Users email", email);
        }
    }

//    @Transactional
//    public UserRegister forgotPassword (String username) throws UnsupportedEncodingException {
//        Optional<UserRegister> resource = userRegisterRepo.findByUsername(username);
//        if (!resource.isPresent())
//            throw new ResourceNotFoundException("User", "Username", username);
//
//        UserRegister user = resource.get();
//        if(user.getId()!=null){
//            user.setMailSubject("M2 Prime - Forgotten Password Verification");
//            user.setMailTemplate("mailForgotPasswordTemplate");
//            String usernameEncode = otherService.encode(user.getUsername());
//
//            user.setLinkUrl(env.getProperty("server-fe")+"/"+usernameEncode+"/change-password");
//            mailService.send(user.getEmail(), user);
//            return user;
//        }else{
//            return null;
//        }
//    }

    @Transactional
    public static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}

