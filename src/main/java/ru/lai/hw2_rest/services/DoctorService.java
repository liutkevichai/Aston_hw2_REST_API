package ru.lai.hw2_rest.services;

import jakarta.servlet.http.HttpServletRequest;
import ru.lai.hw2_rest.models.Doctor;
import ru.lai.hw2_rest.repositories.Repository;
import ru.lai.hw2_rest.utils.RequestParser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorService implements Service<Doctor> {
    private final Repository<Doctor> repository;
    private static final Logger logger = Logger.getLogger(DoctorService.class.getName());

    public DoctorService(Repository<Doctor> repository) {
        this.repository = repository;
    }

    @Override
    public Doctor getById(int id) {
        try {
            return repository.findById(id);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "(Service) Could not find id in the database, id: " + id, e);
            throw new RuntimeException("(Service) Failed to find the id in the database: " + id, e);
        }
    }

    @Override
    public List<Doctor> getAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "(Service) Could not fetch result set from database", e);
            throw new RuntimeException("(Service) Failed to fetch result set from database", e);
        }
    }

    @Override
    public int create(Doctor entity) {
        try {
            return repository.save(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "(Service) Could not create a new record in database: " + entity, e);
            throw new RuntimeException("(Service) Failed to create a new record in database: " + entity, e);
        }
    }

    @Override
    public int update(Doctor entity) {
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
    public Doctor parseEntity(HttpServletRequest req) {
        Map<String, String> doctorMap = RequestParser.getParameterMap(req);
        Doctor doctor = new Doctor();
        try {
            doctor.setUpWithMap(doctorMap);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "(Service) Could not setup entity with parameter map: " + doctorMap, e);
            throw new RuntimeException("(Service) Failed to setup entity with parameter map: " + doctorMap, e);
        }

        return doctor;
    }

}