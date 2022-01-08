package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.dao.IUnitOfWork;
import com.dataaccesslayer.dao.mapper.UserMapper;
import com.dataaccesslayer.entity.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CrudUserTDG implements IUnitOfWork {
    private final Database db = Database.getDatabase();

    @Override
    public void RegisterNew(final Object entity) {
        Register((UserEntity) entity, IUnitOfWork.INSERT);
    }

    @Override
    public void RegisterModified(final Object entity) {
        Register((UserEntity) entity, IUnitOfWork.MODIFY);
    }

    @Override
    public void RegisterDeleted(final Object entity) {
        Register((UserEntity) entity, IUnitOfWork.DELETE);
    }

    @Override
    public int Commit() throws Exception {
        int executedStatements = 0;
        if (context == null || context.size() == 0) {
            return executedStatements;
        }
        try {
            db.BeginConnection();
            if (context.containsKey(IUnitOfWork.INSERT)) {
                executedStatements += CommitInsert();
            }
            if (context.containsKey(IUnitOfWork.MODIFY)) {
                executedStatements += CommitModify();
            }
            if (context.containsKey(IUnitOfWork.DELETE)) {
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

    private int CommitInsert() throws SQLException {
        List objectsToBePersisted = context.get(IUnitOfWork.INSERT);
        int objectsInserted = 0;
        for (Object userEntity : objectsToBePersisted) {
            Insert((UserEntity) userEntity);
            objectsInserted++;
        }
        return objectsInserted;
    }

    private int CommitModify() {
        List objectsToBePersisted = context.get(IUnitOfWork.MODIFY);
        int objectsInserted = 0;
        for (Object userEntity : objectsToBePersisted) {
            Update((UserEntity) userEntity);
            objectsInserted++;
        }
        return objectsInserted;
    }

    private int CommitDelete() {
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
        var parameters = new HashMap<>();
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

    public UserEntity SelectByEmail(final String emailParam) {
        db.BeginConnection();
        String query = "SELECT * FROM \"user\" WHERE email LIKE ?";
        var parameters = new HashMap<>();
        parameters.put(1, new AbstractMap.SimpleEntry(String.class, emailParam));
        UserMapper userMapper = new UserMapper();
        UserEntity userEntity = userMapper.mapResultSingle(db.ExecutePreparedSelect(query, parameters));
        db.EndConnection();
        return userEntity;
    }

    public List<UserEntity> SelectAll() {
        db.BeginConnection();
        String query = "SELECT * FROM \"user\"";
        UserMapper userMapper = new UserMapper();
        List<UserEntity> userEntities = userMapper.mapResultSet(db.ExecuteSelect(query));
        db.EndConnection();
        return userEntities;
    }
}
