package com.visiongate.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String visitorName;

    @Column(nullable = false)
    private String visitorEmail;

    @Column(nullable = false)
    private String visitorPhone;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String hostName;

    @Column(nullable = false)
    private String hostEmail;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @Column(nullable = false)
    private String appointmentTime;

    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED, COMPLETED, CANCELLED

    @Column
    private String rejectionReason;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime approvedDate;

    public AppointmentEntity() {}

    public AppointmentEntity(String visitorName, String visitorEmail, String visitorPhone, 
                           String company, String hostName, String hostEmail, 
                           String purpose, LocalDateTime appointmentDate, String appointmentTime) {
        this.visitorName = visitorName;
        this.visitorEmail = visitorEmail;
        this.visitorPhone = visitorPhone;
        this.company = company;
        this.hostName = hostName;
        this.hostEmail = hostEmail;
        this.purpose = purpose;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = "PENDING";
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }

    public String getVisitorEmail() { return visitorEmail; }
    public void setVisitorEmail(String visitorEmail) { this.visitorEmail = visitorEmail; }

    public String getVisitorPhone() { return visitorPhone; }
    public void setVisitorPhone(String visitorPhone) { this.visitorPhone = visitorPhone; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }

    public String getHostEmail() { return hostEmail; }
    public void setHostEmail(String hostEmail) { this.hostEmail = hostEmail; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public LocalDateTime getApprovedDate() { return approvedDate; }
    public void setApprovedDate(LocalDateTime approvedDate) { this.approvedDate = approvedDate; }
}
