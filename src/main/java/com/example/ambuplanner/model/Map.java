package com.example.ambuplanner.model;

import java.util.LinkedList;
import java.util.List;

public class Map<T extends AbstractNode> {

    /**
     * width + 1 is size of first dimension of nodes.
     */
    private int width;
    /**
     * height + 1 is size of second dimension of nodes.
     */
    private int height;

    /**
     * a Factory to create instances of specified nodes.
     */
    private NodeFactory nodeFactory;

    /**
     * constructs a squared map with given width and hight.
     * <p>
     * The nodes will be instanciated througth the given nodeFactory.
     *
     * @param width       width of map
     * @param height      Height of map
     * @param nodeFactory constructor
     */
    public Map(int width, int height, NodeFactory nodeFactory) {
        // TODO check parameters. width and height should be > 0.
        this.nodeFactory = nodeFactory;
        this.width = width - 1;
        this.height = height - 1;
    }


    // variables needed for path finding

    /**
     * list containing nodes not visited but adjacent to visited nodes.
     */
    private List<T> openList;
    /**
     * list containing nodes already visited/taken care of.
     */
    private List<T> closedList;
    /**
     * done finding path?
     */
    private boolean done = false;

    /**
     * finds an allowed path from start to goal coordinates on this map.
     * <p>
     * This method uses the A* algorithm. The hCosts value is calculated in
     * the given Node implementation.
     * <p>
     * This method will return a LinkedList containing the start node at the
     * beginning followed by the calculated shortest allowed path ending
     * with the end node.
     * <p>
     * If no allowed path exists, an empty list will be returned.
     * <p>
     * <p>
     * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
     *
     * @param oldX start X
     * @param oldY start Y
     * @param newX end X
     * @param newY end Y
     * @return List path list
     */
    public final List<T> findPath(int oldX, int oldY, int newX, int newY) {
        openList = new LinkedList<T>();
        closedList = new LinkedList<T>();
        openList.add((T) App.getNodePosition(oldX, oldY)); // add starting node to open list

        done = false;
        T current;
        while (!done) {
            current = lowestFInOpen(); // get node with lowest fCosts from openList
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list

            //System.out.println("(" + current.getxPosition() + " " + current.getyPosition() + ") (" + newX + " " + newY + ")");

            if ((current.getxPosition() == newX) && (current.getyPosition() == newY)) { // found goal
                return calcPath((T) App.getNodePosition(oldX, oldY), current);
            }

            // for all adjacent nodes:
            List<T> neighbors = getNeighbors(current.getxPosition(), current.getyPosition());
            for (int i = 0; i < neighbors.size(); i++) {
                T currentAdj = neighbors.get(i);
                if (!openList.contains(currentAdj)) { // node is not in openList
                    currentAdj.setPrevious(current); // set current node as previous for this node
                    currentAdj.sethCosts(App.getNodePosition(newX, newY)); // set h costs of this node (estimated costs to goal)
                    currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    openList.add(currentAdj); // add node to openList
                } else { // node is in openList
                    if (currentAdj.getgCosts() > currentAdj.calculategCosts(current)) { // costs from current node are cheaper than previous costs
                        currentAdj.setPrevious(current); // set current node as previous for this node
                        currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    }
                }
            }

            if (openList.isEmpty()) { // no path exists
                return new LinkedList<T>(); // return empty list
            }
        }
        return null; // unreachable
    }

    /**
     * calculates the found path between two points according to
     * their given <code>previousNode</code> field.
     *
     * @param start
     * @param goal
     * @return
     */
    private List<T> calcPath(T start, T goal) {
        // TODO if invalid nodes are given (eg cannot find from
        // goal to start, this method will result in an infinite loop!)
        LinkedList<T> path = new LinkedList<T>();

        T curr = goal;
        boolean done = false;
        while (!done) {
            path.addFirst(curr);
            curr = (T) curr.getPrevious();
            if (curr.equals(start)) {
                done = true;
            }
        }
        return path;
    }

    /**
     * returns the node with the lowest fCosts.
     *
     * @return
     */
    private T lowestFInOpen() {
        // TODO currently, this is done by going through the whole openList!
        T cheapest = openList.get(0);
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).getfCosts() < cheapest.getfCosts()) {
                cheapest = openList.get(i);
            }
        }
        return cheapest;
    }


    /**
     * returns a LinkedList with nodes adjacent to the given node.
     * if those exist, are walkable and are not already in the closedList!
     */
    @SuppressWarnings("unchecked")
    public List<T> getNeighbors(int posx, int posy) {
        List<T> neighbors = new LinkedList<T>();

        T temp;
        if (posx > 0) {
            temp = (T) App.getNodePosition(posx - 1, posy);
            if (temp.getValue().equals("null") && !closedList.contains(temp)) {
                neighbors.add(temp);
            }
        }

        if (posx < width) {
            temp = (T) App.getNodePosition(posx + 1, posy);
            if (temp.getValue().equals("null") && !closedList.contains(temp)) {
                neighbors.add(temp);
            }
        }

        if (posy > 0) {
            temp = (T) App.getNodePosition(posx, posy - 1);
            if (temp.getValue().equals("null") && !closedList.contains(temp)) {
                neighbors.add(temp);
            }
        }

        if (posy < Math.sqrt(App.getNodes().size())) {
            temp = (T) App.getNodePosition(posx, posy + 1);
            if (temp.getValue().equals("null") && !closedList.contains(temp)) {
                neighbors.add(temp);
            }
        }

        System.out.println("N: (" + posx + " " + posy + ") " + neighbors);

        return neighbors;
    }

}
