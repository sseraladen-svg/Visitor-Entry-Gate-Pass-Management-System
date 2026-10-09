package com.visiongate.repository;

import com.visiongate.entity.GatePassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GatePassRepository extends JpaRepository<GatePassEntity, Long> {
    Optional<GatePassEntity> findByPassId(String passId);
    List<GatePassEntity> findByVisitorId(Long visitorId);
    List<GatePassEntity> findByVisitorEmail(String visitorEmail);
    List<GatePassEntity> findByStatus(String status);
    List<GatePassEntity> findByVisitDate(String visitDate);
}