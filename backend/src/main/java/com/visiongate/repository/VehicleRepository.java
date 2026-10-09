package com.visiongate.repository;

import com.visiongate.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    List<VehicleEntity> findByVisitorId(String visitorId);
    List<VehicleEntity> findByVehicleNumber(String vehicleNumber);
    List<VehicleEntity> findByStatus(String status);
}
