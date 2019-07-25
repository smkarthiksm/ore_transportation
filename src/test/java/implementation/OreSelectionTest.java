package implementation;

import constants.ApplicationConstants;
import constants.OreType;
import exception.ExceptionHandler;
import model.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class OreSelectionTest {
    public static ArrayList<Ore> ores;
    public static ArrayList<Container> containers;
    public static ArrayList<OreCombination> oreCombinations;
    public static ArrayList<ProhibitedCombination> prohibitedCombinations;
    public static ArrayList<Ore> resourcesOnAsteroid;
    public static ArrayList<Ore> resourcesNeededOnMars;
    public static ArrayList<AllPossibleOreCombination> allPossibleOreCombinations;

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

    @Before
    public void getAllPossibleOreCombinations() {
        try {
            allPossibleOreCombinations = new OreAvailability().getAllPossibleOreCombinations(resourcesNeededOnMars, resourcesOnAsteroid, oreCombinations);
        } catch (ExceptionHandler e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBestOptionForTheNeededOresShouldPass() {
        try {
            ArrayList<OreCombination> output = new OreSelection().getBestOptionForTheNeededOres(allPossibleOreCombinations, containers);
            assert output.size() > 0;
        } catch (ExceptionHandler e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBestOptionForTheNeededOresShouldFail() {
        try {

            resourcesNeededOnMars = new ArrayList<>();
            resourcesNeededOnMars.add(new Ore("Sulphate", OreType.Gas, 60f));
            getAllPossibleOreCombinations();
            ArrayList<OreCombination> output = new OreSelection().getBestOptionForTheNeededOres(allPossibleOreCombinations, containers);
            assertEquals(output.size(), 0);
        } catch (ExceptionHandler e) {
            assertEquals(ApplicationConstants.RESOURCES_UNAVAILABLE, e.getMessage());
        }
    }

    @Test
    public void resolveOresInRHSOfEquationShouldPass() {
        try {
            ores = new ArrayList<>();
            ores.add(new Ore("O2HotSteam", OreType.Gas));
            ArrayList<OreCombination> output = new OreSelection().resolveOresInRHSOfEquation(ores, resourcesOnAsteroid, oreCombinations, containers);
            assert output.size() > 0;
        } catch (ExceptionHandler e) {
            e.printStackTrace();
        }
    }

    @Test
    public void resolveOresInRHSOfEquationShouldFail() {
        try {

            ores = new ArrayList<>();
            ores.add(new Ore("Sulphate", OreType.Gas));
            ArrayList<OreCombination> output = new OreSelection().resolveOresInRHSOfEquation(ores, resourcesOnAsteroid, oreCombinations, containers);
            assertEquals(output.size(), 0);
        } catch (ExceptionHandler e) {
            assertEquals(ApplicationConstants.RESOURCES_UNAVAILABLE, e.getMessage());
        }
    }

    @Test
    public void checkIfRHSExistsShouldPass() {
        try {
            ores = new ArrayList<>();
            ores.add(new Ore("O2HotSteam", OreType.Gas));
            ArrayList<AllPossibleOreCombination> output = new OreSelection().checkIfRHSExists(ores, resourcesOnAsteroid, oreCombinations);
            assert output.size() > 0;
        } catch (ExceptionHandler e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfRHSExistsShouldFail() {
        try {

            ores = new ArrayList<>();
            ores.add(new Ore("Sulphate", OreType.Gas));
            getAllPossibleOreCombinations();
            ArrayList<AllPossibleOreCombination> output = new OreSelection().checkIfRHSExists(ores, resourcesOnAsteroid, oreCombinations);
            assertEquals(output.size(), 0);
        } catch (ExceptionHandler e) {
            assertEquals(ApplicationConstants.RESOURCES_UNAVAILABLE, e.getMessage());
        }
    }
}
