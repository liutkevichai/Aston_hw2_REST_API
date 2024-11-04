package ru.lai.hw2_rest.services;

import ru.lai.hw2_rest.models.Appointment;
import ru.lai.hw2_rest.repositories.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentService implements Service<Appointment> {
    private final Repository<Appointment> repository;
    private static final Logger logger = Logger.getLogger(AppointmentService.class.getName());

    public AppointmentService(Repository<Appointment> repository) {
        this.repository = repository;
    }

    @Override
    public Appointment getById(int id) {
        try {
            return repository.findById(id);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not find id in the database, id: " + id, e);
            throw new RuntimeException("Failed to find the id in the database: " + id, e);
        }
    }

    @Override
    public List<Appointment> getAll() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not fetch result set from database", e);
            throw new RuntimeException("Failed to fetch result set from database", e);
        }
    }

    @Override
    public int create(Appointment entity) {
        validateAppointmentDateTime(entity.getAppointmentDatetime());
        try {
            return repository.save(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not create a new record in database: " + entity, e);
            throw new RuntimeException("Failed to create a new record in database: " + entity, e);
        }
    }

    @Override
    public int update(Appointment entity) {
        validateAppointmentDateTime(entity.getAppointmentDatetime());
        try {
            return repository.update(entity);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not update a record in database: " + entity, e);
            throw new RuntimeException("Failed to update a record in database: " + entity, e);
        }

    }

    @Override
    public int delete(int id) {
        try {
            return repository.delete(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Could not delete the record from database, id: " + id, e);
            throw new RuntimeException("Failed to delete the record from database, id: " + id, e);
        }
    }

    private void validateAppointmentDateTime(LocalDateTime appointmentDatetime) {
        if (appointmentDatetime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Date and time of the appointment must be later than the current moment");
        }
    }
}
