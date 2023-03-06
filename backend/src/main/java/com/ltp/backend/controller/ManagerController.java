package com.ltp.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.backend.entity.Manager;
import com.ltp.backend.service.ManagerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Manager> getSingleManager(@PathVariable Long id) {

        return new ResponseEntity<>(managerService.getSingleManager(id), HttpStatus.OK);
    }

  

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Manager> updateManager(@PathVariable Long id, @Valid @RequestBody Manager manager) {

        return new ResponseEntity<>(managerService.updateManager(id, manager), HttpStatus.OK);
    }

}
