package com.codegym.service;

import java.util.List;

public interface ReceptionistService <Receptionist>{
    List<Receptionist> findAll();

    void save(Receptionist receptionist);

    Receptionist findById(int id);

    void update(int id, Receptionist receptionist);

    void remove(int id);

    List<Receptionist> search(String word);
}
