package com.visiongate;

import com.visiongate.entity.VehicleEntity;
import com.visiongate.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/register-vehicle")
    public String registerVehicle(@RequestParam String visitorId,
                                   @RequestParam String vehicleNumber,
                                   @RequestParam String vehicleType,
                                   @RequestParam(required = false) String vehicleModel,
                                   @RequestParam(required = false) String vehicleColor) {

        System.out.println("Vehicle registration - Number: " + vehicleNumber);

        try {
            VehicleEntity vehicle = vehicleService.registerVehicle(
                visitorId, vehicleNumber, vehicleType, vehicleModel, vehicleColor
            );

            System.out.println("Vehicle registered successfully: " + vehicle.getId());
            return "redirect:/vehicle-management.html?success=true";
        } catch (Exception e) {
            System.out.println("Vehicle registration failed: " + e.getMessage());
            return "redirect:/vehicle-management.html?error=Registration failed";
        }
    }

    @PostMapping("/mark-vehicle-exit")
    public String markVehicleExit(@RequestParam Long vehicleId) {

        System.out.println("Marking vehicle exit: " + vehicleId);

        try {
            vehicleService.markExit(vehicleId);
            System.out.println("Vehicle exit marked: " + vehicleId);
            return "redirect:/vehicle-management.html?success=true";
        } catch (Exception e) {
            System.out.println("Exit marking failed: " + e.getMessage());
            return "redirect:/vehicle-management.html?error=Exit marking failed";
        }
    }
}
