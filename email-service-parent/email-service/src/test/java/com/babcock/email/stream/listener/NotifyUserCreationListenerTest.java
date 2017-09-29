package com.babcock.email.stream.listener;

import com.babcock.email.stream.channel.MessageChannels;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.subethamail.wiser.Wiser;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotifyUserCreationListenerTest {

    @Autowired
    private MessageChannels messageChannels;

    @Value("${spring.mail.host}")
    private String smtpHost;

    @Value("${spring.mail.port}")
    private int smtpPort;

    private Wiser wiserSmtpServer;

    @Before
    public void setUp() {
        wiserSmtpServer = new Wiser();
        wiserSmtpServer.setHostname(smtpHost);
        wiserSmtpServer.setPort(smtpPort);
        wiserSmtpServer.start();
    }

    @After
    public void tearDown() {
        wiserSmtpServer.stop();
    }

    @Test
    public void notifyUserCreationReceiver_receives_userPayload_asExpected() throws MessagingException, IOException {
        messageChannels.notifyUserCreationChannel().send(createMessage(getExamplePayload()));

        assertEquals(1,wiserSmtpServer.getMessages().size());

        String recipient = wiserSmtpServer.getMessages().get(0).getMimeMessage().getRecipients(javax.mail.Message.RecipientType.TO)[0].toString();
        String subject = wiserSmtpServer.getMessages().get(0).getMimeMessage().getSubject();
        String body = wiserSmtpServer.getMessages().get(0).getMimeMessage().getContent().toString();

        assertEquals("admin@test.com",recipient);
        assertEquals("New User Awaiting Activation", subject);
        assertEquals("User joebloggs(joe blogs) has been created and is awaiting activation", body.replaceAll("\r\n", ""));
    }

    public String getExamplePayload() {
        return "{\"username\": \"joebloggs\",\"firstname\": \"joe\",\"lastname\": \"blogs\"}";
    }

    private Message<String> createMessage(String payload) {
        return new GenericMessage<>(payload);
    }

}

