package com.ltp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ltp.backend.entity.Department;
import com.ltp.backend.entity.Employee;
import com.ltp.backend.entity.Manager;
import com.ltp.backend.exception.EmployeeNotFoundWithIdException;
import com.ltp.backend.repository.EmployeeRepository;
import com.ltp.backend.repository.PaginationEmployeeRepository;

@Service
public class EmployeeServiceIml implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private PaginationEmployeeRepository paginationEmployeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        // TODO Auto-generated method stub
        return (List<Employee>) employeeRepository.findAll();
    }

    @Override
    public Employee getSingleEmployee(Long id) {
        // TODO Auto-generated method stub
        return employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundWithIdException("This employee with this id " + id + " cannot be found!"));

    }

    @Override
    public Employee updateEmployee(Long id, Employee employee, Long departmentId) {
        Employee updatedEmployee = getSingleEmployee(id);
        Department department = departmentService.getDepartment(departmentId);

        updatedEmployee.setName(employee.getName());
        updatedEmployee.setDepartment(department);

        return employeeRepository.save(updatedEmployee);

    }

    @Override
    public Employee addEmployee(Long departmentId, Long managerId, Employee employee) {
        Department department = departmentService.getDepartment(departmentId);
        Manager manager = managerService.getSingleManager(managerId);

        employee.setDepartment(department);
        employee.setManager(manager);

        return employeeRepository.save(employee);

    }

    @Override
    public void deleteEmployee(Long id) {
        // TODO Auto-generated method stub
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> getEmployeeWithPagination(int offset, int pageSize) {
        Page<Employee> pagination = paginationEmployeeRepository.findAll(PageRequest.of(offset, pageSize));

        return pagination;
    }
}
