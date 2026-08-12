package com.visiongate.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entry_log")
public class EntryLogEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String passId;
    
    @Column(nullable = false)
    private String visitorName;
    
    @Column(nullable = false)
    private String action; // ENTRY or EXIT
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column
    private String notes;
    
    // Constructors
    public EntryLogEntity() {}
    
    public EntryLogEntity(String passId, String visitorName, String action, LocalDateTime timestamp) {
        this.passId = passId;
        this.visitorName = visitorName;
        this.action = action;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPassId() { return passId; }
    public void setPassId(String passId) { this.passId = passId; }
    
    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}