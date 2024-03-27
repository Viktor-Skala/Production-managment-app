package com.example.factory.service;

import com.example.factory.model.Machine;

import java.util.List;

public interface MachineService {

    Machine create(Machine machine);
    Machine read(long id);
    List<Machine> getAll();
    Machine update(Machine machine);
    void delete(long machineId);
}
