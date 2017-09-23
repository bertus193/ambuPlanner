package com.example.ambuplanner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NodePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int x;
    private int y;
    private String value;

    public NodePosition(int x, int y, String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public NodePosition() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == "null") {
            return " ";
        } else {
            return value;
        }
    }
}
