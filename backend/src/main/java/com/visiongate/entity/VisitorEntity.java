package com.visiongate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "visitor")
public class VisitorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String purpose;

    public VisitorEntity() {}

    public VisitorEntity(String name, String phone, String purpose) {
        this.name = name;
        this.phone = phone;
        this.purpose = purpose;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}