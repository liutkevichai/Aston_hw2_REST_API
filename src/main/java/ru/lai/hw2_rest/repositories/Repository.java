package ru.lai.hw2_rest.repositories;

import org.hibernate.Session;

import java.util.List;

public abstract class Repository<T> {

    private final Class<T> entityClass;

    protected Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(int id, Session session) {
        return session.get(entityClass, id);
    }

    public List<T> findAll(Session session) {
        return session.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    public T save(T entity, Session session) {
        session.saveOrUpdate(entity);
        return entity;
    }

    public void delete(T entity, Session session) {
        session.delete(entity);
    }
}
