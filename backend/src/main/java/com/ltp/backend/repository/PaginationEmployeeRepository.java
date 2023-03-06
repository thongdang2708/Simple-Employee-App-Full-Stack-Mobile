package com.ltp.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.ltp.backend.entity.Employee;

@Repository
public interface PaginationEmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    List<Employee> findAll(Sort sort);

    Page<Employee> findAll(Pageable pageable);
}
