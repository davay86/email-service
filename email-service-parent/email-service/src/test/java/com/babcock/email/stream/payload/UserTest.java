package com.babcock.email.stream.payload;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    User user;

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void firstname_returnsAsExpected() {
        user.setFirstname("firstName");
        assertEquals("firstName",user.getFirstname());
    }

    @Test
    public void lastname_returnsAsExpected() {
        user.setLastname("lastName");
        assertEquals("lastName",user.getLastname());
    }

    @Test
    public void username_returnsAsExpected() {
        user.setUsername("userName");
        assertEquals("userName",user.getUsername());
    }

}