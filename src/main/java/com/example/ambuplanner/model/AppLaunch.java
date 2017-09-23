package com.example.ambuplanner.model;

public class AppLaunch {

    public AppLaunch() {

    }

    public void launch() {
        for (Notification notification : App.getNotifications()) {
            int mapPosition = App.generateNewMap();
            //Node node = App.getMaps().get(mapPosition).
        }

        this.printMaps();
    }

    public void printMaps() {
        for (AppMap map : App.getMaps()) {
            map.printMap();
            System.out.println("##################");
        }
    }

}
