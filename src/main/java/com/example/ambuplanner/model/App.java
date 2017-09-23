package com.example.ambuplanner.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static List<AppMap> maps = new ArrayList<>();
    private static List<Notification> notifications = new ArrayList<>();

    private App(List<Notification> notifications) {
        App.notifications = notifications;
    }


    public static void createApp(List<Notification> notifications) {
        if (App.getNotifications().isEmpty()) {
            new App(notifications);
        } else {
            System.out.println("Ya se ha instalaciado la app");
        }
    }

    public static List<Notification> getNotifications() {
        return notifications;
    }

    public static List<AppMap> getMaps() {
        return maps;
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
                CoordValue pos = new CoordValue((Integer) jsonPosition.get(1), (Integer) jsonPosition.get(2), "P");
                initNotifications.add(new Notification((Integer) jsonPosition.get(0), pos));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppMap initMap = new AppMap(initNodes, 0);
        App.createApp(initNotifications);
        App.getMaps().add(initMap);

    }

    public static int generateNewMap() {
        int startSize = App.getMaps().size() - 1;
        int endSize = startSize + 1;
        App.getMaps().add(new AppMap(App.getMaps().get(startSize).getNodes(), endSize));
        return App.getMaps().size() - 1;
    }


}
