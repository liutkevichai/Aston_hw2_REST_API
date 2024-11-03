package ru.lai.hw2_rest.services;

import ru.lai.hw2_rest.models.Patient;
import ru.lai.hw2_rest.repositories.Repository;

import java.sql.SQLException;
import java.util.List;
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
            logger.log(Level.WARNING, "Could not find id in the database, id: " + id, e);
            throw new RuntimeException("Failed to find the id in the database: " + id, e);
        }
    }

    @Override
    public List<Patient> getAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not fetch result set from database", e);
            throw new RuntimeException("Failed to fetch result set from database", e);
        }
    }

    @Override
    public void create(Patient entity) {
        try {
            repository.save(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not create a new record in database: " + entity, e);
            throw new RuntimeException("Failed to create a new record in database: " + entity, e);
        }
    }

    @Override
    public void update(Patient entity) {
        try {
            repository.update(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not update a record in database: " + entity, e);
            throw new RuntimeException("Failed to update a record in database: " + entity, e);
        }

    }

    @Override
    public void delete(Patient entity) {
        try {
            repository.delete(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not delete the record from database: " + entity, e);
            throw new RuntimeException("Failed to delete the record from database: " + entity, e);
        }
    }

}
