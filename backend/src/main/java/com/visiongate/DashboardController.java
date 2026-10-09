package com.visiongate;

import com.visiongate.entity.AppointmentEntity;
import com.visiongate.entity.EntryLogEntity;
import com.visiongate.entity.GatePassEntity;
import com.visiongate.entity.VisitorEntity;
import com.visiongate.repository.AppointmentRepository;
import com.visiongate.repository.EntryLogRepository;
import com.visiongate.repository.GatePassRepository;
import com.visiongate.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private GatePassRepository gatePassRepository;

    @Autowired
    private EntryLogRepository entryLogRepository;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        // Count only visitors with VISITOR role
        stats.put("totalVisitors", visitorRepository.findByRole("VISITOR").size());
        stats.put("approvedAppointments", appointmentRepository.findByStatus("APPROVED").size());
        stats.put("pendingAppointments", appointmentRepository.findByStatus("PENDING").size());
        stats.put("activeVisitors", gatePassRepository.findByStatus("INSIDE").size());
        return stats;
    }

    @GetMapping("/recent-activity")
    public List<Map<String, String>> getRecentActivity() {
        List<Map<String, String>> activities = new java.util.ArrayList<>();
        
        // Get recent entry logs
        List<EntryLogEntity> logs = entryLogRepository.findAllByOrderByTimestampDesc();
        logs.stream()
            .limit(5)
            .forEach(log -> {
                Map<String, String> activity = new HashMap<>();
                activity.put("type", log.getAction());
                activity.put("details", log.getVisitorName() + " - " + log.getPassId());
                activities.add(activity);
            });
        
        return activities;
    }

    @GetMapping("/appointments")
    public List<AppointmentEntity> getAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/gate-passes")
    public List<GatePassEntity> getGatePasses() {
        return gatePassRepository.findAll();
    }

    @GetMapping("/visitors")
    public List<VisitorEntity> getVisitors() {
        return visitorRepository.findAll();
    }
}
