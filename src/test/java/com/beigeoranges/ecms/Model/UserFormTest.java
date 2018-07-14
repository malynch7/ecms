package com.beigeoranges.ecms.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserFormTest {

    UserForm user = new UserForm((long) 99, "john", "doe", true, "test@test.com", "test", "test", "Admin");

    @Before
    public void initialize(){
        user = new UserForm((long) 99, "john", "doe", true, "test@test.com", "test", "test", "Admin");
    }

    @Test
    public void getUserId() {
        assertSame((long) 99, user.getUserId());
    }

    @Test
    public void setUserId() {
        user.setUserId((long) 12);
        assertSame((long) 12, user.getUserId());
    }

    @Test
    public void getFirstName() {
        assertSame("john", user.getFirstName());

    }

    @Test
    public void setFirstName() {
        user.setFirstName("chang");
        assertSame("chang", user.getFirstName());

    }

    @Test
    public void getLastName() {
        assertEquals("doe", user.getLastName());
    }

    @Test
    public void setLastName() {
        user.setLastName("moon");
        assertSame("moon", user.getLastName());

    }

    @Test
    public void isEnabled() {
        assertSame(true, user.isEnabled());
    }

    @Test
    public void setEnabled() {
        user.setEnabled(false);
        assertSame(false, user.isEnabled());

    }

    @Test
    public void getEmail() {
        assertSame("test@test.com", user.getEmail());
    }

    @Test
    public void setEmail() {
        user.setEmail("cmoon9@student.gsu.edu");
        assertSame("cmoon9@student.gsu.edu", user.getEmail());
    }

    @Test
    public void getPassword() {
        assertSame("test", user.getPassword());
    }

    @Test
    public void setPassword() {
        user.setPassword("cmoon9");
        assertSame("cmoon9", user.getPassword());

    }

    @Test
    public void getConfirmPassword() {
        assertSame("test", user.getConfirmPassword());
    }

    @Test
    public void setConfirmPassword() {
        user.setConfirmPassword("cmoon9");
        assertSame("cmoon9", user.getConfirmPassword());

    }

    @Test
    public void getAdminCode() {
        assertSame("Admin", user.getAdminCode());
    }

    @Test
    public void setAdminCode() {
        user.setAdminCode("Player");
        assertSame("Player", user.getAdminCode());

    }
}