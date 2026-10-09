package com.visiongate.repository;

import com.visiongate.entity.EntryLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryLogRepository extends JpaRepository<EntryLogEntity, Long> {
    List<EntryLogEntity> findByPassId(String passId);
    List<EntryLogEntity> findByAction(String action);
    List<EntryLogEntity> findAllByOrderByTimestampDesc();
}