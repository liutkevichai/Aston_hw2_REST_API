package ru.lai.hw2_rest.services;

import jakarta.servlet.http.HttpServletRequest;
import ru.lai.hw2_rest.models.StaffAssignment;
import ru.lai.hw2_rest.repositories.Repository;
import ru.lai.hw2_rest.utils.RequestParser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaffAssignmentService implements Service<StaffAssignment> {
    private final Repository<StaffAssignment> repository;
    private static final Logger logger = Logger.getLogger(StaffAssignmentService.class.getName());

    public StaffAssignmentService(Repository<StaffAssignment> repository) {
        this.repository = repository;
    }

    @Override
    public StaffAssignment getById(int id) {
        try {
            return repository.findById(id);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "(Service) Could not find id in the database, id: " + id, e);
            throw new RuntimeException("(Service) Failed to find the id in the database: " + id, e);
        }
    }

    @Override
    public List<StaffAssignment> getAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "(Service) Could not fetch result set from database", e);
            throw new RuntimeException("(Service) Failed to fetch result set from database", e);
        }
    }

    @Override
    public int create(StaffAssignment entity) {
        try {
            return repository.save(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "(Service) Could not create a new record in database: " + entity, e);
            throw new RuntimeException("(Service) Failed to create a new record in database: " + entity, e);
        }
    }

    @Override
    public int update(StaffAssignment entity) {
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
    public StaffAssignment parseEntity(HttpServletRequest req) {
        Map<String, String> assignmentMap = RequestParser.getParameterMap(req);
        StaffAssignment staffAssignment = new StaffAssignment();
        try {
            staffAssignment.setUpWithMap(assignmentMap);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "(Service) Could not setup entity with parameter map: " + assignmentMap, e);
            throw new RuntimeException("(Service) Failed to setup entity with parameter map: " + assignmentMap, e);
        }

        return staffAssignment;
    }

}