package com.example.ambuplanner.model;

public class Position {

    private Coordinate coord;
    private String value;

    public Position(Coordinate coord, String value) {
        this.coord = coord;
        this.value = value;
    }

    public Position() {}

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
