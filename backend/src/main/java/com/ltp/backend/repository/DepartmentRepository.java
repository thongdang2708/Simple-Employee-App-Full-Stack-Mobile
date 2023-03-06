package com.ltp.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.backend.entity.Department;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findByManagerId(Long managerId);

    Optional<Department> findByName(String name);
}
