package com.babcock.email.stream.listener;

import com.babcock.email.service.EmailService;
import com.babcock.email.stream.channel.MessageChannels;
import com.babcock.email.stream.payload.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(MessageChannels.class)
public class NotifyUserCreationListener {

    private static Logger logger = LoggerFactory.getLogger(NotifyUserCreationListener.class);

    @HystrixCommand
    @StreamListener(MessageChannels.NOTIFY_USER_CREATION_CHANNEL)
    public void notifyUserCreationReceiver(User userPayload) {
        logger.info("UserPayload Received : "+ userPayload);
        emailService.sendUserCreationEmail(userPayload);
    }

    @Autowired
    EmailService emailService;
}
