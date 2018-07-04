package com.beigeoranges.ecms.Dao;

import javax.sql.DataSource;

import com.beigeoranges.ecms.Mapper.UserMapper;
import com.beigeoranges.ecms.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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


}
