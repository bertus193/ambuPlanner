package com.example.ambuplanner.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static List<AbstractNode> nodes = new ArrayList<>();
    private static List<Notification> notifications = new ArrayList<>();

    private App(List<AbstractNode> nodes, List<Notification> notifications) {
        App.nodes = nodes;
        App.notifications = notifications;
    }

    public static List<AbstractNode> getNodes() {
        return App.nodes;
    }

    public static void createApp(List<AbstractNode> positions, List<Notification> notifications) {
        if (App.getNodes().isEmpty()) {
            new App(positions, notifications);
        } else {
            System.out.println("Ya se ha instalaciado la app");
        }
    }

    public App clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    public static void initApp() {
        String mapJSON = "[[1, null, null, null, null, null, null, null, null, 4], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, null, null, null, null, null, null, null, null, null], [null, \"B\", \"H\", 2, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"H\", \"B\", null], [null, null, null, null, null, null, null, 3, null, null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"B\", null], [null, \"B\", \"B\", null, \"B\", \"B\", null, \"B\", \"H\", null, \"B\", \"B\", null],[5, null, null, null, null, null, null, 6, null, null]]";
        String notificationsJSON = "[[1, 4, 4], [5, 1, 7], [20, 4, 9]]";
        JSONArray jsonPosition;

        List<AbstractNode> initNodes = new ArrayList<>();
        int x = 0;
        try {
            JSONArray array = new JSONArray(mapJSON);
            for (int countPosition = 0; countPosition < array.length(); countPosition++) {
                jsonPosition = array.getJSONArray(countPosition);
                for (int y = 0; y < jsonPosition.length(); y++) {
                    initNodes.add(new Node(x, y, jsonPosition.get(y).toString()));
                }
                x++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Notification> initNotifications = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(notificationsJSON);
            for (int countPosition = 0; countPosition < array.length(); countPosition++) {
                jsonPosition = array.getJSONArray(countPosition);
                NodePosition pos = new NodePosition((Integer) jsonPosition.get(1), (Integer) jsonPosition.get(2), "P");
                initNotifications.add(new Notification((Integer) jsonPosition.get(0), pos));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        App.createApp(initNodes, initNotifications);
    }


}
