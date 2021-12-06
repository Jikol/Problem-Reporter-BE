package com.dataaccesslayer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Database {

    private static Database instance = null;
    private Configuration connection;
    private Session session;
    private Transaction transaction = null;

    private Database() {
//        connection = new Configuration();
//        connection.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//        connection.setProperty("hibernate.connection.url", "dbc:postgresql://localhost:5432/hibernatedb");
//        connection.setProperty("hibernate.connection.username", "root");
//        connection.setProperty("hibernate.connection.password", "root");
//        connection.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        connection.setProperty("show_sql", "true");
    }
}
