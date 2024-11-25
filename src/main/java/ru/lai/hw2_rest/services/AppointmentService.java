package ru.lai.hw2_rest.services;

import org.hibernate.Session;
import ru.lai.hw2_rest.models.Appointment;
import ru.lai.hw2_rest.repositories.Repository;
import ru.lai.hw2_rest.utils.HibernateUtil;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService implements Service<Appointment> {
    private final Repository<Appointment> repository;

    public AppointmentService(Repository<Appointment> repository) {
        this.repository = repository;
    }

    @Override
    public Appointment getById(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Appointment appointment = repository.findById(id, session);
            session.getTransaction().commit();
            return appointment;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching appointment with ID " + id);
        }
    }

    @Override
    public List<Appointment> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            List<Appointment> appointments = repository.findAll(session);
            session.getTransaction().commit();
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching all appointments");
        }
    }

    @Override
    public Appointment save(Appointment entity) {
        validateAppointmentDateTime(entity.getAppointmentDatetime());

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Appointment savedAppointment = repository.save(entity, session);
            session.getTransaction().commit();
            return savedAppointment;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving appointment");
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Appointment appointment = repository.findById(id, session);
            if (appointment != null) {
                repository.delete(appointment, session);
                session.getTransaction().commit();
            } else {
                throw new IllegalArgumentException("Appointment not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting appointment with ID " + id);
        }
    }

    private void validateAppointmentDateTime(LocalDateTime appointmentDatetime) {
        if (appointmentDatetime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "(Service) Date and time of the appointment must be later than the current moment");
        }
    }

}
