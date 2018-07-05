package com.beigeoranges.ecms.Services;

import com.beigeoranges.ecms.Dao.UserDao;
import com.beigeoranges.ecms.Model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user) {
        getUserDao().createUser(user);
    }

    public List<User> fetchAllUsers() {
        return getUserDao().getAllUsers();
    }

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        UserService userService = (UserService) context.getBean("userService");

        User user = new User();
        user.setEmail("cmoon9@student.gsu.edu");
        user.setEncryptedPassword("cmoon9");
        user.setFirstName("Chang");
        user.setLastName("Moon");
        userService.addUser(user);
        System.out.println("User : " + user.getUserName() + " added successfully");

        List<User> users = userService.fetchAllUsers();
        System.out.println("List of students = " + users);
    }
}
