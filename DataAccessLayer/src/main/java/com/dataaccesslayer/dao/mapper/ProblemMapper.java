package com.dataaccesslayer.dao.mapper;

import com.dataaccesslayer.dao.AbstractRowMapper;
import com.dataaccesslayer.entity.ProblemEntity;
import com.dataaccesslayer.entity.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProblemMapper extends AbstractRowMapper<ProblemEntity> {

    public ProblemMapper() {
        super(ProblemEntity.class);
    }

    @Override
    protected ProblemEntity mapRow(ResultSet rs) throws Exception {
        try {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String summary = rs.getString("summary");
            String configuration = rs.getString("configuration");
            String expectedBehavior = rs.getString("expect_behavior");
            String actualBehavior = rs.getString("actual_behavior");
            return new ProblemEntity(id, title, summary, configuration, expectedBehavior, actualBehavior);
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }
}
