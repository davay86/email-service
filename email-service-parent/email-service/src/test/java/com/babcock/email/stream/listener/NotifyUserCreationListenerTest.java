package com.babcock.email.stream.listener;

import com.babcock.email.stream.channel.MessageChannels;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotifyUserCreationListenerTest {

    @Autowired
    private MessageChannels messageChannels;

    @Test
    public void notifyUserCreationReceiver_receives_userPayload_asExpected() {
        messageChannels.notifyUserCreationChannel().send(createMessage(getExamplePayload()));
    }

    public String getExamplePayload() {
        return "{\"users\" : [{\"username\": \"joebloggs\",\"firstname\": \"joe\",\"lastname\": \"blogs\"}]}";
    }

    private Message<String> createMessage(String payload) {
        return new GenericMessage<>(payload);
    }

}

