package com.example.ambuplanner.model;

public class Node extends AbstractNode {

    public Node(int xPosition, int yPosition, String value) {
        super(xPosition, yPosition, value);
        // do other init stuff
    }

    public void sethCosts(AbstractNode endNode) {
        this.sethCosts((absolute(this.getNodePosition().getX() - endNode.getNodePosition().getX())
                + absolute(this.getNodePosition().getY() - endNode.getNodePosition().getY()))
                * BASICMOVEMENTCOST);
    }

    private int absolute(int a) {
        return a > 0 ? a : -a;
    }
}
