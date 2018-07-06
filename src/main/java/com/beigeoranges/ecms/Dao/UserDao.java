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

import java.util.List;

@Repository
@Transactional
public class UserDao extends JdbcDaoSupport {

    @Autowired
    public UserDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public User findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
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

        String sqlcreateuser ="INSERT INTO users (email,typeofuser, first_name, last_name,encrypted_password, enabled) VALUE(?,?,?,?,?,?)";
        String email = form.getEmail();
        String password = encryptedPassword;
        String first_name = form.getFirstName();
        String last_name  = form.getLastName();
        String typeofuser = "";
        int enable = 0;


        getJdbcTemplate().update(sqlcreateuser, email,typeofuser,first_name,last_name,password,enable);
        User user = new User((long) getMaxUserId(),email,password,first_name,last_name);

        return user;
    }

    public List<User> getAllUsers(){
        String sqlgetallusers = "SELECT * FROM users";

        try {
            return getJdbcTemplate().query(sqlgetallusers, new UserMapper());
        } catch (Exception e) {
            return null;
        }
    }


    public User findUserByEmail(String email) {
        User user = new User();
        String sqlgetuserbyemail = "SELECT * FROM users WHERE email ='"+email+"'";
//        if(getJdbcTemplate().queryForObject(sqlgetuserbyemail, String.class) != null){
//            return user;
//        }
            return null;
        }


}
