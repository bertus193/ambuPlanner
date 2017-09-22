package com.example.ambuplanner.model;

import java.util.List;

public class App {

    private static List<Position> positions;

    public App(List<Position> positions){
        if(positions.isEmpty()){
            this.positions = positions;
        }
        else{
            System.out.println("Ya se ha instalaciado la app");
        }
    }

    public static List<Position> getPositions() {
        return positions;
    }

    public App clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
