package model;

import java.util.ArrayList;

public class Trip {
    ArrayList<Container> containers;

    public Trip(ArrayList<Container> containers) {
        this.containers = containers;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "containers=" + containers +
                '}';
    }
}
