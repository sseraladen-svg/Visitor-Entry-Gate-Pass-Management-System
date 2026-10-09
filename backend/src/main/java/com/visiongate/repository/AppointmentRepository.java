package com.visiongate.repository;

import com.visiongate.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByVisitorEmail(String visitorEmail);
    List<AppointmentEntity> findByHostEmail(String hostEmail);
    List<AppointmentEntity> findByStatus(String status);
}
