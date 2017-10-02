package com.babcock.integration.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class MessageStreamHelper {

    private static Logger logger = LoggerFactory.getLogger(MessageStreamHelper.class);

    private MessageChannels channels;

    @Autowired
    public MessageStreamHelper(MessageChannels channels) {
        this.channels = channels;
    }

    public void sendMessageToCreateUserEventTopic(String messagePayload) {
        logger.info("Sending CreateUserEvent Message ("+messagePayload+")");
        channels.createUserChannel().send(MessageBuilder.withPayload("CreateUserEvent Message ("+messagePayload+")").build());
    }
}
