package com.babcock.integration.asserter;

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

    private static boolean serviceUnavailable = false;

    public void waitForServices() throws InterruptedException {
        WaitForService waitForEmailService = new WaitForService(baseUrl + "/info", restTemplate);
        waitForEmailService.setMaxWaitTime(720000);

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

        WaitForService waitForRabbitMQ= new WaitForService("http://localhost:15672", restTemplate);
        waitForRabbitMQ.setMaxWaitTime(720000);
        waitForRabbitMQ.performAssertion();
    }

    public void waitForMailMessages() throws InterruptedException {
        WaitForMail waitForMail = new WaitForMail("http://localhost:8025/api/v1/messages", restTemplate);
        waitForMail.setMaxWaitTime(60000);
        waitForMail.performAssertion();
    }
}
