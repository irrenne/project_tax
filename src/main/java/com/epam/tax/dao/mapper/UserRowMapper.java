package com.epam.tax.dao.mapper;

import com.epam.tax.entities.Role;
import com.epam.tax.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet resultSet) {
        User user = new User();
        try {
            user.setLogin(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setSurname(resultSet.getString("surname"));
            user.setName(resultSet.getString("name"));
            user.setRole(Role.valueOf(resultSet.getString("role_name").toUpperCase()));
            user.setId(resultSet.getLong("id"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
