package com.babcock.email.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannels {

    String NOTIFY_USER_CREATION_CHANNEL = "notifyUserCreationChannel";

    @Input("notifyUserCreationChannel")
    SubscribableChannel notifyUserCreationChannel();
}
