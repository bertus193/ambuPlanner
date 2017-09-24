package com.example.ambuplanner.model;

public class CoordValue {

    private int x;
    private int y;
    private String value;

    public CoordValue(int x, int y, String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value.equals("null")) {
            return " ";
        } else {
            return value;
        }
    }

    public boolean equals(CoordValue coord) {
        if (this.getX() == coord.getX() && this.getY() == coord.getY()) {
            return true;
        } else {
            return false;
        }
    }
}
