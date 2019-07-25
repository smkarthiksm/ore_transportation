package controller;

import constants.OreType;
import implementation.ContainerCreation;
import implementation.ContainerShipping;
import implementation.OreAvailability;
import implementation.OreSelection;
import model.*;

import java.util.ArrayList;

public class UserInput extends Exception {

    public UserInput userInput;

    // Static variables are used inorder to be accessbile from other classes.
    // In actual implementation these values will be fed to those classes from the data layer.
    public static ArrayList<Ore> ores;
    public static ArrayList<Container> containers;
    public static ArrayList<OreCombination> oreCombinations;
    public static ArrayList<ProhibitedCombination> prohibitedCombinations;
    public static ArrayList<Ore> resourcesOnAsteroid;
    public static ArrayList<Ore> resourcesNeededOnMars;

    public static void main(String[] args) {
        UserInput userInput = new UserInput();
        userInput.getUserInputForSulphate();
        try {
            ArrayList<AllPossibleOreCombination> allPossibleOreCombinations = new OreAvailability().getAllPossibleOreCombinations(
                    resourcesNeededOnMars, resourcesOnAsteroid, oreCombinations);
            ArrayList<OreCombination> oreCombinationsToBeSent = new OreSelection().getBestOptionForTheNeededOres(allPossibleOreCombinations, containers);
            ArrayList<Container> containersCreated = new ContainerCreation().getRequiredContainers(oreCombinationsToBeSent, resourcesNeededOnMars, allPossibleOreCombinations, containers);
            ArrayList<Trip> requiredNoOfTrips = new ContainerShipping().getNumberOfTrips(containersCreated, prohibitedCombinations, containers);
            System.out.println(requiredNoOfTrips);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Function that mocks user input from console for sulphate resource
     */
    public void getUserInputForSulphate() {

        //List of ores
        ores = new ArrayList<>();
        ores.add(new Ore("SulphateResouce", OreType.Acid));
        ores.add(new Ore("UResource", OreType.Radioactive));
        ores.add(new Ore("WaterIce", OreType.Solid));
        ores.add(new Ore("WaterSlush", OreType.Liquid));
        ores.add(new Ore("LeadResource", OreType.Solid));
        ores.add(new Ore("ULeadSlush", OreType.Liquid));
        ores.add(new Ore("HePockets", OreType.Gas));
        ores.add(new Ore("ThResource", OreType.Radioactive));
        ores.add(new Ore("O2HotSteam", OreType.Gas));
        ores.add(new Ore("O2ColdSteam", OreType.Gas));
        ores.add(new Ore("GoldResouce", OreType.Solid));
        ores.add(new Ore("GoldLava", OreType.Liquid));
        ores.add(new Ore("GoldThSlush", OreType.Liquid));
        ores.add(new Ore("SO2Steam", OreType.Gas));
        ores.add(new Ore("NO2Steam", OreType.Gas));
        ores.add(new Ore("SulfurResource", OreType.Acid));
        ores.add(new Ore("NitricResource", OreType.Acid));
        ores.add(new Ore("Junk", OreType.Solid));


        //List of containers
        containers = new ArrayList<>();
        containers.add(new Container("RC1", OreType.Radioactive, 100f));
        containers.add(new Container("LC1", OreType.Liquid, 100f));
        containers.add(new Container("GC2", OreType.Gas, 100f));

        //List of ore combinations
        oreCombinations = new ArrayList<>();

        ores = new ArrayList<>();
        ores.add(new Ore("SulfurResource", OreType.Acid, 90f));
        ores.add(new Ore("WaterSlush", OreType.Liquid, 10f));
        oreCombinations.add(new OreCombination(new Ore("SulphateResouce", OreType.Acid), ores));

        ores = new ArrayList<>();
        ores.add(new Ore("UResource", OreType.Radioactive, 60f));
        ores.add(new Ore("LeadResource", OreType.Solid, 30f));
        ores.add(new Ore("WaterSlush", OreType.Liquid, 10f));
        oreCombinations.add(new OreCombination(new Ore("ULeadSlush", OreType.Liquid), ores));

        ores = new ArrayList<>();
        ores.add(new Ore("SulfurResource", OreType.Acid, 70f));
        ores.add(new Ore("O2HotSteam", OreType.Gas, 30f));
        oreCombinations.add(new OreCombination(new Ore("SO2Steam", OreType.Gas), ores));

        ores = new ArrayList<>();
        ores.add(new Ore("WaterIce", OreType.Solid, 90f));
        ores.add(new Ore("Junk", OreType.Solid, 10f));
        oreCombinations.add(new OreCombination(new Ore("WaterSlush", OreType.Liquid), ores));

        ores = new ArrayList<>();
        ores.add(new Ore("GoldResouce", OreType.Solid, 50f));
        ores.add(new Ore("ThResource", OreType.Radioactive, 30f));
        ores.add(new Ore("WaterSlush", OreType.Liquid, 20f));
        oreCombinations.add(new OreCombination(new Ore("GoldThSlush", OreType.Liquid), ores));

        ores = new ArrayList<>();
        ores.add(new Ore("GoldResouce", OreType.Solid, 80f));
        ores.add(new Ore("O2HotSteam", OreType.Gas, 20f));
        oreCombinations.add(new OreCombination(new Ore("GoldLava", OreType.Liquid), ores));

        ores = new ArrayList<>();
        ores.add(new Ore("NitricResource", OreType.Acid, 75f));
        ores.add(new Ore("O2HotSteam", OreType.Gas, 25f));
        oreCombinations.add(new OreCombination(new Ore("NO2Steam", OreType.Gas), ores));

        //List of prohibited combinations
        prohibitedCombinations = new ArrayList<>();

        ores = new ArrayList<>();
        ores.add(new Ore("ThResource", OreType.Radioactive));
        ores.add(new Ore("UResource", OreType.Radioactive));
        prohibitedCombinations.add(new ProhibitedCombination(ores));

        ores = new ArrayList<>();
        ores.add(new Ore("GoldLava", OreType.Liquid));
        ores.add(new Ore("SO2Steam", OreType.Gas));
        prohibitedCombinations.add(new ProhibitedCombination(ores));

        //List of resources on asteroid
        resourcesOnAsteroid = new ArrayList<>();
        resourcesOnAsteroid.add(new Ore("SulfurResource", OreType.Acid));
        resourcesOnAsteroid.add(new Ore("WaterIce", OreType.Solid));
        resourcesOnAsteroid.add(new Ore("Junk", OreType.Solid));
        resourcesOnAsteroid.add(new Ore("O2HotSteam", OreType.Gas));

        resourcesNeededOnMars = new ArrayList<>();
        resourcesNeededOnMars.add(new Ore("SulphateResouce", OreType.Acid, 400f));
    }

    /**
     * Function that mocks user input from console for O2HotSteam resource
     */
    public void getUserInputForO2HotSteam() {

        //List of ores
        ores = new ArrayList<>();
        ores.add(new Ore("SulfurResource", OreType.Acid));
        ores.add(new Ore("SO2Steam", OreType.Gas));
        ores.add(new Ore("O2HotSteam", OreType.Gas));
        ores.add(new Ore("Junk", OreType.Radioactive));
        ores.add(new Ore("NitricResource", OreType.Gas));

        //List of containers
        containers = new ArrayList<>();
        containers.add(new Container("RC1", OreType.Radioactive, 100f));
        containers.add(new Container("LC1", OreType.Liquid, 50f));
        containers.add(new Container("GC1", OreType.Gas, 100f));
        containers.add(new Container("GC2", OreType.Gas, 100f));
        containers.add(new Container("AC1", OreType.Acid, 100f));

        //Ores to be added for combination
        ores = new ArrayList<>();
        ores.add(new Ore("SulfurResource", OreType.Acid, 70f));
        ores.add(new Ore("O2HotSteam", OreType.Gas, 30f));

        //List of ore combinations
        oreCombinations = new ArrayList<>();
        oreCombinations.add(new OreCombination(new Ore("SO2Steam", OreType.Gas), ores));

        //List of prohibited combinations
        ores = new ArrayList<>();
        ores.add(new Ore("NitricResource", OreType.Gas));
        ores.add(new Ore("O2HotSteam", OreType.Gas));
        prohibitedCombinations = new ArrayList<>();
        prohibitedCombinations.add(new ProhibitedCombination(ores));

        //List of resources on asteroid
        resourcesOnAsteroid = new ArrayList<>();
        resourcesOnAsteroid.add(new Ore("SO2Steam", OreType.Gas));
        resourcesOnAsteroid.add(new Ore("NitricResource", OreType.Acid));
        resourcesOnAsteroid.add(new Ore("Junk", OreType.Solid));

        resourcesNeededOnMars = new ArrayList<>();
        resourcesNeededOnMars.add(new Ore("O2HotSteam", OreType.Gas, 60f));
    }
}
