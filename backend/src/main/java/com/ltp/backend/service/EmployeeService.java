package com.ltp.backend.service;

import com.ltp.backend.entity.Department;
import com.ltp.backend.entity.Employee;
import java.util.List;

import org.springframework.data.domain.Page;

public interface EmployeeService {
    Employee addEmployee(Long departmentId, Long managerId, Employee employee);

    Employee updateEmployee(Long id, Employee employee, Long departmentId);

    void deleteEmployee(Long id);

    List<Employee> getAllEmployees();

    Employee getSingleEmployee(Long id);

    Page<Employee> getEmployeeWithPagination(int offset, int pageSize);

}
