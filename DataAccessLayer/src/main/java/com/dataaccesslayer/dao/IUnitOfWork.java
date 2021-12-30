package com.dataaccesslayer.dao;

public interface IUnitOfWork<T> {
    String INSERT = "INSERT";
    String DELETE = "DELETE";
    String MODIFY = "MODIFY";

    void RegisterNew(T entity);
    void RegisterModified(T entity);
    void RegisterDeleted(T entity);
    int Commit() throws Exception;
}
