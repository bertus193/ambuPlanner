package com.example.ambuplanner.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static List<Position> positions = new ArrayList<>();

    private App(List<Position> positions){
        App.positions = positions;
    }

    public static List<Position> getPositions() {
        return App.positions;
    }

    public static void setPositions(List<Position> positions) {
        if(App.getPositions().isEmpty()){
            new App(positions);
        }
        else{
            System.err.println("Ya se ha instalaciado la app");
        }
    }

    public App clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public static void initPositions() {
        String map = "[[1, null, null, null, null, null, null, null, null, 4], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, null, null, null, null, null, null, null, null, null], [null, \"B\", \"H\", 2, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"H\", \"B\", null], [null, null, null, null, null, null, null, 3, null, null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"H\", null, \"B\", \"B\", null],[5, null, null, null, null, null, null, 6, null, null]]";
        JSONArray jsonPosition;
        List<Position> initPositions = new ArrayList<>();
        int y = 0;
        try {
            JSONArray array = new JSONArray(map);
            for (int countPosition = 0; countPosition < array.length(); countPosition++) {
                jsonPosition = array.getJSONArray(countPosition);
                for(int x = 0; x < jsonPosition.length(); x++){
                    initPositions.add(new Position(new Coordinate(x, y), jsonPosition.get(x).toString()));
                }
                y++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        App.setPositions(initPositions);
    }

    public static List<Position> avaiablePositionsAround(Coordinate coord){
        List<Position> positionsOut = new ArrayList<>();

        if(coord.getX()==0){
            if(coord.getY()==0){
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()+1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY()+1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY())));
            }
            else if(coord.getY() == Math.sqrt(App.getPositions().size()) - 1){
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY())));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY()-1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()-1)));
            }
            else{
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()+1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY()+1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY())));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY()-1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()-1)));
            }

        }
        else if(coord.getX() == Math.sqrt(App.getPositions().size()) - 1){//derecho
            if(coord.getY()==0){
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY())));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY()+1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()+1)));
            }
            else if(coord.getY() == Math.sqrt(App.getPositions().size()) - 1){
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY()-1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY())));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()-1)));
            }
            else{
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY()-1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY())));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY()+1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()+1)));
                positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()-1)));
            }
        }

        else if(coord.getY()==0){//arriba
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY())));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY()+1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()+1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY()+1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY())));
        }
        else if(coord.getY() == Math.sqrt(App.getPositions().size()) - 1){//abajo
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY()-1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY())));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY())));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY()-1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()-1)));
        }
        else{
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY()-1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY())));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()-1,coord.getY()+1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()+1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY()+1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY())));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX()+1,coord.getY()-1)));
            positionsOut.add(App.getPositionByCoordinate(new Coordinate(coord.getX(),coord.getY()-1)));
        }

        for(int i = 0; i < positionsOut.size(); i++){
            Position pos = positionsOut.get(i);
            if(pos.getValue() != "null"){
                positionsOut.remove(pos);
                i--;
            }
        }

        return positionsOut;
    }

    public static Position getPositionByCoordinate(Coordinate coord){
        for(Position pos : App.getPositions()){
            if(pos.getCoord().getX() == coord.getX() && pos.getCoord().getY() == coord.getY()){
                return pos;
            }
        }
        return null;
    }


    public static void printMap(){
        String out = "";
        for(int i = 0; i < App.getPositions().size(); i++){
            if(App.getPositions().get(i).getCoord().getX() == 0){
                out += "\n";
            }
            out += App.getPositions().get(i) + " ";
        }
        System.out.println(out);
    }
}
