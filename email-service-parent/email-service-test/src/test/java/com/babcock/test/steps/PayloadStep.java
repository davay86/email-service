package com.babcock.test.steps;

import com.babcock.test.runtime.RuntimeState;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class PayloadStep extends AbstractStep {

    @Autowired
    RuntimeState runtimeState;

    @Given("^a valid createUserEvent payload$")
    public void a_valid_createUserEvent_payload() throws Throwable {
        runtimeState.setMessagePayload(getExamplePayload());
    }

    public String getExamplePayload() {
        return "{\"username\": \"joebloggs\",\"firstname\": \"joe\",\"lastname\": \"blogs\"}";
    }

}

