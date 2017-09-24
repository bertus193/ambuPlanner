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
        CoordValue oldCoordPosition, newCoordPosition, destCoordPosition, mapOldCoordPosition;
        List<Node> newPathRoute;
        int oldIntPosition, newIntPosition;
        Node startNode;
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

                        mapOldCoordPosition = currentMap.getNodes().get(oldIntPosition).getCoordValue();


                        System.out.println("TOTAL NODES: " + (currentMap.getNodes().size() - 1) + " OLD POS: " + oldIntPosition + " NEW POS: " + newIntPosition);

                        System.out.println("(" + oldCoordPosition.getX() + " " + oldCoordPosition.getY() + " - " + oldCoordPosition.getValue() + ") (" + newCoordPosition.getX() + " " + newCoordPosition.getY() + " - " + newCoordPosition.getValue() + ") (" + destCoordPosition.getX() + " " + destCoordPosition.getY() + " - " + destCoordPosition.getValue() + ")");

                        ambulance.setCurrentPosition(newPosition);


                        //Recalculate route
                        if (!oldCoordPosition.equals(newCoordPosition)) {
                            if (ambulance.isBackRoute()) {
                                currentMap.getNodes().get(newIntPosition).getCoordValue().setValue(destCoordPosition.getValue());
                            } else {
                                currentMap.getNodes().get(newIntPosition).getCoordValue().setValue(mapOldCoordPosition.getValue());
                                System.out.println("(" + currentMap.getNodes().get(newIntPosition).getCoordValue().getX() + " " + currentMap.getNodes().get(newIntPosition).getCoordValue().getY() + " - " + currentMap.getNodes().get(newIntPosition).getCoordValue().getValue() + ")");
                            }


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

                                if (!ambulance.isBackRoute()) {
                                    startNode = ambulance.getStartNode();

                                    newPathRoute = currentMap.findPath(newCoordPosition.getX(), newCoordPosition.getY(),
                                            startNode.getCoordValue().getX(), startNode.getCoordValue().getY());
                                    Ambulance backAmbulance = new Ambulance(newPathRoute, ambulance.getCurrentPosition(), startNode);
                                    backAmbulance.setBackRoute(true);
                                    ambulancesReady.add(backAmbulance);
                                    System.out.println("New Ambulance Back: " + backAmbulance.getPathRoute());
                                }


                                ambulancesReady.remove(i);
                            }

                        }
                    }

                }

            } else {
                newPatientFound = false;
            }

            //System.out.println(turn);
            currentMap.printMap();
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
