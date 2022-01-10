package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.dao.IUnitOfWork;
import com.dataaccesslayer.entity.ProblemEntity;

import java.sql.SQLException;
import java.util.*;

public class CrudProblemTDG implements IUnitOfWork<ProblemEntity> {
    private final Database db = Database.getDatabase();

    @Override
    public void RegisterNew(final ProblemEntity entity) {
        Register(entity, INSERT);
    }

    @Override
    public void RegisterModified(final ProblemEntity entity) {
        Register(entity, MODIFY);
    }

    @Override
    public void RegisterDeleted(final ProblemEntity entity) {
        Register(entity, DELETE);
    }

    @Override
    public int Commit() throws Exception {
        int executedStatements = 0;
        if (context == null || context.size() == 0) {
            return executedStatements;
        }
        try {
            db.BeginConnection();
            if (context.containsKey(INSERT)) {
                executedStatements += CommitInsert();
            }
            if (context.containsKey(MODIFY)) {
                executedStatements += CommitModify();
            }
            if (context.containsKey(DELETE)) {
                executedStatements += CommitDelete();
            }
            db.Commit();
            db.EndConnection();
        } catch (SQLException ex) {
            db.Rollback();
            throw new Exception("SQL " + ex.getLocalizedMessage());
        }
        return executedStatements;
    }

    @Override
    public void Insert(final ProblemEntity entity) throws SQLException {
        String query = "INSERT INTO problem(title, summary, configuration, expected_behavior, actual_behavior, context, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        var parameters = new HashMap<>();
        parameters.put(1, new AbstractMap.SimpleEntry(String.class, entity.getTitle()));
        parameters.put(2, new AbstractMap.SimpleEntry(String.class, entity.getSummary()));
        parameters.put(3, new AbstractMap.SimpleEntry(String.class, entity.getConfiguration()));
        db.ExecutePreparedUpdate(query, parameters);
    }

    @Override
    public void Update(final ProblemEntity entity) throws SQLException {

    }

    @Override
    public void Delete(final ProblemEntity entity) throws SQLException {

    }

}
