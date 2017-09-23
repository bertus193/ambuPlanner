package com.example.ambuplanner.model;

public class Notification {

    private int orderNumber;

    private CoordValue patientPosition;

    public Notification(int orderNumber, CoordValue patientPosition) {
        this.orderNumber = orderNumber;
        this.patientPosition = patientPosition;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public CoordValue getPatientPosition() {
        return patientPosition;
    }

    public void setPatientPosition(CoordValue patientPosition) {
        this.patientPosition = patientPosition;
    }
}
