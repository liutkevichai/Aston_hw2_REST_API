package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.utils.DataSourceConfig;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;;

public abstract class Repository {
    private static final Logger logger = Logger.getLogger(Repository.class.getName());

    public Connection getConnection() {
        try {
            return DataSourceConfig.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not connect to the database", e);
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public abstract <T> T findById(int id);
    public abstract <T> List<T> findAll();
    public abstract <T> void create(T entity);
    public abstract <T> void update(T entity);
    public abstract <T> void delete(T entity);
}
