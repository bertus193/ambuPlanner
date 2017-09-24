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
        CoordValue oldCoordPosition, newCoordPosition, destCoordPosition, mapOldCoordPosition, newDestinationPlaceCoordPosition;
        List<Node> newPathRoute;
        int oldIntPosition, newIntPosition;
        String firstMapValue;

        List<DestinationPlace> ambulancesReady = new ArrayList<>();

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

                    DestinationPlace destinationPlace = currentMap.getNearestAmbulance(oldCoordPosition.getX(), oldCoordPosition.getY());
                    ambulancesReady.add(destinationPlace);

                    System.out.println("New Patient: " + destinationPlace.getPathRoute() + " position: " + notification.getPatientPosition());
                    newPatientFound = true;
                }
            }
            if (newPatientFound == false) {

                for (int i = 0; i < ambulancesReady.size(); i++) {

                    DestinationPlace destinationPlace = ambulancesReady.get(i);
                    if (!destinationPlace.getPathRoute().isEmpty()) {

                        Node newPosition = destinationPlace.getPathRoute().get(0);


                        oldCoordPosition = destinationPlace.getCurrentPosition().getCoordValue();
                        newCoordPosition = newPosition.getCoordValue();
                        destCoordPosition = destinationPlace.getDestinationNode().getCoordValue();
                        oldIntPosition = currentMap.getNodePositionByCoord(oldCoordPosition.getX(), oldCoordPosition.getY());
                        newIntPosition = currentMap.getNodePositionByCoord(newCoordPosition.getX(), newCoordPosition.getY());

                        mapOldCoordPosition = currentMap.getNodes().get(oldIntPosition).getCoordValue();


                        System.out.println("TOTAL NODES: " + (currentMap.getNodes().size() - 1) + " OLD POS: " + oldIntPosition + " NEW POS: " + newIntPosition);

                        System.out.println("(" + oldCoordPosition.getX() + " " + oldCoordPosition.getY() + " - " + oldCoordPosition.getValue() + ") (" + newCoordPosition.getX() + " " + newCoordPosition.getY() + " - " + newCoordPosition.getValue() + ") (" + destCoordPosition.getX() + " " + destCoordPosition.getY() + " - " + destCoordPosition.getValue() + ")");

                        destinationPlace.setCurrentPosition(newPosition);


                        //Recalculate route
                        if (!oldCoordPosition.equals(newCoordPosition)) {
                            if (destinationPlace.isBackRoute()) {
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
                                destinationPlace.setPathRoute(newPathRoute);
                            } else {

                                if (!destinationPlace.isHospitalRoute()) {
                                    newDestinationPlaceCoordPosition = newPosition.getCoordValue();

                                    DestinationPlace hospitalDestinationPlace = currentMap.getNearestHospital(newDestinationPlaceCoordPosition.getX(), newDestinationPlaceCoordPosition.getY());

                                    hospitalDestinationPlace.setHospitalRoute(true);
                                    ambulancesReady.add(hospitalDestinationPlace);
                                    System.out.println("New DestinationPlace Hospital: " + hospitalDestinationPlace.getPathRoute());
                                }

                                /*if (destinationPlace.isHospitalRoute() && !destinationPlace.isBackRoute()) {
                                    newDestinationPlaceCoordPosition = destinationPlace.getStartNode().getCoordValue();

                                    newPathRoute = currentMap.findPath(newCoordPosition.getX(), newCoordPosition.getY(), newDestinationPlaceCoordPosition.getX(), newDestinationPlaceCoordPosition.getY());
                                    DestinationPlace backDestinationPlace = new DestinationPlace(newPathRoute, destinationPlace.getCurrentPosition(), destinationPlace.getStartNode());
                                    backDestinationPlace.setBackRoute(true);
                                    ambulancesReady.add(backDestinationPlace);
                                    System.out.println("New DestinationPlace Back: " + backDestinationPlace.getPathRoute());
                                }*/


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
