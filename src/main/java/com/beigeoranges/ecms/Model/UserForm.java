package com.beigeoranges.ecms.Model;

public class UserForm {

    private long userId;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String email;
    private String password;
    private String confirmPassword;

    public UserForm() {

    }

    public UserForm(long userId, String firstName, String lastName, boolean enabled, String email, String password, String confirmPassword) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}