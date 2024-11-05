package ru.lai.hw2_rest.services;

import jakarta.servlet.http.HttpServletRequest;
import ru.lai.hw2_rest.models.Patient;
import ru.lai.hw2_rest.repositories.Repository;
import ru.lai.hw2_rest.utils.RequestParser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientService implements Service<Patient> {
    private final Repository<Patient> repository;
    private static final Logger logger = Logger.getLogger(PatientService.class.getName());

    public PatientService(Repository<Patient> repository) {
        this.repository = repository;
    }

    @Override
    public Patient getById(int id) {
        try {
            return repository.findById(id);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "(Service) Could not find id in the database, id: " + id, e);
            throw new RuntimeException("(Service) Failed to find the id in the database: " + id, e);
        }
    }

    @Override
    public List<Patient> getAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "(Service) Could not fetch result set from database", e);
            throw new RuntimeException("(Service) Failed to fetch result set from database", e);
        }
    }

    @Override
    public int create(Patient entity) {
        try {
            return repository.save(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "(Service) Could not create a new record in database: " + entity, e);
            throw new RuntimeException("(Service) Failed to create a new record in database: " + entity, e);
        }
    }

    @Override
    public int update(Patient entity) {
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
    public Patient parseEntity(HttpServletRequest req) {
        Map<String, String> patientMap = RequestParser.getParameterMap(req);
        Patient patient = new Patient();
        try {
            patient.setUpWithMap(patientMap);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "(Service) Could not setup entity with parameter map: " + patientMap, e);
            throw new RuntimeException("(Service) Failed to setup entity with parameter map: " + patientMap, e);
        }

        return patient;
    }

}
