package com.example.ambuplanner.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class App {

    //private static List<Position> positions = new ArrayList<>();

    private static List<AbstractNode> nodes = new ArrayList<>();

    private App(List<AbstractNode> nodes) {
        App.nodes = nodes;
    }

    public static List<AbstractNode> getNodes() {
        return App.nodes;
    }

    public static void setNodes(List<AbstractNode> positions) {
        if (App.getNodes().isEmpty()) {
            new App(positions);
        } else {
            System.err.println("Ya se ha instalaciado la app");
        }
    }

    public App clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public static AbstractNode getNodePosition(int x, int y) {
        for (AbstractNode actualNode : App.getNodes()) {
            if (x == actualNode.getxPosition() && y == actualNode.getyPosition()) {
                System.out.println(actualNode);
                return actualNode;
            }
        }
        return null;
    }

    public static void initNodePositions() {
        String map = "[[1, null, null, null, null, null, null, null, null, 4], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, null, null, null, null, null, null, null, null, null], [null, \"B\", \"H\", 2, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"H\", \"B\", null], [null, null, null, null, null, null, null, 3, null, null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"H\", null, \"B\", \"B\", null],[5, null, null, null, null, null, null, 6, null, null]]";
        JSONArray jsonPosition;
        List<AbstractNode> initNodes = new ArrayList<>();
        int x = 0;
        try {
            JSONArray array = new JSONArray(map);
            for (int countPosition = 0; countPosition < array.length(); countPosition++) {
                jsonPosition = array.getJSONArray(countPosition);
                for (int y = 0; y < jsonPosition.length(); y++) {
                    initNodes.add(new MyNode(x, y, jsonPosition.get(y).toString()));
                }
                x++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        App.setNodes(initNodes);
    }


    public static void printMap() {
        String out = "";
        for (int i = 0; i < App.getNodes().size(); i++) {
            if (App.getNodes().get(i).getxPosition() == 0) {
                out += "\n";
            }
            String value = App.getNodes().get(i).getValue();
            if (value == "null") {
                out += " ";
            } else {
                out += value;
            }
            out += " ";
        }
        System.out.println(out);
    }
}
