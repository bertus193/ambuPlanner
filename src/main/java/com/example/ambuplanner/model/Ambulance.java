package com.example.ambuplanner.model;

import java.util.List;

public class Ambulance {

    private List<AbstractNode> pathRoute;

    private AbstractNode destinationNode;

    private AbstractNode startNode;

    private AbstractNode currentPosition;

    private AbstractNode initAmbulancePosition;

    private boolean backRoute = false;

    private boolean hospitalRoute = false;

    private int identifier;

    public Ambulance(List<AbstractNode> pathRoute, AbstractNode startNode, AbstractNode destinationNode) {
        this.pathRoute = pathRoute;
        if (this.destinationNode == null) {
            this.destinationNode = destinationNode;
        }
        if (this.startNode == null) {
            this.startNode = startNode;
        }
        this.currentPosition = startNode;


    }

    public List<AbstractNode> getPathRoute() {
        return pathRoute;
    }

    public void setPathRoute(List<AbstractNode> pathRoute) {
        this.pathRoute = pathRoute;
    }

    public AbstractNode getDestinationNode() {
        return destinationNode;
    }
    
    public AbstractNode getStartNode() {
        return startNode;
    }

    public AbstractNode getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(AbstractNode currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isBackRoute() {
        return backRoute;
    }

    public void setBackRoute(boolean backRoute) {
        this.backRoute = backRoute;
    }

    public boolean isHospitalRoute() {
        return hospitalRoute;
    }

    public void setHospitalRoute(boolean hospitalRoute) {
        this.hospitalRoute = hospitalRoute;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public AbstractNode getInitAmbulancePosition() {
        return initAmbulancePosition;
    }

    public void setInitAmbulancePosition(Node initAmbulancePosition) {
        this.initAmbulancePosition = initAmbulancePosition;
    }
}
