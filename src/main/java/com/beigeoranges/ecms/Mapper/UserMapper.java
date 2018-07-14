package com.beigeoranges.ecms.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.beigeoranges.ecms.Model.User;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
    //used to translate between the user table and user objects

    public static final String BASE_SQL //
            = "Select u.user_Id, u.email,u.first_name, u.last_name, u.encrypted_password From users u ";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        int userId = rs.getInt("user_Id");
        String userName = rs.getString("email");
        String encryptedPassword = rs.getString("encrypted_password");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");

        return new User((long) userId, userName, encryptedPassword, firstName, lastName);
    }

}