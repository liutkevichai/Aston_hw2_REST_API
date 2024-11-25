package ru.lai.hw2_rest.services;

import org.hibernate.Session;
import ru.lai.hw2_rest.models.Doctor;
import ru.lai.hw2_rest.repositories.Repository;
import ru.lai.hw2_rest.utils.HibernateUtil;

import java.util.List;

public class DoctorService implements Service<Doctor> {
    private final Repository<Doctor> repository;

    public DoctorService(Repository<Doctor> repository) {
        this.repository = repository;
    }

    @Override
    public Doctor getById(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Doctor doctor = repository.findById(id, session);
            session.getTransaction().commit();
            return doctor;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching doctor with ID " + id);
        }
    }

    @Override
    public List<Doctor> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            List<Doctor> doctors = repository.findAll(session);
            session.getTransaction().commit();
            return doctors;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching all doctors");
        }
    }

    @Override
    public Doctor save(Doctor entity) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Doctor savedDoctors = repository.save(entity, session);
            session.getTransaction().commit();
            return savedDoctors;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving doctor");
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Doctor doctor = repository.findById(id, session);
            if (doctor != null) {
                repository.delete(doctor, session);
                session.getTransaction().commit();
            } else {
                throw new IllegalArgumentException("Doctor not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting doctor with ID " + id);
        }
    }

}