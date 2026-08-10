package com.visiongate;

public class GatePass {

    private String passId;
    private String visitorName;
    private String purpose;
    private String status;

    public GatePass(String passId, String visitorName, String purpose, String status) {
        this.passId = passId;
        this.visitorName = visitorName;
        this.purpose = purpose;
        this.status = status;
    }

    public String getPassId() {
        return passId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getStatus() {
        return status;
    }
}