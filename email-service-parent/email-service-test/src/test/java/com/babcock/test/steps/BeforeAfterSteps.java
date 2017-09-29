package com.babcock.test.steps;

import com.babcock.test.helper.asserter.WaitForService;
import com.babcock.test.helper.asserter.WaitUntilAssertionError;
import com.babcock.test.runtime.RuntimeState;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.subethamail.wiser.Wiser;

import static org.junit.Assert.fail;

public class BeforeAfterSteps extends AbstractStep{

    @Autowired
    RuntimeState runtimeState;

    @Autowired
    RestTemplate restTemplate;

    @Value("${email.service.url}")
    String baseUrl;

    private static boolean serviceUnavailable = false;

    private Wiser wiserSmtpServer;

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.port}")
    private int smtpPort;

    @Before
    public void setUp(Scenario scenario) throws InterruptedException {
        runtimeState.initialise();
        runtimeState.setScenario(scenario);

        wiserSmtpServer = new Wiser();
        wiserSmtpServer.setHostname(smtpHost);
        wiserSmtpServer.setPort(smtpPort);
        wiserSmtpServer.start();

        runtimeState.setSmtpServer(wiserSmtpServer);

        WaitForService waitForSecurityAdminService = new WaitForService(baseUrl + "/info", restTemplate);
        waitForSecurityAdminService.setMaxWaitTime(720000);

        if(serviceUnavailable) {
            fail("email-service docker environment unavailable");
        }

        System.out.println("waiting for service : " + baseUrl + "/info");

        try {
            waitForSecurityAdminService.performAssertion();
        }catch (WaitUntilAssertionError wae) {
            serviceUnavailable = true;
            fail("email-service docker environment unavailable");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        wiserSmtpServer.stop();
    }
}

