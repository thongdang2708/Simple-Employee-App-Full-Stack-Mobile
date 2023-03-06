package com.ltp.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.backend.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
