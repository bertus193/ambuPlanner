package com.example.ambuplanner.model;

public class AppLaunch {

    public AppLaunch() {

    }

    public void launch() {
        for (Notification notification : App.getNotifications()) {
            int mapPosition = App.generateNewMap();
            Node node = (Node) App.getMaps().get(mapPosition)
                    .getNearestAmbulance(notification.getPatientPosition().getCoordValue().getX(),
                            notification.getPatientPosition().getCoordValue().getY());
            App.getMaps().get(mapPosition).printMap();
            System.out.println("Node: " + node + " " + notification.getPatientPosition());
        }

        //this.printMaps();
    }

    public void printMaps() {
        for (AppMap map : App.getMaps()) {
            map.printMap();
            System.out.println("#####################################");
        }
    }

}
