package com.visiongate;

public class Visitor {

    private String name;
    private String phone;
    private String purpose;

    public Visitor(String name, String phone, String purpose) {
        this.name = name;
        this.phone = phone;
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPurpose() {
        return purpose;
    }
}