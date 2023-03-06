package com.ltp.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.backend.entity.Manager;

@Repository
public interface ManagerRepository extends CrudRepository<Manager, Long> {

}
