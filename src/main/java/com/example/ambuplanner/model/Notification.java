package com.example.ambuplanner.model;

public class Notification {

    private int orderNumber;

    private NodePosition patientPosition;

    public Notification(int orderNumber, NodePosition patientPosition) {
        this.orderNumber = orderNumber;
        this.patientPosition = patientPosition;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public NodePosition getPatientPosition() {
        return patientPosition;
    }

    public void setPatientPosition(NodePosition patientPosition) {
        this.patientPosition = patientPosition;
    }
}
