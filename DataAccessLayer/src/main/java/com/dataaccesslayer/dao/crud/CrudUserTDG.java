package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.dao.IUnitOfWork;
import com.dataaccesslayer.entity.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CrudUserTDG implements IUnitOfWork {
    private final Map<String, List<UserEntity>> context = new HashMap<>();
    private final Database db = Database.getDatabase();

    @Override
    public void registerNew(final Object entity) {
        register((UserEntity) entity, IUnitOfWork.INSERT);
    }

    @Override
    public void registerModified(final Object entity) {
        register((UserEntity) entity, IUnitOfWork.MODIFY);
    }

    @Override
    public void registerDeleted(final Object entity) {
        register((UserEntity) entity, IUnitOfWork.DELETE);
    }

    private void register(final UserEntity userEntity, final String operation) {
        List entitiesToPersistence = context.get(operation);
        if (entitiesToPersistence == null) {
            entitiesToPersistence = new ArrayList();
        }
        entitiesToPersistence.add(userEntity);
        context.put(operation, entitiesToPersistence);
    }

    @Override
    public int commit() throws Exception {
        int executedStatements = 0;
        if (context == null || context.size() == 0) {
            return executedStatements;
        }
        try {
            db.BeginConnection();
            if (context.containsKey(IUnitOfWork.INSERT)) {
                executedStatements += commitInsert();
            }
            if (context.containsKey(IUnitOfWork.MODIFY)) {
                executedStatements += commitModify();
            }
            if (context.containsKey(IUnitOfWork.DELETE)) {
                executedStatements += commitDelete();
            }
            db.Commit();
            db.EndConnection();
        } catch (SQLException ex) {
            db.Rollback();
            throw new Exception("SQL " + ex.getLocalizedMessage());
        }
        return executedStatements;
    }

    private int commitInsert() throws SQLException {
        List objectsToBePersisted = context.get(IUnitOfWork.INSERT);
        int objectsInserted = 0;
        for (Object userEntity : objectsToBePersisted) {
            Insert((UserEntity) userEntity);
            objectsInserted++;
        }
        return objectsInserted;
    }

    private int commitModify() {
        List objectsToBePersisted = context.get(IUnitOfWork.MODIFY);
        int objectsInserted = 0;
        for (Object userEntity : objectsToBePersisted) {
            Update((UserEntity) userEntity);
            objectsInserted++;
        }
        return objectsInserted;
    }

    private int commitDelete() {
        List objectsToBePersisted = context.get(IUnitOfWork.DELETE);
        int objectsInserted = 0;
        for (Object userEntity : objectsToBePersisted) {
            Delete((UserEntity) userEntity);
            objectsInserted++;
        }
        return objectsInserted;
    }

    private void Insert(final UserEntity userEntity) throws SQLException {
        String query = "INSERT INTO \"user\"(email, passwd, name, surname) VALUES (?, ?, ?, ?)";
        Map<Integer, AbstractMap.SimpleEntry<Object, Object>> parameters = new HashMap<>();
        parameters.put(1, new AbstractMap.SimpleEntry(String.class, userEntity.getEmail()));
        parameters.put(2, new AbstractMap.SimpleEntry(String.class, userEntity.getPasswd()));
        parameters.put(3, new AbstractMap.SimpleEntry(String.class, userEntity.getName()));
        parameters.put(4, new AbstractMap.SimpleEntry(String.class, userEntity.getSurname()));
        db.ExecutePreparedUpdate(query, parameters);
    }

    private void Update(final UserEntity userEntity) {

    }

    private void Delete(final UserEntity userEntity) {

    }

    public UserEntity selectByEmail(final String emailParam) {
        UserEntity userEntity = null;
        try {
            db.BeginConnection();
            String query = "SELECT * FROM \"user\" WHERE email LIKE ?";
            Map<Integer, AbstractMap.SimpleEntry<Object, Object>> parameters = new HashMap<>();
            parameters.put(1, new AbstractMap.SimpleEntry(String.class, emailParam));
            ResultSet rs = db.ExecutePreparedSelect(query, parameters);
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String passwd = rs.getString("passwd");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                userEntity = new UserEntity(id, email, passwd, name, surname);
            }
            rs.close();
            db.EndConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userEntity;
    }
}
