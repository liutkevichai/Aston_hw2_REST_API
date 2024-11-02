package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.utils.DataSourceConfig;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;;

public abstract class Repository<T> {
    private static final Logger logger = Logger.getLogger(Repository.class.getName());

    public Connection getConnection() {
        try {
            return DataSourceConfig.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not connect to database", e);
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public abstract T findById(int id) throws SQLException;

    public abstract List<T> findAll() throws SQLException;

    public abstract void save(T entity) throws SQLException;

    public abstract void update(T entity) throws SQLException;

    public abstract void delete(T entity) throws SQLException;
}
