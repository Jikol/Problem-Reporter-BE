package com.dataaccesslayer.dao.crud;

import com.dataaccesslayer.Database;
import com.dataaccesslayer.dao.IUnitOfWork;
import com.dataaccesslayer.dao.mapper.UserMapper;
import com.dataaccesslayer.entity.UserEntity;

import java.sql.SQLException;
import java.util.*;

public class CrudUserTDG implements IUnitOfWork<UserEntity> {
    private final Database db = Database.getDatabase();

    @Override
    public void RegisterNew(final UserEntity entity) {
        Register(entity, INSERT);
    }

    @Override
    public void RegisterModified(final UserEntity entity) {
        Register(entity, MODIFY);
    }

    @Override
    public void RegisterDeleted(final UserEntity entity) {
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
    public void Insert(final UserEntity userEntity) throws SQLException {
        String query = "INSERT INTO \"user\"(email, passwd, name, surname) VALUES (?, ?, ?, ?)";
        var parameters = new HashMap<>();
        parameters.put(1, new AbstractMap.SimpleEntry(String.class, userEntity.getEmail()));
        parameters.put(2, new AbstractMap.SimpleEntry(String.class, userEntity.getPasswd()));
        parameters.put(3, new AbstractMap.SimpleEntry(String.class, userEntity.getName()));
        parameters.put(4, new AbstractMap.SimpleEntry(String.class, userEntity.getSurname()));
        db.ExecutePreparedUpdate(query, parameters);
    }

    @Override
    public void Update(final UserEntity userEntity) {}

    @Override
    public void Delete(final UserEntity userEntity) {}

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
