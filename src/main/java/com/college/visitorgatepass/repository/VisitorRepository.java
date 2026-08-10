package com.college.visitorgatepass.repository;

import com.college.visitorgatepass.model.Visitor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByPhone(String phone);

    List<Visitor> findByFullNameContainingIgnoreCaseOrPhoneContaining(String fullName, String phone);
}
