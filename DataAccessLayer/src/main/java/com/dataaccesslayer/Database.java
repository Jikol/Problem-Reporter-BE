package com.dataaccesslayer;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Database {

    private static Database instance = null;
    private Configuration connection;
    private Session session;
    private Transaction transaction = null;

    private Database() {
        connection = new Configuration();
        connection.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        connection.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/my_db");
        connection.setProperty("hibernate.connection.username", "postgres");
        connection.setProperty("hibernate.connection.password", "postgres");
        connection.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        connection.setProperty("show_sql", "true");
    }

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

    public static Database Create() {
        if (instance == null) {
            instance = new Database();
            return instance;
        } else {
            return instance;
        }
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
        Create();
        try {
            session = connection.buildSessionFactory().openSession();
        } catch (SessionException ex) {
            System.out.println(ex.getCause());
        }
    }

    public void Close() {
        try {
            session.close();
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
            System.out.println(ex.getLocalizedMessage());
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
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public void Rollback() {
        transaction.rollback();
    }

    @Override
    public String toString() {
        return """
                Database
                connection: %s
                session: %s
                transaction: %s
                """.formatted(connection, session, transaction);
    }
}
