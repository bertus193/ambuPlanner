package com.example.ambuplanner.model;

import java.util.List;

public class Ambulance {

    private List<Node> pathRoute;

    private Node destinationNode;

    private Node startNode;

    private Node currentPosition;

    public Ambulance(List<Node> pathRoute, Node startNode, Node destinationNode) {
        this.pathRoute = pathRoute;
        if (this.destinationNode == null) {
            this.destinationNode = destinationNode;
        }
        if (this.startNode == null) {
            this.startNode = startNode;
        }
        this.currentPosition = startNode;
    }

    public List<Node> getPathRoute() {
        return pathRoute;
    }

    public void setPathRoute(List<Node> pathRoute) {
        this.pathRoute = pathRoute;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Node currentPosition) {
        this.currentPosition = currentPosition;
    }
}
