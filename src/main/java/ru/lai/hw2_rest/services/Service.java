package ru.lai.hw2_rest.services;

import java.util.List;

public interface Service<T> {
    T getById(int id);
    List<T> getAll();
    void create(T entity);
    void update(T entity);
    void delete(T entity);
}
