package com.visiongate;

import com.visiongate.entity.AppointmentEntity;
import com.visiongate.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @PostMapping("/approve-appointment")
    public String approveAppointment(@RequestParam Long appointmentId) {

        System.out.println("Approving appointment: " + appointmentId);

        try {
            appointmentService.approveAppointment(appointmentId, "admin");
            System.out.println("Appointment approved: " + appointmentId);
            return "redirect:/host-approval.html?success=true";
        } catch (Exception e) {
            System.out.println("Approval failed: " + e.getMessage());
            return "redirect:/host-approval.html?error=Approval failed";
        }
    }

    @PostMapping("/reject-appointment")
    public String rejectAppointment(@RequestParam Long appointmentId,
                                    @RequestParam String reason) {

        System.out.println("Rejecting appointment: " + appointmentId);

        try {
            appointmentService.rejectAppointment(appointmentId, reason);
            System.out.println("Appointment rejected: " + appointmentId);
            return "redirect:/host-approval.html?success=true";
        } catch (Exception e) {
            System.out.println("Rejection failed: " + e.getMessage());
            return "redirect:/host-approval.html?error=Rejection failed";
        }
    }
}
