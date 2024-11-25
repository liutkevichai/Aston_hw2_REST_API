package ru.lai.hw2_rest.services;

import org.hibernate.Session;
import ru.lai.hw2_rest.models.Office;
import ru.lai.hw2_rest.repositories.Repository;
import ru.lai.hw2_rest.utils.HibernateUtil;

import java.util.List;


public class OfficeService implements Service<Office> {
    private final Repository<Office> repository;

    public OfficeService(Repository<Office> repository) {
        this.repository = repository;
    }

    @Override
    public Office getById(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Office office = repository.findById(id, session);
            session.getTransaction().commit();
            return office;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching office with ID " + id);
        }
    }

    @Override
    public List<Office> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            List<Office> offices = repository.findAll(session);
            session.getTransaction().commit();
            return offices;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching all offices");
        }
    }

    @Override
    public Office save(Office entity) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Office savedOffices = repository.save(entity, session);
            session.getTransaction().commit();
            return savedOffices;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving office");
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Office office = repository.findById(id, session);
            if (office != null) {
                repository.delete(office, session);
                session.getTransaction().commit();
            } else {
                throw new IllegalArgumentException("Office not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting office with ID " + id);
        }
    }

}
