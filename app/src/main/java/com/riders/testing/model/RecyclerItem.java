package com.riders.testing.model;

public class RecyclerItem {

    private String name;

    public RecyclerItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Name : " + this.name;
    }
}
