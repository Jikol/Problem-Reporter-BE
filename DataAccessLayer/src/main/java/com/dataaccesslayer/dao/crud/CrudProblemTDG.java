package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.dao.IUnitOfWork;
import com.dataaccesslayer.dao.mapper.ProblemMapper;
import com.dataaccesslayer.dao.mapper.UserMapper;
import com.dataaccesslayer.entity.ProblemEntity;

import java.sql.SQLException;
import java.util.*;

public class CrudProblemTDG implements IUnitOfWork<ProblemEntity> {
    public final Map<String, List<ProblemEntity>> context = new HashMap<>();
    private final Database db = Database.getDatabase();
    private List<Integer> insertedIds = new ArrayList<>();

    public List<Integer> getInsertedIds() {
        return insertedIds;
    }

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
    public int Commit(final boolean makeDatabaseCommit) throws Exception {
        int executedStatements = 0;
        if (context == null || context.size() == 0) {
            return executedStatements;
        }
        try {
            db.BeginConnection();
            if (context.containsKey(INSERT)) {
                var simpleEntry = CommitInsert();
                executedStatements += (Integer) simpleEntry.getKey();
                insertedIds = (List) simpleEntry.getValue();
            }
            if (context.containsKey(MODIFY)) {
                executedStatements += CommitModify();
            }
            if (context.containsKey(DELETE)) {
                executedStatements += CommitDelete();
            }
            if (makeDatabaseCommit) {
                db.Commit();
                db.EndConnection();
            }
        } catch (SQLException ex) {
            db.Rollback();
            throw new Exception("SQL " + ex.getLocalizedMessage());
        }
        return executedStatements;
    }

    @Override
    public int Insert(final ProblemEntity entity) throws SQLException {
        String query = "INSERT INTO data.problem (title, summary, configuration, expect_behavior, actual_behavior, user_id, deployment_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        var parameters = new HashMap<>();
        parameters.put(1, new AbstractMap.SimpleEntry(String.class, entity.getTitle()));
        parameters.put(2, new AbstractMap.SimpleEntry(String.class, entity.getSummary()));
        parameters.put(3, new AbstractMap.SimpleEntry(String.class, entity.getConfiguration()));
        parameters.put(4, new AbstractMap.SimpleEntry(String.class, entity.getExpectedBehavior()));
        parameters.put(5, new AbstractMap.SimpleEntry(String.class, entity.getActualBehavior()));
        parameters.put(6, new AbstractMap.SimpleEntry(Integer.class, entity.getUserEntity().getId()));
        parameters.put(7, new AbstractMap.SimpleEntry(Integer.class, entity.getDeploymentEntity().getId()));
        return db.ExecutePreparedUpdate(query, parameters);
    }

    @Override
    public void Update(final ProblemEntity entity) throws SQLException {

    }

    @Override
    public void Delete(final ProblemEntity entity) throws SQLException {

    }

    public List<ProblemEntity> SelectAll(final String emailParam) throws Exception {
        db.BeginConnection();
        String query = "SELECT *\n" +
                "FROM data.problem\n" +
                "WHERE EXISTS (\n" +
                "    SELECT id\n" +
                "    FROM data.\"user\"\n" +
                "    WHERE problem.user_id = \"user\".id AND email LIKE ?'\n" +
                ")";
        var parameters = new HashMap<>();
        parameters.put(1, new AbstractMap.SimpleEntry(String.class, emailParam));
        return new ProblemMapper().mapResultSet(db, db.ExecutePreparedSelect(query, parameters));
    }

    private void Register(final ProblemEntity entity, final String operation) {
        List entitiesToPersistence = context.get(operation);
        if (entitiesToPersistence == null) {
            entitiesToPersistence = new ArrayList();
        }
        entitiesToPersistence.add(entity);
        context.put(operation, entitiesToPersistence);
    }

    private AbstractMap.SimpleEntry CommitInsert() throws SQLException {
        var objectsToBePersisted = context.get(INSERT);
        List<Integer> insertedIds = new ArrayList<>();
        int objectsInserted = 0;
        for (ProblemEntity entity : objectsToBePersisted) {
            insertedIds.add(Insert(entity));
            objectsInserted++;
        }
        return new AbstractMap.SimpleEntry(objectsInserted, insertedIds);
    }

    private int CommitModify() throws SQLException {
        var objectsToBePersisted = context.get(MODIFY);
        int objectsInserted = 0;
        for (ProblemEntity entity : objectsToBePersisted) {
            Update(entity);
            objectsInserted++;
        }
        return objectsInserted;
    }

    private int CommitDelete() throws SQLException {
        var objectsToBePersisted = context.get(DELETE);
        int objectsInserted = 0;
        for (ProblemEntity entity : objectsToBePersisted) {
            Delete(entity);
            objectsInserted++;
        }
        return objectsInserted;
    }
}
