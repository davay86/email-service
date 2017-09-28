package com.babcock.email.service;

import com.babcock.email.stream.payload.User;
import com.babcock.email.stream.payload.UserPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendUserCreationEmail(UserPayload userPayload) {

        for(User user : userPayload.getUsers()) {
            logger.info("Sending Email : "+ user);
        }

    }
}
