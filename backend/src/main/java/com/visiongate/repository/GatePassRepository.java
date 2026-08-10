package com.visiongate.repository;

import com.visiongate.entity.GatePassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatePassRepository extends JpaRepository<GatePassEntity, Long> {
    GatePassEntity findByPassId(String passId);
}