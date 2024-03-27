package com.example.factory.dto;

import com.example.factory.model.Employee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private long departmentId;
    private String departmentName;
    @Email
    @NotBlank
    private String email;
    @Pattern(regexp = "[A-Za-z\\d]{6,}",
            message = "Must be minimum 6 symbols long, using digits and latin letters")
    private String password;

    public static EmployeeDto of(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getName())
                .lastName(employee.getLastName())
                .departmentId(Objects.isNull(employee.getDepartment()) ? 0 : employee.getDepartment().getId())
                .departmentName(Objects.isNull(employee.getDepartment()) ? "" : employee.getDepartment().getName())
                .email(employee.getEmail())
                .password(employee.getPassword())
                .build();
    }
}
