package com.example.ambuplanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class AppLaunch extends TimerTask {

    public AppLaunch() {

    }

    @Override
    public void run() {

    }

    public void launch() {
        int turn = 0; // to infinite, wait totalNotifications
        boolean isEnd = false;
        int totalNotifications = 0; //count notification to finish turns

        boolean newPatientFound = false;

        //patient position
        CoordValue oldCoordPosition, newCoordPosition, destCoordPosition;
        List<Node> newPathRoute;
        int oldIntPosition, newIntPosition;
        String firstMapValue;

        List<Ambulance> ambulancesReady = new ArrayList<>();

        while (!isEnd) {
            int mapPosition = App.generateNewMap();
            AppMap currentMap = App.getMaps().get(mapPosition);

            for (Notification notification : App.getNotifications()) {
                if (notification.getOrderNumber() == turn) { //First time
                    totalNotifications++;
                    //Set patient position
                    oldCoordPosition = notification.getPatientPosition().getCoordValue();
                    oldIntPosition = currentMap.getNodePositionByCoord(oldCoordPosition.getX(), oldCoordPosition.getY());
                    currentMap.getNodes().get(oldIntPosition).getCoordValue().setValue("P");

                    Ambulance ambulance = currentMap.getNearestAmbulance(oldCoordPosition.getX(), oldCoordPosition.getY());
                    ambulancesReady.add(ambulance);

                    System.out.println("New Patient: " + ambulance.getPathRoute() + " position: " + notification.getPatientPosition());
                    newPatientFound = true;
                }
            }
            if (newPatientFound == false) {

                for (int i = 0; i < ambulancesReady.size(); i++) {

                    Ambulance ambulance = ambulancesReady.get(i);
                    if (!ambulance.getPathRoute().isEmpty()) {

                        Node newPosition = ambulance.getPathRoute().get(0);


                        oldCoordPosition = ambulance.getCurrentPosition().getCoordValue();
                        newCoordPosition = newPosition.getCoordValue();
                        destCoordPosition = ambulance.getDestinationNode().getCoordValue();
                        oldIntPosition = currentMap.getNodePositionByCoord(oldCoordPosition.getX(), oldCoordPosition.getY());
                        newIntPosition = currentMap.getNodePositionByCoord(newCoordPosition.getX(), newCoordPosition.getY());

                        System.out.println("(" + oldCoordPosition.getX() + " " + oldCoordPosition.getY() + ") (" + newCoordPosition.getX() + " " + newCoordPosition.getY() + ") (" + destCoordPosition.getX() + " " + destCoordPosition.getY() + ")");

                        ambulance.setCurrentPosition(newPosition);


                        //Recalculate route
                        if (!oldCoordPosition.equals(newCoordPosition)) {
                            currentMap.getNodes().get(newIntPosition).getCoordValue().setValue(oldCoordPosition.getValue());

                            firstMapValue = App.getMaps().get(0).getNodes().get(oldIntPosition).getCoordValue().getValue();
                            if (firstMapValue.equals("H") || firstMapValue.equals("B")) {
                                currentMap.getNodes().get(oldIntPosition).getCoordValue().setValue(firstMapValue);
                            } else {
                                currentMap.getNodes().get(oldIntPosition).getCoordValue().setValue("null");
                            }


                            if (!newCoordPosition.equals(destCoordPosition)) {
                                newPathRoute = currentMap.findPath(newCoordPosition.getX(), newCoordPosition.getY(), destCoordPosition.getX(), destCoordPosition.getY());
                                ambulance.setPathRoute(newPathRoute);
                            } else {
                                ambulancesReady.remove(i);
                            }

                        }
                    } else {
                        ambulancesReady.remove(i);
                    }

                }

            } else {
                newPatientFound = false;
            }

            //System.out.println(turn);
            App.getMaps().get(mapPosition).printMap();
            turn++;

            if (totalNotifications >= App.getNotifications().size() && ambulancesReady.isEmpty()) {
                isEnd = true;
            }
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
