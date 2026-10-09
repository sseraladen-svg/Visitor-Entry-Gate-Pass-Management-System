package com.visiongate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String visitorId;

    @Column(nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private String vehicleType; // CAR, BIKE, TRUCK, VAN, OTHER

    @Column
    private String vehicleModel;

    @Column
    private String vehicleColor;

    @Column(nullable = false)
    private String status; // PARKED, EXITED

    @Column
    private String parkingSlot;

    @Column(nullable = false)
    private String entryTime;

    @Column
    private String exitTime;

    public VehicleEntity() {}

    public VehicleEntity(String visitorId, String vehicleNumber, String vehicleType, 
                       String vehicleModel, String vehicleColor) {
        this.visitorId = visitorId;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.status = "PARKED";
        this.entryTime = java.time.LocalTime.now().toString();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVisitorId() { return visitorId; }
    public void setVisitorId(String visitorId) { this.visitorId = visitorId; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getVehicleModel() { return vehicleModel; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }

    public String getVehicleColor() { return vehicleColor; }
    public void setVehicleColor(String vehicleColor) { this.vehicleColor = vehicleColor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getParkingSlot() { return parkingSlot; }
    public void setParkingSlot(String parkingSlot) { this.parkingSlot = parkingSlot; }

    public String getEntryTime() { return entryTime; }
    public void setEntryTime(String entryTime) { this.entryTime = entryTime; }

    public String getExitTime() { return exitTime; }
    public void setExitTime(String exitTime) { this.exitTime = exitTime; }
}
