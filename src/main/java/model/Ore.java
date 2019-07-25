package model;

import constants.OreType;

public class Ore {

    private String name;
    private OreType type;
    private float capacity;

    public Ore(String name, OreType type, float... capacity) {
        this.name = name;
        this.type = type;
        this.capacity = capacity.length > 0 ? capacity[0] : 100f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OreType getType() {
        return type;
    }

    public void setType(OreType type) {
        this.type = type;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Ore{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", capacity=" + capacity +
                '}';
    }
}
