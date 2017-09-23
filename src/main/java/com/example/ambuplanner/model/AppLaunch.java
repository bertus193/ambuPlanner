package com.example.ambuplanner.model;

public class AppLaunch {

    public AppLaunch() {

    }

    public void launch() {
        for (Notification notification : App.getNotifications()) {
            int mapPosition = App.generateNewMap();
        }
        
        this.printMaps();
    }

    public void printMaps() {
        Map<AbstractNode> myMap;
        for (int i = 0; i < App.getMaps().size(); i++) {
            myMap = new Map<>(i);
            myMap.printMap();

            System.out.println("##################");
        }
    }

}
