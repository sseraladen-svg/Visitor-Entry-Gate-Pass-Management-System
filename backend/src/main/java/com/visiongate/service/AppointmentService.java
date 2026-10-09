package com.visiongate.service;

import com.visiongate.entity.AppointmentEntity;
import com.visiongate.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public AppointmentEntity createAppointment(String visitorName, String visitorEmail, String visitorPhone,
                                              String company, String hostName, String hostEmail,
                                              String purpose, LocalDateTime appointmentDate, String appointmentTime) {
        AppointmentEntity appointment = new AppointmentEntity(
                visitorName, visitorEmail, visitorPhone, company, hostName, hostEmail,
                purpose, appointmentDate, appointmentTime
        );
        return appointmentRepository.save(appointment);
    }
    
    public List<AppointmentEntity> findByHostEmail(String hostEmail) {
        return appointmentRepository.findByHostEmail(hostEmail);
    }
    
    public List<AppointmentEntity> findByVisitorEmail(String visitorEmail) {
        return appointmentRepository.findByVisitorEmail(visitorEmail);
    }
    
    public List<AppointmentEntity> findByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
    
    public AppointmentEntity approveAppointment(Long id, String approvedBy) {
        AppointmentEntity appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus("APPROVED");
            appointment.setApprovedDate(LocalDateTime.now());
            return appointmentRepository.save(appointment);
        }
        return null;
    }
    
    public AppointmentEntity rejectAppointment(Long id, String reason) {
        AppointmentEntity appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus("REJECTED");
            appointment.setRejectionReason(reason);
            return appointmentRepository.save(appointment);
        }
        return null;
    }
}
