package com.example.ambuplanner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Position {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

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

    @Override
    public String toString() {
        if(value == "null"){
            return " ";
        } else{
            return value;
        }
    }
}
