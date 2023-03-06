package com.ltp.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.backend.entity.Manager;
import com.ltp.backend.exception.ManagerNotFoundWithIdException;
import com.ltp.backend.repository.ManagerRepository;

@Service
public class ManagerServiceIml implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Manager getSingleManager(Long id) {
        Optional<Manager> manager = managerRepository.findById(id);

        if (manager.isPresent()) {
            return manager.get();
        } else {
            throw new ManagerNotFoundWithIdException("This manager with this " + id + " cannot be found!");
        }
    }

    @Override
    public Manager updateManager(Long id, Manager manager) {
        Manager updatedManager = getSingleManager(id);

        updatedManager.setCity(manager.getCity());
        updatedManager.setAddress(manager.getAddress());

        return managerRepository.save(updatedManager);
    }
}
