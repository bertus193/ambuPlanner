package com.example.ambuplanner.model;

public class MyNode extends AbstractNode {

    public MyNode(int xPosition, int yPosition, String value) {
        super(xPosition, yPosition, value);
        // do other init stuff
    }

    public void sethCosts(AbstractNode endNode) {
        this.sethCosts((absolute(this.getxPosition() - endNode.getxPosition())
                + absolute(this.getyPosition() - endNode.getyPosition()))
                * BASICMOVEMENTCOST);
    }

    private int absolute(int a) {
        return a > 0 ? a : -a;
    }
}
