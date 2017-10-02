package com.babcock.integration.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

public interface MessageChannels {

    String CREATE_USER_CHANNEL = "createUserChannel";

    @Output("createUserChannel")
    MessageChannel createUserChannel();

}