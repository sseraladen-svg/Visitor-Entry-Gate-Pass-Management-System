package com.visiongate.service;

import com.visiongate.entity.VehicleEntity;
import com.visiongate.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    public VehicleEntity registerVehicle(String visitorId, String vehicleNumber, String vehicleType,
                                        String vehicleModel, String vehicleColor) {
        VehicleEntity vehicle = new VehicleEntity(visitorId, vehicleNumber, vehicleType, 
                                               vehicleModel, vehicleColor);
        return vehicleRepository.save(vehicle);
    }
    
    public List<VehicleEntity> findByVisitorId(String visitorId) {
        return vehicleRepository.findByVisitorId(visitorId);
    }
    
    public List<VehicleEntity> findByStatus(String status) {
        return vehicleRepository.findByStatus(status);
    }
    
    public VehicleEntity markExit(Long id) {
        VehicleEntity vehicle = vehicleRepository.findById(id).orElse(null);
        if (vehicle != null) {
            vehicle.setStatus("EXITED");
            vehicle.setExitTime(java.time.LocalTime.now().toString());
            return vehicleRepository.save(vehicle);
        }
        return null;
    }
}
