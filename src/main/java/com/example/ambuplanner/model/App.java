package com.example.ambuplanner.model;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static List<AppMap> maps = new ArrayList<>();
    private static List<Notification> notifications = new ArrayList<>();

    private App(List<AppMap> maps, List<Notification> notifications) {
        App.maps = maps;
        App.notifications = notifications;
    }


    /**
     * createApp Create a global application with singleton
     *
     * @param notifications notifications provided by client
     */
    public static void createApp(List<AppMap> maps, List<Notification> notifications) {
        if (App.getMaps().isEmpty() && App.getNotifications().isEmpty()) {
            new App(maps, notifications);
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

    public static int calculateSize(JSONArray array) {
        int count;
        int out = 0;
        JSONArray jsonPosition;
        try {

            for (int countPosition = 0; countPosition < array.length(); countPosition++) {
                jsonPosition = array.getJSONArray(countPosition);
                count = 0;
                for (int y = 0; y < jsonPosition.length(); y++) {
                    count++;
                }
                if (out < count) {
                    out = count;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }


    public static void initApp(MapRequest map) {
        JSONArray jsonPosition;

        List<AbstractNode> initNodes = new ArrayList<>();
        int x = 0;
        try {
            JSONArray array = new JSONArray(map.getMapJSON());
            int size = calculateSize(array);
            for (int countPosition = 0; countPosition < array.length(); countPosition++) {
                jsonPosition = array.getJSONArray(countPosition);
                for (int y = 0; y < size; y++) {
                    if (y >= jsonPosition.length()) {
                        initNodes.add(new Node(x, y, "null"));
                    } else {
                        initNodes.add(new Node(x, y, jsonPosition.get(y).toString()));
                    }

                }
                x++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Notification> initNotifications = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(map.getNotificationsJSON());
            for (int countPosition = 0; countPosition < array.length(); countPosition++) {
                jsonPosition = array.getJSONArray(countPosition);
                Node node = new Node((Integer) jsonPosition.get(1), (Integer) jsonPosition.get(2), "P");
                initNotifications.add(new Notification((Integer) jsonPosition.get(0), node));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AppMap initMap = new AppMap(initNodes, 0);
        List<AppMap> mapList = new ArrayList<>();
        mapList.add(initMap);

        App.getMaps().clear();
        App.getNotifications().clear();

        App.createApp(mapList, initNotifications);

    }

    /**
     * Each iteration is necesary a new Map, this method generate them.
     *
     * @return int, position of this map in array.
     */
    public static int generateNewMap() {
        int startSize = App.getMaps().size() - 1;
        int endSize = startSize + 1;
        List<AbstractNode> nodes = App.copyValuesFromAppMap(App.getMaps().get(startSize));
        App.getMaps().add(new AppMap(nodes, endSize));
        System.out.println("Total Maps: " + (App.getMaps().size() - 1));
        return App.getMaps().size() - 1;
    }

    public static List<AbstractNode> copyValuesFromAppMap(AppMap mapForCopy) {
        List<AbstractNode> out = new ArrayList<>();

        for (AbstractNode node : mapForCopy.getNodes()) {
            AbstractNode node2 = new Node(node.getCoordValue().getX(), node.getCoordValue().getY(), node.getCoordValue().getValue());
            out.add(node2);
        }

        return out;
    }

    public static String mapsToJson() {
        Gson gson = new Gson();
        String out;
        out = gson.toJson(App.getMaps());
        return out;
    }


}
