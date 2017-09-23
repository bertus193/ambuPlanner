package com.example.ambuplanner.model;

public interface NodeFactory {

    public AbstractNode createNode(int x, int y, String value);
}
