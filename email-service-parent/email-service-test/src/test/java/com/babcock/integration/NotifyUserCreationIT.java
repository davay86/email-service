package com.babcock.integration;

import com.babcock.integration.application.TestApplication;
import com.babcock.integration.asserter.WaitForHelper;
import com.babcock.integration.stream.MessageChannels;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
@EnableBinding(MessageChannels.class)
@TestPropertySource("classpath:test.properties")
public class NotifyUserCreationIT {

    @Autowired
    private MessageChannels messageChannels;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WaitForHelper waitForHelper;

    @Before
    public void before() throws InterruptedException {
        waitForHelper.waitForServices();
    }

    @Test
    public void notifyUserCreationReceiver_receives_userPayload_asExpected() throws MessagingException, IOException, InterruptedException {
        String uniqueStr = getUniqueString();

        messageChannels.publishCreateUserChannel().send(createMessage(getExamplePayload(uniqueStr)));

        waitForHelper.waitForMailMessages();

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8025/api/v1/messages", String.class);

        assertThat(response.getBody(), containsString("To: admin@test.com"));
        assertThat(response.getBody(), containsString("Subject: New User Awaiting Activation"));
        assertThat(response.getBody(), containsString("User joe"+uniqueStr+"(joe "+uniqueStr+") has been created and is awaiting activation"));
    }

    public String getExamplePayload(String uniqueStr) {
        return "{\"username\": \"joe"+uniqueStr+"\",\"firstname\": \"joe\",\"lastname\": \""+uniqueStr+"\"}";
    }

    private Message<String> createMessage(String payload) {
        return new GenericMessage<>(payload);
    }

    private String getUniqueString() {
        return RandomStringUtils.randomAlphabetic(10);
    }

}

