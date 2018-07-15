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
    User user1 = new User(1L, "user1@test.com", "1234567890", "john", "doe");
    User user2 = new User(2L, "user2@test.com", "0987654321", "john", "smith");
    User user3 = new User(3L, "user3@test.com", "1029384756", "pam", "beezley");
    @Before
    //make sure user1, user2 and user3 are in the db
    public void createUserDao(){
        userDao= mock(UserDao.class);

    }

    @Test
    public void findUserAccount() {
        String userName = "john";
        User resultUser= userDao.findUserAccount(userName);
        User expectedUser = user1;
        assertSame("findUserAccount: result user and expected are same", resultUser, expectedUser);
    }

    @Test
    public void getMaxUserId() {
        int max= 3;
        assertEquals(max, userDao.getMaxUserId());
        //make sure you have a max id of 3 in the database or change this "max" value accordingly.
    }

    @Test
    public void createUser() {
        UserForm userForm = new UserForm(4L, "Jim", "Halpert", false, "user4@test.com", "1122334455", "1122334455", "0" );
        User expectedUser = new User(4L, "user4@test.com", "1122334455", "Jim", "Halpert");
        User resultUser = userDao.createUser(userForm);
        assertEquals(expectedUser, resultUser);

    }

    @Test
    public void getAllUsers() {

        List<User> expectedListOfUsers= new ArrayList<User>();
        expectedListOfUsers.add(user1);
        expectedListOfUsers.add(user2);
        expectedListOfUsers.add(user3);
        assertSame(expectedListOfUsers, userDao.getAllUsers());
    }

    @Test
    public void findUserByEmail() {
        String email= "user1@test.com";
        User resultUser = userDao.findUserByEmail(email);
        assertEquals(resultUser, user1);
    }

    @Test
    public void getUserIdByEmail() {
        String email= "user1@test.com";
        int resultId = userDao.getUserIdByEmail(email);
        assertEquals(1,resultId);
    }

}