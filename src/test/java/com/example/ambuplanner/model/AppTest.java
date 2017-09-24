package com.example.ambuplanner.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

    private App app;

    private List<AbstractNode> nodes;
    private List<Notification> notifications;
    private List<AppMap> appMaps;

    @Before
    public void setUp() {
        AbstractNode node1 = new Node(0, 0, "null");
        AbstractNode node2 = new Node(0, 1, "null");
        AbstractNode node3 = new Node(0, 2, "null");
        AbstractNode node4 = new Node(0, 3, "null");
        AbstractNode node5 = new Node(1, 0, "1");
        AbstractNode node6 = new Node(1, 1, "B");
        AbstractNode node7 = new Node(1, 2, "H");
        AbstractNode node8 = new Node(1, 3, "null");
        AbstractNode node9 = new Node(2, 0, "null");
        AbstractNode node10 = new Node(2, 1, "null");
        AbstractNode node11 = new Node(2, 2, "null");
        AbstractNode node12 = new Node(2, 3, "3");
        this.nodes = Arrays.asList(node1, node2, node3, node4, node5, node6, node7, node8, node9, node10, node11, node12);


        Notification noti1 = new Notification(1, node1);
        Notification noti2 = new Notification(1, node8);
        this.notifications = Arrays.asList(noti1, noti2);

        AppMap map = new AppMap(this.nodes, 0);
        appMaps = new ArrayList<>();
        this.appMaps.add(map);

    }

    @Test
    public void createNewApp() throws Exception {
        App.createApp(this.appMaps, this.notifications);
        Assert.assertEquals(App.getMaps().size(), 1);
    }

    @Test
    public void createNewApp2() throws Exception {

        App.getMaps().add(new AppMap(this.nodes, 1));
        App.createApp(this.appMaps, this.notifications);
        Assert.assertEquals(App.getMaps().size(), 2);

        String value = appMaps.get(0).getNodes().get(0).getCoordValue().getValue();
        Assert.assertEquals(value, "null");
    }

    @Test
    public void generateNewMap() {
        Assert.assertEquals(App.getMaps().size(), 2);
        App.generateNewMap();
        Assert.assertEquals(App.getMaps().size(), 3);
    }

    @Test
    public void generateJSON() {
        String json = App.mapsToJson();
        Assert.assertThat(json, containsString("[{\"nodes\":[{\"coordValue\":{\"x\":0,\"y\":0,\"value\":\"null\"}"));
    }


}