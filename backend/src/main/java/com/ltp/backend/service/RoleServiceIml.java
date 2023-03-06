package com.ltp.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.backend.entity.Role;
import com.ltp.backend.exception.RoleNotFoundWithIdException;
import com.ltp.backend.repository.RoleRepository;

@Service
public class RoleServiceIml implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);

        if (role.isPresent()) {
            return role.get();
        } else {
            throw new RoleNotFoundWithIdException("This role id " + id + " cannot be found!");
        }
    }
}
