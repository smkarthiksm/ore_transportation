package model;

import constants.OreType;

public class Container {
    private String name;
    private OreType type;
    private float capacity;
    private String ore;

    public Container(String name, OreType type, Float capacity) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
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

    public String getOre() {
        return ore;
    }

    public void setOre(String ore) {
        this.ore = ore;
    }

    @Override
    public String toString() {
        return "Container{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", capacity=" + capacity +
                ", ore='" + ore + '\'' +
                '}';
    }
}
