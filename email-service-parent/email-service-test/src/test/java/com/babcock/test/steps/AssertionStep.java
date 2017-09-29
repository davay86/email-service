package com.babcock.test.steps;


import com.babcock.test.runtime.RuntimeState;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class AssertionStep extends AbstractStep {

    @Autowired
    RuntimeState runtimeState;

    @Then("^an email is sent to the Administrators$")
    public void an_email_is_sent_to_the_Administrators() throws Throwable {
        assertEquals(1,runtimeState.getSmtpServer().getMessages().size());

        String recipient = runtimeState.getSmtpServer().getMessages().get(0).getMimeMessage().getRecipients(javax.mail.Message.RecipientType.TO)[0].toString();
        String subject = runtimeState.getSmtpServer().getMessages().get(0).getMimeMessage().getSubject();
        String body = runtimeState.getSmtpServer().getMessages().get(0).getMimeMessage().getContent().toString();

        assertEquals("admin@test.com",recipient);
        assertEquals("New User Awaiting Activation", subject);
        assertEquals("User joebloggs(joe blogs) has been created and is awaiting activation", body.replaceAll("\r\n", ""));
    }

}
