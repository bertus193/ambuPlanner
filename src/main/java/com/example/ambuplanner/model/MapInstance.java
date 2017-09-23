package com.example.ambuplanner.model;

import java.util.ArrayList;
import java.util.List;

public class MapInstance {

    private List<AbstractNode> nodes = new ArrayList<>();

    public MapInstance(List<AbstractNode> nodes) {
        this.nodes = nodes;
    }

    public List<AbstractNode> getNodes() {
        return this.nodes;
    }

    public void setNodes(List<AbstractNode> nodes) {
        this.nodes = nodes;
    }
}
