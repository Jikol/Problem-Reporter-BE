package com.dataaccesslayer.dao;

import com.dataaccesslayer.entity.ProblemEntity;
import com.dataaccesslayer.entity.UserEntity;

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

    default void Register(final T entity, final String operation) {
        List entitiesToPersistence = context.get(operation);
        if (entitiesToPersistence == null) {
            entitiesToPersistence = new ArrayList();
        }
        entitiesToPersistence.add(entity);
        context.put(operation, entitiesToPersistence);
    }
}
