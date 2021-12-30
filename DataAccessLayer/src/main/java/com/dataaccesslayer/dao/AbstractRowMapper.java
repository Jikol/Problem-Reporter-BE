package com.dataaccesslayer.dao;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRowMapper<T> {
    public List<T> mapResultSet(ResultSet rs) {
        List<T> objectList = new ArrayList<>();
        try {
            while(rs.next()) {
                objectList.add(mapRow(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return objectList;
    }

    public T mapResultSingle(ResultSet rs) {
        try {
            while(rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected abstract T mapRow(ResultSet rs);
}
