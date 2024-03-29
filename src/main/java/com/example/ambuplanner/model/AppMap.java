package com.example.ambuplanner.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class AppMap {

    private List<AbstractNode> nodes = new ArrayList<>();

    public AppMap(List<AbstractNode> nodes, int position) {
        this.nodes = nodes;
        this.mapPosition = position;
    }

    public List<AbstractNode> getNodes() {
        return this.nodes;
    }

    private int mapPosition;


    // list containing nodes not visited but adjacent to visited nodes.
    private List<AbstractNode> openList;
    // list containing nodes already visited/taken care of.
    private List<AbstractNode> closedList;

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
     * @param startX start X
     * @param startY start Y
     * @param endX   end X
     * @param endY   end Y
     * @return List path list
     */
    @SuppressWarnings("unchecked")
    public List<AbstractNode> findPath(int startX, int startY, int endX, int endY) {
        openList = new LinkedList<>();
        closedList = new LinkedList<>();
        openList.add(this.getNodeByCoord(startX, startY)); // add starting node to open list

        CoordValue endCoord = new CoordValue(endX, endY, "");

        AbstractNode current;
        while (true) {
            current = lowestFInOpen(); // get node with lowest fCosts from openList
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list

            //System.out.println("(" + current.getCoordValue().getX() + " " + current.getCoordValue().getY() + ") (" + endX + " " + endY + ")");

            if (current.getCoordValue().equals(endCoord)) { // found goal
                return calcPath(this.getNodeByCoord(startX, startY), current);
            }

            // for all adjacent nodes:
            List<AbstractNode> neighbors = getNeighbors(current.getCoordValue().getX(), current.getCoordValue().getY(), endCoord);
            for (AbstractNode currentAdj : neighbors) {
                if (!openList.contains(currentAdj)) { // node is not in openList
                    currentAdj.setPrevious(current); // set current node as previous for this node
                    currentAdj.sethCosts(this.getNodeByCoord(endX, endY)); // set h costs of this node (estimated costs to goal)
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
    private List<AbstractNode> calcPath(AbstractNode start, AbstractNode goal) {
        // goal to start, this method will result in an infinite loop!)
        LinkedList<AbstractNode> path = new LinkedList<>();

        AbstractNode curr = goal;
        boolean done = false;
        while (!done) {
            path.addFirst(curr);
            curr = curr.getPrevious();
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
    private AbstractNode lowestFInOpen() {
        AbstractNode cheapest = openList.get(0);
        for (AbstractNode anOpenList : openList) {
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
    private List<AbstractNode> getNeighbors(int posx, int posy, CoordValue endPosition) {
        List<AbstractNode> neighbors = new LinkedList<>();

        AbstractNode temp;
        if (posx > 0) {
            temp = this.getNodeByCoord(posx - 1, posy);
            if (temp != null && temp.getCoordValue().equals(endPosition) || (temp != null && temp.getCoordValue().getValue().equals("null") && !closedList.contains(temp))) {
                neighbors.add(temp);
            }
        }

        if (posx < this.countLenght()) {
            temp = this.getNodeByCoord(posx + 1, posy);
            if (temp != null && temp.getCoordValue().equals(endPosition) || (temp != null && temp.getCoordValue().getValue().equals("null") && !closedList.contains(temp))) {
                neighbors.add(temp);
            }
        }

        if (posy > 0) {
            temp = this.getNodeByCoord(posx, posy - 1);
            if (temp != null && temp.getCoordValue().equals(endPosition) || (temp != null && temp.getCoordValue().getValue().equals("null") && !closedList.contains(temp))) {
                neighbors.add(temp);
            }
        }

        if (posy < this.countLenght()) {
            temp = this.getNodeByCoord(posx, posy + 1);
            if (temp != null && temp.getCoordValue().equals(endPosition) || (temp != null && temp.getCoordValue().getValue().equals("null") && !closedList.contains(temp))) {
                neighbors.add(temp);
            }
        }

        return neighbors;
    }

    public int countLenght() {
        int out = this.getNodes().size();
        for (int i = 0; i < this.getNodes().size(); i++) {
            if (this.getNodes().get(i).getCoordValue().getX() == 1 && this.getNodes().get(i).getCoordValue().getY() == 0) {
                return i + 1;
            }
        }
        return out;
    }

    public AbstractNode getNodeByCoord(int x, int y) {
        for (AbstractNode actualNode : this.getNodes()) {
            if (x == actualNode.getCoordValue().getX() && y == actualNode.getCoordValue().getY()) {
                return actualNode;
            }
        }
        return null;
    }

    public int getNodePositionByCoord(int x, int y) {
        int out = -1;
        for (int i = 0; i < getNodes().size(); i++) {
            if (x == getNodes().get(i).getCoordValue().getX() && y == getNodes().get(i).getCoordValue().getY()) {
                out = i;
            }
        }
        return out;
    }

    public void printMap() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < App.getMaps().get(mapPosition).getNodes().size(); i++) {
            if (getNodes().get(i).getCoordValue().getY() == 0) {
                out.append("\n");
            }
            String value = getNodes().get(i).getCoordValue().getValue();
            if (Objects.equals(value, "null")) {
                out.append(" ");
            } else {
                out.append(value);
            }
            out.append(" ");
        }
        System.out.println(out);
    }

    public List<Node> getAmbulances() {
        List<Node> ambulances = new ArrayList<>();

        for (AbstractNode node : getNodes()) {
            if (StringUtils.isNumeric(node.getCoordValue().getValue())) {
                ambulances.add((Node) node);
            }
        }

        return ambulances;
    }

    public List<Node> getHospitals() {
        List<Node> hospitals = new ArrayList<>();

        for (AbstractNode node : getNodes()) {
            if (node.getCoordValue().getValue().equals("H")) {
                hospitals.add((Node) node);
            }
        }

        return hospitals;
    }

    /**
     * @return position of ambulance near
     */
    public Ambulance getNearestAmbulance(int posx, int posy) {
        Ambulance out = null;
        List<AbstractNode> routePath;
        int actualMax = App.getMaps().get(mapPosition).getNodes().size();

        Node patientNode = new Node(posx, posy, "P");

        List<Ambulance> movements = new ArrayList<>();
        for (Node ambulance : getAmbulances()) {
            routePath = findPath(ambulance.getCoordValue().getX(), ambulance.getCoordValue().getY(),
                    patientNode.getCoordValue().getX(), patientNode.getCoordValue().getY());
            movements.add(new Ambulance(routePath, ambulance, patientNode));
        }

        for (Ambulance mov : movements) {
            if (mov.getPathRoute().size() < actualMax) {
                out = mov;
                actualMax = mov.getPathRoute().size();
            }
        }

        return out;
    }


    /**
     * @return position of hospital near
     */
    public Ambulance getNearestHospital(int posx, int posy) {
        Ambulance out = null;
        List<AbstractNode> routePath;
        int actualMax = App.getMaps().get(mapPosition).getNodes().size();

        Node ambulanceNode = new Node(posx, posy, "P");

        List<Node> hospitals = this.getHospitals();
        List<Ambulance> movements = new ArrayList<>();
        for (Node hospital : hospitals) {
            routePath = findPath(ambulanceNode.getCoordValue().getX(), ambulanceNode.getCoordValue().getY(),
                    hospital.getCoordValue().getX(), hospital.getCoordValue().getY());
            movements.add(new Ambulance(routePath, ambulanceNode, hospital));
        }

        for (Ambulance mov : movements) {
            if (mov.getPathRoute().size() < actualMax) {
                out = mov;
                actualMax = mov.getPathRoute().size();
            }
        }

        return out;
    }
}
