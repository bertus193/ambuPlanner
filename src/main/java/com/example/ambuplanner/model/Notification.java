package com.example.ambuplanner.model;

public class Notification {

    private int orderNumber;

    private AbstractNode patientPosition;

    public Notification(int orderNumber, AbstractNode patientPosition) {
        this.orderNumber = orderNumber;
        this.patientPosition = patientPosition;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public AbstractNode getPatientPosition() {
        return patientPosition;
    }

    public void setPatientPosition(AbstractNode patientPosition) {
        this.patientPosition = patientPosition;
    }
}
