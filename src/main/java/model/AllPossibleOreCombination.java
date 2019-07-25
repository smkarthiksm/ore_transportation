package model;

import java.util.ArrayList;

public class AllPossibleOreCombination {
    private String name;
    private Ore directOre;
    private ArrayList<OreCombination> oreCombinations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ore getDirectOre() {
        return directOre;
    }

    public void setDirectOre(Ore directOre) {
        this.directOre = directOre;
    }

    public ArrayList<OreCombination> getOreCombinations() {
        return oreCombinations;
    }

    public void setOreCombinations(ArrayList<OreCombination> oreCombinations) {
        this.oreCombinations = oreCombinations;
    }

    @Override
    public String toString() {
        return "AllPossibleOreCombination{" +
                "name='" + name + '\'' +
                ", directOre=" + directOre +
                ", oreCombination=" + oreCombinations +
                '}';
    }
}
