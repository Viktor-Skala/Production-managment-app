package com.example.factory.service;

import com.example.factory.model.Employee;
import com.example.factory.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    Role create(Role role);
    Role read(long id);
    Role update(Role role);
    void delete(long id);
    List<Role> getAll();
    Role findByName(String name);
}
