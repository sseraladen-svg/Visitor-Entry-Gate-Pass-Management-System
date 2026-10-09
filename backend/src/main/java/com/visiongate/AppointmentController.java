package com.visiongate;

import com.visiongate.entity.AppointmentEntity;
import com.visiongate.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book-appointment")
    public String bookAppointment(@RequestParam String visitorName,
                                  @RequestParam String visitorEmail,
                                  @RequestParam String visitorPhone,
                                  @RequestParam String hostName,
                                  @RequestParam String hostEmail,
                                  @RequestParam String purpose,
                                  @RequestParam String appointmentDate,
                                  @RequestParam String appointmentTime) {

        System.out.println("Appointment booking - Email: " + visitorEmail);

        try {
            // Parse date string to LocalDateTime
            LocalDateTime localDate = LocalDateTime.parse(appointmentDate + "T00:00:00");

            AppointmentEntity appointment = appointmentService.createAppointment(
                visitorName, visitorEmail, visitorPhone, "", hostName, hostEmail,
                purpose, localDate, appointmentTime
            );

            System.out.println("Appointment booked successfully: " + appointment.getId());
            return "redirect:/appointment.html?success=true";
        } catch (Exception e) {
            System.out.println("Appointment booking failed: " + e.getMessage());
            return "redirect:/appointment.html?error=Booking failed";
        }
    }

    @PostMapping("/api/approve-appointment")
    @ResponseBody
    public Map<String, Object> approveAppointment(@RequestBody Map<String, Long> request) {
        Map<String, Object> response = new HashMap<>();
        Long appointmentId = request.get("appointmentId");

        System.out.println("Approving appointment: " + appointmentId);

        try {
            appointmentService.approveAppointment(appointmentId, "admin");
            System.out.println("Appointment approved: " + appointmentId);
            response.put("success", true);
            response.put("message", "Appointment approved successfully");
        } catch (Exception e) {
            System.out.println("Approval failed: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Approval failed");
        }

        return response;
    }

    @PostMapping("/api/reject-appointment")
    @ResponseBody
    public Map<String, Object> rejectAppointment(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        Long appointmentId = (Long) request.get("appointmentId");
        String reason = (String) request.get("reason");

        System.out.println("Rejecting appointment: " + appointmentId);

        try {
            appointmentService.rejectAppointment(appointmentId, reason);
            System.out.println("Appointment rejected: " + appointmentId);
            response.put("success", true);
            response.put("message", "Appointment rejected successfully");
        } catch (Exception e) {
            System.out.println("Rejection failed: " + e.getMessage());
            response.put("success", false);
            response.put("message", "Rejection failed");
        }

        return response;
    }
}
