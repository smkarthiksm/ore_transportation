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
import static org.junit.Assert.assertNotEquals;

public class ContainerShippingTest {

    public static ArrayList<Ore> ores;
    public static ArrayList<Container> containers;
    public static ArrayList<OreCombination> oreCombinations;
    public static ArrayList<ProhibitedCombination> prohibitedCombinations;
    public static ArrayList<Ore> resourcesOnAsteroid;
    public static ArrayList<Ore> resourcesNeededOnMars;
    public static ArrayList<AllPossibleOreCombination> allPossibleOreCombinations;
    public static ArrayList<Container> containersToBeSent;

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
    public void setupContainers() {
        try {
            allPossibleOreCombinations = new OreAvailability().getAllPossibleOreCombinations(resourcesNeededOnMars, resourcesOnAsteroid, oreCombinations);
            containersToBeSent = new ContainerCreation().getRequiredContainers(oreCombinations, resourcesNeededOnMars, allPossibleOreCombinations, containers);
        } catch (ExceptionHandler e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNumberOfTripsShouldPass() {
        try {
            ArrayList<Trip> output = new ContainerShipping().getNumberOfTrips(containersToBeSent, prohibitedCombinations, containers);
            assert output.size() > 0;
        } catch (ExceptionHandler e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNumberOfTripsShouldFail() {
        try {
            containers.remove(2);
            containers.remove(2);
            ArrayList<Trip> output = new ContainerShipping().getNumberOfTrips(containersToBeSent, prohibitedCombinations, containers);
            assertEquals(output.size(), 0);
        } catch (ExceptionHandler e) {
            assertEquals(ApplicationConstants.CONTAINER_SHIPPING_FAILED, e.getMessage());
        }
    }
}
