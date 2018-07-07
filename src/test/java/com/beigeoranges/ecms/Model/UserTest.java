package com.beigeoranges.ecms.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    User user = new User((long) 99, "test@test.com", "asdf", "test", "test");

    @Before public void initialize(){
        user = new User((long) 99, "test@test.com", "asdf", "test", "test");
    }



    @Test
    public void getUserId() {
        assertSame((long) 99, user.getUserId());
    }

    @Test
    public void setUserId() {
        user.setUserId((long)12);
        assertSame((long)12,user.getUserId());

    }

    @Test
    public void getEmail() {
        assertEquals("test@test.com", user.getEmail());
    }

    @Test
    public void setEmail() {
        user.setEmail("test2@test2.com");
        assertEquals("test2@test2.com", user.getEmail());
    }

    @Test
    public void getEncryptedPassword() {
        assertEquals("asdf", user.getEncryptedPassword());
    }

    @Test
    public void setEncryptedPassword() {
        user.setEncryptedPassword("1234");
        assertEquals("1234", user.getEncryptedPassword());
    }

    @Test
    public void getLastName() {

        assertEquals("test",user.getLastName());
    }

    @Test
    public void setLastName() {
        user.setLastName("doe");
        assertEquals("doe",user.getLastName());
    }

    @Test
    public void getFirstName() {
        assertEquals("test",user.getFirstName());
    }

    @Test
    public void setFirstName() {
        user.setFirstName("john");
        assertEquals("john",user.getFirstName());
    }
}