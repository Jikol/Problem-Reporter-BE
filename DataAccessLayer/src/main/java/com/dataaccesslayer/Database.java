package com.dataaccesslayer;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Database {

    private static Database instance = null;
    private Configuration connection;
    private Session session = null;
    private Transaction transaction = null;

    private Database(String hostName, String port, String database, String userName, String password) {
        connection = new Configuration();
        connection.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        connection.setProperty("hibernate.connection.url", "jdbc:postgresql://" + hostName + ":" + port + "/" + database);
        connection.setProperty("hibernate.connection.username", userName);
        connection.setProperty("hibernate.connection.password", password);
        connection.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        connection.setProperty("show_sql", "true");
    }

    public static Database getDatabase() {
        return instance;
    }

    public Session getSession() {
        return session;
    }

    public static Database Create(String hostName, String port, String database, String userName, String password) {
        if (instance == null) {
            instance = new Database(hostName, port, database, userName, password);
            return instance;
        } else {
            return instance;
        }
    }

    public static void Destroy() {
        if (instance != null) {
            instance = null;
        }
    }

    public void Connect() {
        try {
            if (session == null) {
                session = connection.buildSessionFactory().openSession();
            }
        } catch (SessionException ex) {
            System.out.println(ex.getCause());
        }
    }

    public void Disconnect() {
        try {
            if (session != null) {
                session.close();
                session = null;
            }
        } catch (Exception ex) {
            if (ex.getCause() == null) {
                System.out.println("Database not connected");
            }
        }
    }

    public int ExecuteQuery(Query query) {
        int rows = 0;
        try {
            rows = query.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            Rollback();
        }
        return rows;
    }

    public void BeginTransaction() {
        transaction = session.beginTransaction();
    }

    public void EndTransaction() {
        try {
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                Rollback();
            }
            ex.printStackTrace();
        }
    }

    public void Rollback() {
        transaction.rollback();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Database{");
        sb.append("connection=").append(connection);
        sb.append(", session=").append(session);
        sb.append(", transaction=").append(transaction);
        sb.append('}');
        return sb.toString();
    }
}
