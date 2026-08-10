package com.college.visitorgatepass.repository;

import com.college.visitorgatepass.model.GatePass;
import com.college.visitorgatepass.model.GatePassStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatePassRepository extends JpaRepository<GatePass, Long> {

    Optional<GatePass> findByPassCode(String passCode);

    List<GatePass> findByStatusOrderByCreatedAtDesc(GatePassStatus status);

    List<GatePass> findByHostIdOrderByCreatedAtDesc(Long hostId);

    List<GatePass> findAllByOrderByCreatedAtDesc();

    long countByStatus(GatePassStatus status);

    long countByCheckInTimeAfter(LocalDateTime from);
}
