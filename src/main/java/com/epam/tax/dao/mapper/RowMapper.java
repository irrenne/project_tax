package com.epam.tax.dao.mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {
    T mapRow(ResultSet resultSet);
}

