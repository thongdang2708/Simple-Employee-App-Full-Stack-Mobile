package com.ltp.backend.service;

import com.ltp.backend.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department getDepartment(Long id);

    Department addDepartment(Long managerId, Department department);

    Department updateDepartment(Long id, Department department);

    void deleteDepartment(Long id);

    List<Department> getAllDepartmentsCreatedByManager(Long managerId);

    List<Department> getAllDepartments();

    Department getDepartmentByName(String name);
}
