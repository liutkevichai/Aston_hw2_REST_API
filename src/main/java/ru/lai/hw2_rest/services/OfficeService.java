package ru.lai.hw2_rest.services;

import jakarta.servlet.http.HttpServletRequest;
import ru.lai.hw2_rest.models.Office;
import ru.lai.hw2_rest.repositories.Repository;
import ru.lai.hw2_rest.utils.RequestParser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OfficeService implements Service<Office> {
    private final Repository<Office> repository;
    private static final Logger logger = Logger.getLogger(OfficeService.class.getName());

    public OfficeService(Repository<Office> repository) {
        this.repository = repository;
    }

    @Override
    public Office getById(int id) {
        try {
            return repository.findById(id);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "(Service) Could not find id in the database, id: " + id, e);
            throw new RuntimeException("(Service) Failed to find the id in the database: " + id, e);
        }
    }

    @Override
    public List<Office> getAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "(Service) Could not fetch result set from database", e);
            throw new RuntimeException("(Service) Failed to fetch result set from database", e);
        }
    }

    @Override
    public int create(Office entity) {
        try {
            return repository.save(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "(Service) Could not create a new record in database: " + entity, e);
            throw new RuntimeException("(Service) Failed to create a new record in database: " + entity, e);
        }
    }

    @Override
    public int update(Office entity) {
        try {
            return repository.update(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "(Service) Could not update a record in database: " + entity, e);
            throw new RuntimeException("(Service) Failed to update a record in database: " + entity, e);
        }

    }

    @Override
    public int delete(int id) {
        try {
            return repository.delete(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "(Service) Could not delete the record from database, id: " + id, e);
            throw new RuntimeException("(Service) Failed to delete the record from database, id: " + id, e);
        }
    }

    @Override
    public Office parseEntity(HttpServletRequest req) {
        Map<String, String> officeMap = RequestParser.getParameterMap(req);
        Office office = new Office();
        try {
            office.setUpWithMap(officeMap);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "(Service) Could not setup entity with parameter map: " + officeMap, e);
            throw new RuntimeException("(Service) Failed to setup entity with parameter map: " + officeMap, e);
        }

        return office;
    }

}
