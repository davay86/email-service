package com.babcock.email.service;

import com.babcock.email.stream.payload.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${user.admin.email}")
    private String userAdminEmail;

    @Value("${new.user.email.subject}")
    private String newUserEmailSubject;

    @Value("${new.user.email.body}")
    private String newUserEmailBody;

    @Autowired
    private JavaMailSender emailSender;

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendUserCreationEmail(User userPayload) {
            sendMessageToUserAdmin(userPayload);
    }

    private void sendMessageToUserAdmin(User user) {
        sendSimpleMessage(userAdminEmail,newUserEmailSubject,getNewUserEmailBody(user));
    }

    private String getNewUserEmailBody(User user) {
        return String.format(newUserEmailBody, user.getUsername(),user.getFirstname(),user.getLastname());
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        logger.info("Sending Email\n to: {}\n subject: {}\n body: {}",to,subject,text);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }
}
