package com.visiongate.repository;

import com.visiongate.entity.VisitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {
    Optional<VisitorEntity> findByEmail(String email);
    List<VisitorEntity> findByRole(String role);
    List<VisitorEntity> findByStatus(String status);
    Optional<VisitorEntity> findByEmailAndPassword(String email, String password);
}
