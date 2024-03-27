package com.example.factory.security;

import com.example.factory.model.Employee;
import com.example.factory.model.Role;
import com.example.factory.service.EmployeeService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private EmployeeService employeeService;

    public UserDetailsServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeService.findByEmail(username);
        if (employee == null) {
            throw new UsernameNotFoundException(username + "not found");
        }
        List<GrantedAuthority> roles = employee.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(employee.getEmail(), employee.getPassword(), roles);
    }

    public UserDetails loadUserWithoutPasswordByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeService.findByEmail(username);
        if (employee == null) {
            throw new UsernameNotFoundException(username + "not found");
        }
        List<GrantedAuthority> roles = employee.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
//        return User.builder().username(employee.getEmail()).authorities(roles).build();
        return new User(employee.getEmail(), "1", roles);
    }
}
