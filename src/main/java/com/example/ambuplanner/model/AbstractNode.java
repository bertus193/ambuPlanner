package com.example.ambuplanner.model;

public abstract class AbstractNode {

    /**
     * costs to move sideways from one square to another.
     */
    protected static final int BASICMOVEMENTCOST = 1;

    private int xPosition;
    private int yPosition;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    // for pathfinding:

    /**
     * the previous AbstractNode of this one on the currently calculated path.
     */
    private AbstractNode previous;

    /**
     * optional extra penalty.
     */
    private int movementPanelty;

    //private int fCosts; // g + h costs

    /**
     * calculated costs from start AbstractNode to this AbstractNode.
     */
    private int gCosts;

    /**
     * estimated costs to get from this AbstractNode to end AbstractNode.
     */
    private int hCosts;

    /**
     * constructs a walkable AbstractNode with given coordinates.
     *
     * @param xPosition
     * @param yPosition
     */
    public AbstractNode(int xPosition, int yPosition, String value) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.value = value;
        this.movementPanelty = 0;
    }


    /**
     * sets x and y coordinates.
     *
     * @param x
     * @param y
     */
    public void setCoordinates(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    /**
     * @return the xPosition
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * @return the yPosition
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * returns the node set as previous node on the current path.
     *
     * @return the previous
     */
    public AbstractNode getPrevious() {
        return previous;
    }

    /**
     * @param previous the previous to set
     */
    public void setPrevious(AbstractNode previous) {
        this.previous = previous;
    }

    /**
     * sets a general penalty for the movement on this node.
     *
     * @param movementPanelty the movementPanelty to set
     */
    public void setMovementPanelty(int movementPanelty) {
        this.movementPanelty = movementPanelty;
    }

    /**
     * returns <code>gCosts</code> + <code>hCosts</code>.
     * <p>
     *
     * @return the fCosts
     */
    public int getfCosts() {
        return gCosts + hCosts;
    }

    /**
     * returns the calculated costs from start AbstractNode to this AbstractNode.
     *
     * @return the gCosts
     */
    public int getgCosts() {
        return gCosts;
    }

    /**
     * sets gCosts to <code>gCosts</code> plus <code>movementPanelty</code>
     * for this AbstractNode.
     *
     * @param gCosts the gCosts to set
     */
    private void setgCosts(int gCosts) {
        this.gCosts = gCosts + movementPanelty;
    }

    /**
     * sets gCosts to <code>gCosts</code> plus <code>movementPanelty</code>
     * for this AbstractNode given the previous AbstractNode as well as the basic cost
     * from it to this AbstractNode.
     *
     * @param previousAbstractNode
     * @param basicCost
     */
    public void setgCosts(AbstractNode previousAbstractNode, int basicCost) {
        setgCosts(previousAbstractNode.getgCosts() + basicCost);
    }

    /**
     * sets gCosts to <code>gCosts</code> plus <code>movementPanelty</code>
     * for this AbstractNode given the previous AbstractNode.
     * <p>
     * It will assume <code>BASICMOVEMENTCOST</code> as the cost from
     * <code>previousAbstractNode</code> to itself if the movement is not diagonally,
     * Weather or not it is diagonally is set in the Map class method which
     * finds the adjacent AbstractNodes.
     *
     * @param previousAbstractNode
     */
    public void setgCosts(AbstractNode previousAbstractNode) {
        setgCosts(previousAbstractNode, BASICMOVEMENTCOST);
    }

    /**
     * calculates - but does not set - g costs.
     * <p>
     * It will assume <code>BASICMOVEMENTCOST</code> as the cost from
     * <code>previousAbstractNode</code> to itself if the movement is not diagonally,
     * Weather or not it is diagonally is set in the Map class method which
     * finds the adjacent AbstractNodes.
     *
     * @param previousAbstractNode
     * @return gCosts
     */
    public int calculategCosts(AbstractNode previousAbstractNode) {
        return (previousAbstractNode.getgCosts()
                + BASICMOVEMENTCOST + movementPanelty);
    }

    /**
     * calculates - but does not set - g costs, adding a movementPanelty.
     *
     * @param previousAbstractNode
     * @param movementCost         costs from previous AbstractNode to this AbstractNode.
     * @return gCosts
     */
    public int calculategCosts(AbstractNode previousAbstractNode, int movementCost) {
        return (previousAbstractNode.getgCosts() + movementCost + movementPanelty);
    }

    /**
     * returns estimated costs to get from this AbstractNode to end AbstractNode.
     *
     * @return the hCosts
     */
    public int gethCosts() {
        return hCosts;
    }

    /**
     * sets hCosts.
     *
     * @param hCosts the hCosts to set
     */
    protected void sethCosts(int hCosts) {
        this.hCosts = hCosts;
    }

    /**
     * calculates hCosts for this AbstractNode to a given end AbstractNode.
     * Uses Manhatten method.
     *
     * @param endAbstractNode
     */
    public abstract void sethCosts(AbstractNode endAbstractNode);


    /*
     * @return the movementPanelty
     */
    private int getMovementPanelty() {
        return movementPanelty;
    }

    /**
     * returns a String containing the coordinates, as well as h, f and g
     * costs.
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + getxPosition() + ", " + getyPosition() + " V: " + getValue() + ")";
        //: h: "+ gethCosts() + " g: " + getgCosts() + " f: " + getfCosts();
    }

    /**
     * returns weather the coordinates of AbstractNodes are equal.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractNode other = (AbstractNode) obj;
        if (this.xPosition != other.xPosition) {
            return false;
        }
        if (this.yPosition != other.yPosition) {
            return false;
        }
        return true;
    }

    /**
     * returns hash code calculated with coordinates.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.xPosition;
        hash = 17 * hash + this.yPosition;
        return hash;
    }

}
