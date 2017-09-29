package com.babcock.test.steps;

import com.babcock.test.helper.stream.MessageStreamHelper;
import com.babcock.test.runtime.RuntimeState;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class SendMessageStep extends AbstractStep {

    @Autowired
    RuntimeState runtimeState;

    @Autowired
    MessageStreamHelper messageStreamHelper;

    @When("^the payload is sent to the createUserEvent topic$")
    public void the_payload_is_sent_to_the_createUserEvent_topic() throws Throwable {
        messageStreamHelper.sendMessageToCreateUserEventTopic(runtimeState.getMessagePayload());
    }

}

