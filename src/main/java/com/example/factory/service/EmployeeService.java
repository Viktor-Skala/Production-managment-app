package com.example.factory.service;

import com.example.factory.model.Employee;
import com.example.factory.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    Employee create(Employee employee);
    Employee read(long id);
    Employee update(Employee employee);
    void delete(long id);
    List<Employee> getAll();
    Employee findByEmail(String email);
}
