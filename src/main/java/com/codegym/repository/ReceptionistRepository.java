package com.codegym.repository;

import com.codegym.model.Receptionist;

import java.util.List;

public interface ReceptionistRepository<T> {
    List<T> findAll();

    void save(T t);

    T findById(int id);

    void update(int id, T t);

    void remove(int id);

    List<T> search(String word);
}
