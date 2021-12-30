package com.dataaccesslayer.dao.mapper;

import com.dataaccesslayer.dao.AbstractRowMapper;
import com.dataaccesslayer.entity.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper extends AbstractRowMapper<UserEntity> {

    @Override
    protected UserEntity mapRow(ResultSet rs) {
        try {
            int id = rs.getInt("id");
            String email = rs.getString("email");
            String passwd = rs.getString("passwd");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            return new UserEntity(id, email, passwd, name, surname);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
