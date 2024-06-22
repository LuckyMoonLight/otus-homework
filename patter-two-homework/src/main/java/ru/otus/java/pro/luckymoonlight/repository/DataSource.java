package ru.otus.java.pro.luckymoonlight.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {
    private Connection connection;
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "1234";

    private static DataSource dataSourceInstance;

    private DataSource() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:db;INIT=RUNSCRIPT FROM 'classpath:init_db.sql';", USER_NAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public static synchronized DataSource getInstance() {
        return dataSourceInstance == null ? new DataSource() : dataSourceInstance;
    }

}
