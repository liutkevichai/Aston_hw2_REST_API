package ru.lai.hw2_rest.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractServiceImpl<T> implements AbstractService<T> {

    private final JpaRepository<T, Integer> repository;

    protected AbstractServiceImpl(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }

    @Override
    public T getById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

}

