package com.ltp.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.ltp.backend.entity.Department;
import com.ltp.backend.service.DepartmentService;

import jakarta.mail.Multipart;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Department> getSingleDepartment(@PathVariable Long id) {

        return new ResponseEntity<Department>(departmentService.getDepartment(id), HttpStatus.OK);
    }

    @PostMapping("/manager/{managerId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Department> addDepartment(@Valid @RequestBody Department department,
            @PathVariable Long managerId) {

        return new ResponseEntity<Department>(departmentService.addDepartment(managerId, department),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Department> updateDepartment(@Valid @RequestBody Department department,
            @PathVariable Long id) {

        return new ResponseEntity<Department>(departmentService.updateDepartment(id, department), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/manager/{managerId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Department>> getDepartmentsCreatedByManagers(@PathVariable Long managerId) {

        return new ResponseEntity<>(departmentService.getAllDepartmentsCreatedByManager(managerId), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Department>> getAllDepartments() {

        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

}
