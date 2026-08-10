package com.college.visitorgatepass.controller;

import com.college.visitorgatepass.dto.GatePassDtos.DashboardStats;
import com.college.visitorgatepass.service.GatePassService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final GatePassService gatePassService;

    public DashboardController(GatePassService gatePassService) {
        this.gatePassService = gatePassService;
    }

    @GetMapping("/stats")
    public DashboardStats stats() {
        return gatePassService.stats();
    }
}
