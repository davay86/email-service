package com.babcock.test.helper.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

interface MessageChannels {

    String CREATE_USER_CHANNEL = "createUserChannel";

    @Output("createUserChannel")
    MessageChannel createUserChannel();

}