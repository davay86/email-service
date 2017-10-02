package com.babcock.email.service;

import com.babcock.email.stream.payload.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.subethamail.wiser.Wiser;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

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
        emailService.sendUserCreationEmail(getExamplePayload());

        assertEquals(1,wiserSmtpServer.getMessages().size());

        String recipient = wiserSmtpServer.getMessages().get(0).getMimeMessage().getRecipients(javax.mail.Message.RecipientType.TO)[0].toString();
        String subject = wiserSmtpServer.getMessages().get(0).getMimeMessage().getSubject();
        String body = wiserSmtpServer.getMessages().get(0).getMimeMessage().getContent().toString();

        assertEquals("admin@test.com",recipient);
        assertEquals("New User Awaiting Activation", subject);
        assertEquals("User joebloggs(joe blogs) has been created and is awaiting activation", body.replaceAll("\r\n", ""));
    }

    public User getExamplePayload() {
        User user = new User();
        user.setUsername("joebloggs");
        user.setFirstname("joe");
        user.setLastname("blogs");

        return user;
    }


}