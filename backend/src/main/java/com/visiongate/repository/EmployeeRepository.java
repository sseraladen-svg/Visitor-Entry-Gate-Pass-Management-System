package com.visiongate.repository;

import com.visiongate.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    EmployeeEntity findByEmployeeId(String employeeId);
    EmployeeEntity findByEmail(String email);
}