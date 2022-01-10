package com.dataaccesslayer.dao;

import com.dataaccesslayer.entity.ProblemEntity;
import com.dataaccesslayer.entity.UserEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IUnitOfWork<T> {
    public final Map<String, List<UserEntity>> context = new HashMap<>();
    public String INSERT = "INSERT";
    public String DELETE = "DELETE";
    public String MODIFY = "MODIFY";

    public void RegisterNew(T entity);
    public void RegisterModified(T entity);
    public void RegisterDeleted(T entity);
    public int Commit() throws Exception;

    public void Insert(T entity) throws SQLException;
    public void Update(T entity) throws SQLException;
    public void Delete(T entity) throws SQLException;

    default void Register(final T entity, final String operation) {
        List entitiesToPersistence = context.get(operation);
        if (entitiesToPersistence == null) {
            entitiesToPersistence = new ArrayList();
        }
        entitiesToPersistence.add(entity);
        context.put(operation, entitiesToPersistence);
    }

    default int CommitInsert() throws SQLException {
        List objectsToBePersisted = context.get(INSERT);
        int objectsInserted = 0;
        for (Object entity : objectsToBePersisted) {
            Insert((T) entity);
            objectsInserted++;
        }
        return objectsInserted;
    }

    default int CommitModify() throws SQLException {
        List objectsToBePersisted = context.get(MODIFY);
        int objectsInserted = 0;
        for (Object entity : objectsToBePersisted) {
            Update((T) entity);
            objectsInserted++;
        }
        return objectsInserted;
    }

    default int CommitDelete() throws SQLException {
        List objectsToBePersisted = context.get(DELETE);
        int objectsInserted = 0;
        for (Object entity : objectsToBePersisted) {
            Delete((T) entity);
            objectsInserted++;
        }
        return objectsInserted;
    }
}
