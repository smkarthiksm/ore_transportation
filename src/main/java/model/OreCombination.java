package model;

import java.util.ArrayList;

public class OreCombination {

    private Ore directOre;
    private ArrayList<Ore> combinations;


    public OreCombination(Ore directOre, ArrayList<Ore> combinations) {
        this.directOre = directOre;
        this.combinations = combinations;
    }

    public Ore getDirectOre() {
        return directOre;
    }

    public void setDirectOre(Ore directOre) {
        this.directOre = directOre;
    }

    public ArrayList<Ore> getCombinations() {
        return combinations;
    }

    public void setCombinations(ArrayList<Ore> combinations) {
        this.combinations = combinations;
    }

    @Override
    public String toString() {
        return "OreCombination{" +
                "directOre=" + directOre +
                ", combinations=" + combinations +
                '}';
    }
}
