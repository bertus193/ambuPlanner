package com.example.ambuplanner.model;

public class Node extends AbstractNode {

    public Node(int xPosition, int yPosition, String value) {
        super(xPosition, yPosition, value);
        // do other init stuff
    }

    public void sethCosts(AbstractNode endNode) {
        this.sethCosts((absolute(this.getCoordValue().getX() - endNode.getCoordValue().getX())
                + absolute(this.getCoordValue().getY() - endNode.getCoordValue().getY()))
                * BASICMOVEMENTCOST);
    }

    private int absolute(int a) {
        return a > 0 ? a : -a;
    }
}
