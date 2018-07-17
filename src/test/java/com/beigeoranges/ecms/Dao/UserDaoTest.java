package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Model.User;
import com.beigeoranges.ecms.Model.UserForm;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class UserDaoTest {
    UserDao userDao;
    User user1 = new User(9L, "dbuser1@yahoo.com", "123", "db", "user");
    User user2 = new User(10L, "dbuser2@yahoo.com", "123", "db", "user");
    User user3 = new User(11L, "dbuser3@yahoo.com", "123", "db", "user");
    User user4 = new User(12L, "playa@yahoo.com", "123", "playa", "playa");
    @Before
    //make sure user1, user2 and user3 nad user4are in the db
    public void createUserDao(){
        userDao= mock(UserDao.class);

    }

    @Test
    public void findUserAccount() {
        String userName = "dbuser1@yahoo.com";
        User resultUser= userDao.findUserAccount(userName);
        User expectedUser = user1;
        assertSame("findUserAccount: result user and expected are same", resultUser, expectedUser);
        //what happens when username does not exist in db?
        userName = "userdoesntexixt@yahoo.com";
        resultUser= userDao.findUserAccount(userName);
        assertSame("findUserAccount: error should happen for when user doesnt exist in db", resultUser, expectedUser);

    }

    @Test
    public void getMaxUserId() {
        int max= 12;
        assertEquals(max, userDao.getMaxUserId());
        //make sure you have a max id of 12 in the database or change this "max" value accordingly.
    }

    @Test
    public void createUser() {
        UserForm userForm = new UserForm(13L, "anotha", "playa", false, "dbuser5@yahoo.com", "123", "123", "0" );
        User expectedUser = new User(13L, "dbuser5@yahoo.com", "123", "anotha", "playa");
        User resultUser = userDao.createUser(userForm);
        assertEquals("creating a user creates inputted user properly",expectedUser, resultUser);

    }
/*
    @Test
    public void getAllUsers() {

        List<User> expectedListOfUsers= new ArrayList<User>();
        expectedListOfUsers.add(user1);
        expectedListOfUsers.add(user2);
        expectedListOfUsers.add(user3);
        expectedListOfUsers.add(user4);
        assertSame(expectedListOfUsers, userDao.getAllUsers());
        //does this execute after add new user ()? if so, will it affect result list?
    }

    @Test
    public void findUserByEmail() {
        String email= "dbuser1@yahoo.com";
        User resultUser = userDao.findUserByEmail(email);
        assertEquals(resultUser, user1);
    }
 */

    @Test
    public void getUserIdByEmail() {
        String email= "dbuser1@yahoo.com";
        int resultId = userDao.getUserIdByEmail(email);
        assertEquals(9,resultId);
    }

}