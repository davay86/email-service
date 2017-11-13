package com.babcock.integration.asserter;

import com.noveria.assertion.exception.WaitUntilAssertionError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.fail;

@Component
@TestPropertySource("classpath:test.properties")
public class WaitForHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${email.service.url}")
    String baseUrl;

    @Value("${mail.server.url}")
    String mailServerUrl;

    private static boolean serviceUnavailable = false;

    public void waitForServices() throws InterruptedException {
        WaitForService waitForEmailService = new WaitForService(baseUrl + "/info", restTemplate);
        waitForEmailService.setMaxWaitTime(300000);

        if(serviceUnavailable) {
            fail("email-service docker environment unavailable");
        }

        System.out.println("waiting for service : " + baseUrl + "/info");

        try {
            waitForEmailService.performAssertion();
        }catch (WaitUntilAssertionError wae) {
            serviceUnavailable = true;
            fail("email-service docker environment unavailable");
        }
    }

    public void waitForMailMessages() throws InterruptedException {
        WaitForMail waitForMail = new WaitForMail(mailServerUrl+"/api/v1/messages", restTemplate);
        waitForMail.setMaxWaitTime(60000);
        waitForMail.performAssertion();
    }
}
