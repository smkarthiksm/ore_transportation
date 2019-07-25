package implementation;

import constants.ApplicationConstants;
import constants.OreType;
import controller.UserInput;
import exception.ExceptionHandler;
import model.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OreAvailabilityTest {

    public static ArrayList<Ore> ores;
    public static ArrayList<Container> containers;
    public static ArrayList<OreCombination> oreCombinations;
    public static ArrayList<ProhibitedCombination> prohibitedCombinations;
    public static ArrayList<Ore> resourcesOnAsteroid;
    public static ArrayList<Ore> resourcesNeededOnMars;

    @BeforeClass
    public static void getUserInput() {
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

    @Test
    public void getAllPossibleOreCombinationsShouldPass() {
        try {
            ArrayList<AllPossibleOreCombination> output = new OreAvailability().getAllPossibleOreCombinations(resourcesNeededOnMars, resourcesOnAsteroid, oreCombinations);
            assertNotEquals(output.get(0).getName(), null);
        } catch (ExceptionHandler e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllPossibleOreCombinationsShouldFail() {
        try {
            resourcesNeededOnMars = new ArrayList<>();
            resourcesNeededOnMars.add(new Ore("Sulphate", OreType.Gas, 60f));
            ArrayList<AllPossibleOreCombination> output = new OreAvailability().getAllPossibleOreCombinations(resourcesNeededOnMars, resourcesOnAsteroid, oreCombinations);
            assertEquals(output.get(0).getName(), null);
        } catch (ExceptionHandler e) {
            assertEquals(ApplicationConstants.RESOURCES_UNAVAILABLE, e.getMessage());
        }
    }
}
