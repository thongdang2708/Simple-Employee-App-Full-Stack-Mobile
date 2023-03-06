package com.ltp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.backend.entity.Department;
import com.ltp.backend.entity.Manager;
import com.ltp.backend.exception.DepartmentNotFoundWithIdException;
import com.ltp.backend.exception.DepartmentNotFoundWithNameException;
import com.ltp.backend.repository.DepartmentRepository;

@Service
public class DepartmentServiceIml implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ManagerService managerService;

    @Override
    public Department getDepartment(Long id) {
        Optional<Department> department = departmentRepository.findById(id);

        if (department.isPresent()) {
            return department.get();
        } else {
            throw new DepartmentNotFoundWithIdException("This department id " + id + " cannot be found!");
        }
    }

    @Override
    public Department addDepartment(Long managerId, Department department) {

        Manager manager = managerService.getSingleManager(managerId);

        department.setManager(manager);

        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        Department updateDepartment = getDepartment(id);

        updateDepartment.setName(department.getName());

        return departmentRepository.save(updateDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        // TODO Auto-generated method stub
        departmentRepository.deleteById(id);

    }

    @Override
    public List<Department> getAllDepartmentsCreatedByManager(Long managerId) {
        // TODO Auto-generated method stub
        return departmentRepository.findByManagerId(managerId);
    }

    @Override
    public List<Department> getAllDepartments() {
        return (List<Department>) departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentByName(String name) {
        // TODO Auto-generated method stub
        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new DepartmentNotFoundWithNameException(
                        "This department with this name " + name + " cannot be found!"));
        return department;
    }
}
