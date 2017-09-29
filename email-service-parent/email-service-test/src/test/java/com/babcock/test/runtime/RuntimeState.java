package com.babcock.test.runtime;

import cucumber.api.Scenario;
import org.springframework.stereotype.Component;
import org.subethamail.wiser.Wiser;

@Component
public class RuntimeState {

    private Scenario scenario;
    private Wiser smtpServer;
    private String messagePayload;

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public void initialise() {
    }

    public void setSmtpServer(Wiser smtpServer) {
        this.smtpServer = smtpServer;
    }

    public Wiser getSmtpServer() {
        return smtpServer;
    }

    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }

    public String getMessagePayload() {
        return messagePayload;
    }
}
