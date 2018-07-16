package com.beigeoranges.ecms.Dao;

import javax.sql.DataSource;

import com.beigeoranges.ecms.Mapper.UserMapper;
import com.beigeoranges.ecms.Model.User;
import com.beigeoranges.ecms.Model.UserForm;
import com.beigeoranges.ecms.Utils.EncryptedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;

@Repository
@Transactional
public class UserDao extends JdbcDaoSupport {

    @Autowired
    public UserDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public User findUserAccount(String userName) {
        String sql = UserMapper.BASE_SQL + " where u.email = ? ";

        Object[] params = new Object[] { userName };
        UserMapper mapper = new UserMapper();
        try {
            User userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int getMaxUserId(){
        return getJdbcTemplate().queryForObject("SELECT MAX(user_id) FROM users", Integer.class);

    }

    public User createUser(UserForm form){
        String encryptedPassword = EncryptedPasswordUtils.encryptPassword(form.getPassword());

        String sqlcreateuser ="INSERT INTO users (email, first_name, last_name,encrypted_password, enabled) VALUE(?,?,?,?,?)";
        String email = form.getEmail();
        String password = encryptedPassword;
        String first_name = form.getFirstName();
        String last_name  = form.getLastName();
        int enable = 0;


        getJdbcTemplate().update(sqlcreateuser, email,first_name,last_name,password,enable);
        long userId = (long) getMaxUserId();
        User user = new User(userId,email,password,first_name,last_name);

        //assign role to user_role
        String sqlAssignRole ="INSERT INTO user_role (user_id,role_id) VALUE(?,?)";
        if(!form.getAdminCode().equals("")){
            getJdbcTemplate().update(sqlAssignRole, userId, 1);
        }else{
            getJdbcTemplate().update(sqlAssignRole, userId, 2);

        }

        return user;
    }

    public int getUserIdByEmail(String username) {

        String sqlgetemail2 = "SELECT user_id FROM users WHERE email = '"+username+"'";
        return getJdbcTemplate().queryForObject(sqlgetemail2, Integer.class);

    }

    public List<User> getInvitedPlayers(int eventId){
        String sqlGetInvitedUsers = "SELECT users.* FROM users, registered_to WHERE registered_to.event_id = '" + eventId  + "' AND registered_to.user_id = users.user_id AND RSVP = 0";

        try {
            return getJdbcTemplate().query(sqlGetInvitedUsers, new UserMapper());
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> getConfirmedPlayers(int eventId){
        String sqlGetRSVPUsers = "SELECT users.* FROM users, registered_to WHERE registered_to.event_id = '" + eventId  + "' AND registered_to.user_id = users.user_id AND RSVP = 1";

        try {
            return getJdbcTemplate().query(sqlGetRSVPUsers, new UserMapper());
        } catch (Exception e) {
            return null;
        }
    }



    public void editProfile(UserForm userForm, int userId){
        String encryptedPassword = EncryptedPasswordUtils.encryptPassword(userForm.getPassword());

        String sql = "UPDATE users SET email = ?, first_name = ?, last_name = ?, encrypted_password = ? WHERE user_id = ?";
        getJdbcTemplate().update(sql, new Object[] {userForm.getEmail(), userForm.getFirstName(), userForm.getLastName(), encryptedPassword, userId});
    }
}
