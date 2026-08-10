package com.visiongate.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gate_pass")
public class GatePassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String passId;

    @Column(nullable = false)
    private String visitorName;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    public GatePassEntity() {}

    public GatePassEntity(String passId, String visitorName, String purpose, String status) {
        this.passId = passId;
        this.visitorName = visitorName;
        this.purpose = purpose;
        this.status = status;
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPassId() { return passId; }
    public void setPassId(String passId) { this.passId = passId; }

    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}