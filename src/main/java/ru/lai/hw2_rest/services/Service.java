package ru.lai.hw2_rest.services;

import java.util.List;

public interface Service<T> {
    T getById(int id);
    List<T> getAll();
    int create(T entity);
    int update(T entity);
    int delete(int id);
}
