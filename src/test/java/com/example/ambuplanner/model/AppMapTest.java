package com.example.ambuplanner.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppMapTest {

    private AppMap myMap;

    @Before
    public void setUp() throws Exception {

        AbstractNode node1 = new Node(0, 0, "null");
        AbstractNode node2 = new Node(0, 1, "2");
        AbstractNode node3 = new Node(0, 2, "1");
        AbstractNode node4 = new Node(0, 3, "null");
        AbstractNode node5 = new Node(1, 0, "null");
        AbstractNode node6 = new Node(1, 1, "B");
        AbstractNode node7 = new Node(1, 2, "H");
        AbstractNode node8 = new Node(1, 3, "null");
        AbstractNode node9 = new Node(2, 0, "null");
        AbstractNode node10 = new Node(2, 1, "null");
        AbstractNode node11 = new Node(2, 2, "null");
        AbstractNode node12 = new Node(2, 3, "null");
        List<AbstractNode> nodes = Arrays.asList(node1, node2, node3, node4, node5, node6, node7, node8, node9, node10, node11, node12);
        myMap = new AppMap(nodes, 0);

        AppMap map = new AppMap(nodes, 0);
        List<AppMap> appMaps = new ArrayList<>();
        appMaps.add(map);
        App.createApp(appMaps, new ArrayList<>());

    }

    @Test
    public void findPaths() {
        int route = myMap.findPath(0, 1, 0, 3).size();
        Assert.assertEquals(route, 8);

        route = myMap.findPath(0, 2, 1, 1).size();
        Assert.assertEquals(route, 6);
    }

    @Test
    public void testGetLenght() {
        Assert.assertEquals(myMap.countLenght(), 4);
    }

    @Test
    public void getCorrectCord() {
        Assert.assertEquals(myMap.getNodeByCoord(1, 1).getCoordValue().getValue(), "B");
        Assert.assertNull(myMap.getNodeByCoord(8, 8));
    }

    @Test
    public void getCorrectPositionByCord() {
        Assert.assertEquals(myMap.getNodePositionByCoord(1, 1), 5);
        Assert.assertEquals(myMap.getNodePositionByCoord(8, 8), -1);
    }

    @Test
    public void getCorrectHospitals() {
        Assert.assertEquals(myMap.getHospitals().toString(), "[(1, 2 V: H)]");
    }

    @Test
    public void getCorrectAmbulances() {
        Assert.assertEquals(myMap.getAmbulances().toString(), "[(0, 1 V: 2), (0, 2 V: 1)]");
    }

    @Test
    public void getNearestAmbulance() {
        Assert.assertEquals(myMap.getNearestAmbulance(2, 0).getPathRoute().toString(), "[(0, 0 V: null), (1, 0 V: null), (2, 0 V: null)]");
    }

    @Test
    public void getNearestHospital() {
        Assert.assertEquals(myMap.getNearestHospital(0, 1).getPathRoute().toString(), "[(0, 0 V: null), (1, 0 V: null), (2, 0 V: null), (2, 1 V: null), (2, 2 V: null), (1, 2 V: H)]");
    }
}