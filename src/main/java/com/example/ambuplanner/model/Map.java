package com.example.ambuplanner.model;

import java.util.LinkedList;
import java.util.List;

public class Map<T extends AbstractNode> {

    public Map() {
    }

    // list containing nodes not visited but adjacent to visited nodes.
    private List<T> openList;
    // list containing nodes already visited/taken care of.
    private List<T> closedList;

    /**
     * finds an allowed path from start to goal coordinates on this map.
     * <p>
     * This method uses the A* algorithm. The hCosts value is calculated in
     * the given Node implementation.
     * <p>
     * This method will return a LinkedList containing the start node at the
     * beginning followed by the calculated shortest allowed path ending
     * with the end node.
     *
     * @param oldX start X
     * @param oldY start Y
     * @param newX end X
     * @param newY end Y
     * @return List path list
     */
    @SuppressWarnings("unchecked")
    public final List<T> findPath(int oldX, int oldY, int newX, int newY) {
        openList = new LinkedList<>();
        closedList = new LinkedList<>();
        openList.add((T) App.getNodePosition(oldX, oldY)); // add starting node to open list

        T current;
        while (true) {
            current = lowestFInOpen(); // get node with lowest fCosts from openList
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list

            //System.out.println("(" + current.getNodePosition().getX() + " " + current.getNodePosition().getY() + ") (" + newX + " " + newY + ")");

            if ((current.getNodePosition().getX() == newX) && (current.getNodePosition().getY() == newY)) { // found goal
                return calcPath((T) App.getNodePosition(oldX, oldY), current);
            }

            // for all adjacent nodes:
            List<T> neighbors = getNeighbors(current.getNodePosition().getX(), current.getNodePosition().getY());
            for (T currentAdj : neighbors) {
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
                return new LinkedList<>(); // return empty list
            }
        }
    }

    /**
     * calculates the found path between two points according to
     * their given <code>previousNode</code> field.
     *
     * @param start start node
     * @param goal  end node
     * @return List nodes to find end
     */
    @SuppressWarnings("unchecked")
    private List<T> calcPath(T start, T goal) {
        // goal to start, this method will result in an infinite loop!)
        LinkedList<T> path = new LinkedList<>();

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
     * @return T node with lowest cost
     */
    private T lowestFInOpen() {
        T cheapest = openList.get(0);
        for (T anOpenList : openList) {
            if (anOpenList.getfCosts() < cheapest.getfCosts()) {
                cheapest = anOpenList;
            }
        }
        return cheapest;
    }


    /**
     * returns a LinkedList with nodes adjacent to the given node.
     * if those exist, are walkable and are not already in the closedList!
     */
    @SuppressWarnings("unchecked")
    private List<T> getNeighbors(int posx, int posy) {
        List<T> neighbors = new LinkedList<>();

        T temp;
        if (posx > 0) {
            temp = (T) App.getNodePosition(posx - 1, posy);
            if (temp != null && temp.getNodePosition().getValue().equals("null") && !closedList.contains(temp)) {
                neighbors.add(temp);
            }
        }

        if (posx < Math.sqrt(App.getNodes().size())) {
            temp = (T) App.getNodePosition(posx + 1, posy);
            if (temp != null && temp.getNodePosition().getValue().equals("null") && !closedList.contains(temp)) {
                neighbors.add(temp);
            }
        }

        if (posy > 0) {
            temp = (T) App.getNodePosition(posx, posy - 1);
            if (temp != null && temp.getNodePosition().getValue().equals("null") && !closedList.contains(temp)) {
                neighbors.add(temp);
            }
        }

        if (posy < Math.sqrt(App.getNodes().size())) {
            temp = (T) App.getNodePosition(posx, posy + 1);
            if (temp != null && temp.getNodePosition().getValue().equals("null") && !closedList.contains(temp)) {
                neighbors.add(temp);
            }
        }

        return neighbors;
    }

}
