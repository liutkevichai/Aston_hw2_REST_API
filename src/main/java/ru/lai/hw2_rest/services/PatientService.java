package ru.lai.hw2_rest.services;

import org.hibernate.Session;
import ru.lai.hw2_rest.models.Patient;
import ru.lai.hw2_rest.repositories.Repository;
import ru.lai.hw2_rest.utils.HibernateUtil;

import java.util.List;

public class PatientService implements Service<Patient> {
    private final Repository<Patient> repository;

    public PatientService(Repository<Patient> repository) {
        this.repository = repository;
    }

    @Override
    public Patient getById(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Patient patient = repository.findById(id, session);
            session.getTransaction().commit();
            return patient;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching patient with ID " + id);
        }
    }

    @Override
    public List<Patient> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            List<Patient> patients = repository.findAll(session);
            session.getTransaction().commit();
            return patients;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching all patients");
        }
    }

    @Override
    public Patient save(Patient entity) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Patient savedPatients = repository.save(entity, session);
            session.getTransaction().commit();
            return savedPatients;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving patient");
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Patient patient = repository.findById(id, session);
            if (patient != null) {
                repository.delete(patient, session);
                session.getTransaction().commit();
            } else {
                throw new IllegalArgumentException("Patient not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting patient with ID " + id);
        }
    }

}
