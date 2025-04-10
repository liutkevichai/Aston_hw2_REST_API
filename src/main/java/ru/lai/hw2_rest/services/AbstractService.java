package ru.lai.hw2_rest.services;

import java.util.List;

public interface AbstractService<T> {
    T getById(int id);

    List<T> getAll();

    T save(T entity);

    void delete(int id);
}
