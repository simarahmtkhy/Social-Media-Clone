package com.socialmediaclone.socialmediaclone.services;

import com.socialmediaclone.socialmediaclone.utilities.EmailBody;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendMail(String mail, String token) {
        String body = "Please verify your account \n" +
                "http://localhost:8080/users/" + token;
        EmailBody emailBody = new EmailBody(mail, body, "Verify Account");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("socialclone@example.com");
        mailMessage.setTo(emailBody.getRecipient());
        mailMessage.setText(emailBody.getMsgBody());
        mailMessage.setSubject(emailBody.getSubject());
        javaMailSender.send(mailMessage);
    }
}
