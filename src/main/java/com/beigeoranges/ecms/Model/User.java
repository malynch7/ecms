package com.beigeoranges.ecms.Model;


public class User {

    private int userId;
    private String userName;
    private String encryptedPassword;
    private String firstName;
    private String lastName;

    public User() {

    }

    public User(int userId, String email, String encryptedPassword, String firstName, String lastName) {
        this.userId = userId;
        this.userName = email;
        this.encryptedPassword = encryptedPassword;
        this.firstName = firstName;

        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.userName = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return this.userName + "/" + this.encryptedPassword;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
