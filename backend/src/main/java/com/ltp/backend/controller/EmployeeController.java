package com.ltp.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.backend.entity.Employee;
import com.ltp.backend.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/department/{departmentId}/manager/{managerId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee, @PathVariable Long departmentId,
            @PathVariable Long managerId) {

        return new ResponseEntity<Employee>(employeeService.addEmployee(departmentId, managerId, employee),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Employee> getSingleEmployee(@PathVariable Long id, @PathVariable Long departmentId) {

        return new ResponseEntity<>(employeeService.getSingleEmployee(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/department/{departmentId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee,
            @PathVariable Long id, @PathVariable Long departmentId) {

        return new ResponseEntity<>(employeeService.updateEmployee(id, employee, departmentId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Employee>> getAllEmployees() {

        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/pagination/all/{offset}/{pageSize}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Employee>> getPaginationOfEmployees(@PathVariable int offset,
            @PathVariable int pageSize) {

        Page<Employee> employees = employeeService.getEmployeeWithPagination(offset, pageSize);

        List<Employee> employeeList = new ArrayList<>();

        for (Employee employee : employees) {
            Employee newEmployee = new Employee(employee.getId(), employee.getName(), employee.getDepartment(),
                    employee.getManager());
            employeeList.add(newEmployee);
        }

        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

}
