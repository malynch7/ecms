package com.beigeoranges.ecms.Dao;

import com.beigeoranges.ecms.Dao.RoleDao;
import com.beigeoranges.ecms.Model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RoleDaoTest {
    RoleDao roleDao;
    User user1 = new User(9L, "dbuser1@yahoo.com", "123", "db", "user");
    @Before
    public void createRoleDao(){
        roleDao= mock(RoleDao.class);
    }

    @Test
    public void getRoleNames() {
        //make sure user1 has role "admin"
        //not sure about this one
        Long id= 1L;
        List<String> resultRoles = roleDao.getRoleNames(id);
        List<String> expectedRoles= new ArrayList<>();
        expectedRoles.add("admin");
        assertEquals(expectedRoles, resultRoles);
    }
}
