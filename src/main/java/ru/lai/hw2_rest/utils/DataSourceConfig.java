package ru.lai.hw2_rest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceConfig {
    private static final String DB_URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        PropertiesUtil pu = PropertiesUtil.fromFile("config.properties");
        DB_URL = pu.readProperty("url");
        USER = pu.readProperty("user");
        PASSWORD = pu.readProperty("password");
    }

    private DataSourceConfig() {}

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found!", e);
        }
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

}
