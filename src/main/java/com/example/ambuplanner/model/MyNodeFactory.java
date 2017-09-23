package com.example.ambuplanner.model;

public class MyNodeFactory implements NodeFactory {

    @Override
    public AbstractNode createNode(int x, int y, String value) {
        return new MyNode(x, y, value);
    }

}
