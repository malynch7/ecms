package com.beigeoranges.ecms.Dao;



import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

    @Repository
    @Transactional
    public class RoleDao extends JdbcDaoSupport {

        @Autowired
        public RoleDao(DataSource dataSource) {
            this.setDataSource(dataSource);
        }

        public List<String> getRoleNames(Long userId) {
            String sql = "Select r.role " //
                    + " from user_role ur, role r " //
                    + " where ur.role_Id = r.role_Id and ur.user_Id = ? ";

            Object[] params = new Object[] { userId };

            List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);

            return roles;
        }

        public int checkRole(int userId){
            String sqlCheckRole= "SELECT role_id FROM user_role WHERE user_id = '" + userId + "'";

            try {
                return getJdbcTemplate().queryForObject(sqlCheckRole, Integer.class);
            } catch (Exception e) {
                return 0;
            }
        }


    }
