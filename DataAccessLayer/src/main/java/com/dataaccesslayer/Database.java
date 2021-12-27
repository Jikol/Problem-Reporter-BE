package com.dataaccesslayer;

import java.sql.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    private static Database instance = null;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    private final String url;
    private final String userName;
    private final String password;

    private Database(String driver, String hostName, String port, String database, String userName, String password) {
        this.url = "jdbc:" + driver + "://" + hostName + ":" + port + "/" + database;
        this.userName = userName;
        this.password = password;

//        connection.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//        connection.setProperty("hibernate.connection.url", "jdbc:postgresql://" + hostName + ":" + port + "/" + database);
//        connection.setProperty("hibernate.connection.username", userName);
//        connection.setProperty("hibernate.connection.password", password);
//        connection.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        connection.setProperty("show_sql", "true");
    }

    public static Database getDatabase() {
        return instance;
    }

    public static Database Create(String driver, String hostName, String port, String database, String userName, String password) {
        if (instance == null) {
            instance = new Database(driver, hostName, port, database, userName, password);
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

    public void BeginConnection() {
        try {
            if (connection == null && statement == null) {
                connection = DriverManager.getConnection(url, userName, password);
                connection.setAutoCommit(false);
                statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getCause());
        }
    }

    public void EndConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
            if (statement != null) {
                statement.close();
                statement = null;
            }
            if (preparedStatement != null) {
                preparedStatement.close();
                preparedStatement = null;
            }
        } catch (SQLException ex) {
            if (ex.getCause() == null) {
                System.out.println("Database not connected");
            }
        }
    }

    public void ExecuteUpdate(String query) throws SQLException {
        statement.executeUpdate(query);
    }

    public void ExecutePreparedUpdate(String query, Map parameters) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        parameters.forEach((index, parameterEntry) -> {
            AbstractMap.SimpleEntry<Object, Object> parameter = (AbstractMap.SimpleEntry<Object, Object>) parameterEntry;
            if (parameter.getKey().equals(Integer.class)) {
                try {
                    preparedStatement.setInt((Integer) index, (Integer) parameter.getValue());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (parameter.getKey().equals(String.class)) {
                try {
                    preparedStatement.setString((Integer) index, (String) parameter.getValue());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        preparedStatement.executeUpdate();
    }

    public ResultSet ExecuteSelect(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet ExecutePreparedSelect(String query, Map parameters) throws SQLException {
        try {
            preparedStatement = connection.prepareStatement(query);
            parameters.forEach((index, parameterEntry) -> {
                AbstractMap.SimpleEntry<Object, Object> parameter = (AbstractMap.SimpleEntry<Object, Object>) parameterEntry;
                if (parameter.getKey().equals(Integer.class)) {
                    try {
                        preparedStatement.setInt((Integer) index, (Integer) parameter.getValue());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (parameter.getKey().equals(String.class)) {
                    try {
                        preparedStatement.setString((Integer) index, (String) parameter.getValue());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            return preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getLocalizedMessage());
            return null;
        }
    }

    public void Rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void Commit() {
        try {
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
