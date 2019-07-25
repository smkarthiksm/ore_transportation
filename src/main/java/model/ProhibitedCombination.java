package model;

import java.util.ArrayList;

public class ProhibitedCombination {
    private ArrayList<Ore> ores;

    public ProhibitedCombination(ArrayList<Ore> ores) {
        this.ores = ores;
    }

    public ArrayList<Ore> getOres() {
        return ores;
    }

    public void setOres(ArrayList<Ore> ores) {
        this.ores = ores;
    }

    @Override
    public String toString() {
        return "ProhibitedCombination{" +
                "ores=" + ores +
                '}';
    }
}
