package implementation;

import constants.ApplicationConstants;
import exception.ExceptionHandler;
import model.AllPossibleOreCombination;
import model.Container;
import model.Ore;
import model.OreCombination;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ContainerCreation {

    /**
     * Will take the best ore combination for the needed resources and will create the required containers.
     *
     * @param oreCombinations
     * @param resourcesNeededOnMars
     * @param allPossibleOreCombinations
     * @param containers
     * @return List of containers
     */
    public ArrayList<Container> getRequiredContainers(ArrayList<OreCombination> oreCombinations, ArrayList<Ore> resourcesNeededOnMars, ArrayList<AllPossibleOreCombination> allPossibleOreCombinations, ArrayList<Container> containers) throws ExceptionHandler {
        try {
            // Iterate through the ore combinations to find out the required ratio of the ores for the needed ore in mars.
            ArrayList<Container> containersToBeSent = new ArrayList<>();
            Set<Ore> oresToBeAdded = new HashSet<>();
            for (Ore resourceNeededOnMars : resourcesNeededOnMars) {
                for (AllPossibleOreCombination allPossibleOreCombination : allPossibleOreCombinations) {
                    if (allPossibleOreCombination.getName().equals(resourceNeededOnMars.getName())) {
                        for (OreCombination possibleOreCombinations : allPossibleOreCombination.getOreCombinations()) {
                            for (OreCombination resultCombinations : oreCombinations) {
                                if (resultCombinations.getCombinations() != null) {
                                    for (Ore resultOres : resultCombinations.getCombinations()) {
                                        for (Ore oresInPossibleCombinations : possibleOreCombinations.getCombinations()) {
                                            if (resultCombinations.getDirectOre().getName().equals(oresInPossibleCombinations.getName()) || resultOres.getName().equals(oresInPossibleCombinations.getName())) {
                                                oresToBeAdded.add(oresInPossibleCombinations);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Check if the needed ore can be extracted and sent directly
                        boolean canExtractFromDirectOre = false;
                        for (Container container : containers) {
                            for (OreCombination combination : oreCombinations) {
                                if (combination.getDirectOre().getName().equals(resourceNeededOnMars.getName()) && combination.getDirectOre().getType().equals(container.getType())) {
                                    canExtractFromDirectOre = true;
                                }
                                for (Ore ore : combination.getCombinations()) {
                                    if (ore.getName().equals(resourceNeededOnMars.getName()) && ore.getType().equals(container.getType())) {
                                        canExtractFromDirectOre = true;
                                    }
                                }
                            }
                        }

                        // If the needed ore can be extracted and sent directly
                        if (canExtractFromDirectOre) {
                            for (Container container : containers) {
                                if (resourceNeededOnMars.getType().equals(container.getType())) {
                                    double noOfContainersNeeded = Math.ceil(resourceNeededOnMars.getCapacity() / container.getCapacity());
                                    for (int i = 0; i < noOfContainersNeeded; i++) {
                                        containersToBeSent.add(new Container(resourceNeededOnMars.getName(), container.getType(), container.getCapacity()));
                                    }
                                }
                            }
                        }
                        // If the needed is in a equation, find the needed ratio
                        else {
                            for (Ore ore : oresToBeAdded) {
                                for (OreCombination combination : oreCombinations) {
                                    boolean isContainerAdded = false;
                                    for (Ore oreInCombination : combination.getCombinations()) {

                                        // If element in the needed equation appears on the RHS
                                        if (oreInCombination.getName().equals(ore.getName())) {
                                            for (Container container : containers) {
                                                if (combination.getDirectOre().getType().equals(container.getType())) {
                                                    isContainerAdded = true;

                                                    // Find the number of containers needed based on the current equation which is estimated from the original equation
                                                    double noOfContainersInEstimatedEquation = Math.ceil(resourceNeededOnMars.getCapacity() / container.getCapacity());
                                                    double oreInCombinationEstimationAmount = 1f;
                                                    double noOfContainersInActualEquation = 0;
                                                    for (int i = 1; oreInCombinationEstimationAmount < ore.getCapacity() * noOfContainersInEstimatedEquation; i++) {
                                                        oreInCombinationEstimationAmount = i * oreInCombination.getCapacity();
                                                        noOfContainersInActualEquation = i;
                                                    }
                                                    for (int i = 0; i < noOfContainersInActualEquation; i++) {
                                                        containersToBeSent.add(new Container(combination.getDirectOre().getName(), combination.getDirectOre().getType(), container.getCapacity()));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    // If element in the needed equation appears on the LHS
                                    if (!isContainerAdded && combination.getDirectOre().getName().equals(ore.getName())) {
                                        for (Container container : containers) {
                                            if (combination.getDirectOre().getType().equals(container.getType())) {

                                                // Find the number of containers needed based on the current equation which is estimated from the original equation
                                                double noOfContainersInEstimatedEquation = Math.ceil(resourceNeededOnMars.getCapacity() / container.getCapacity());
                                                double noOfContainersInActualEquation = Math.ceil(noOfContainersInEstimatedEquation + ((ore.getCapacity() * noOfContainersInEstimatedEquation - combination.getDirectOre().getCapacity() * noOfContainersInEstimatedEquation) / container.getCapacity()));
                                                for (int i = 0; i < noOfContainersInActualEquation; i++) {
                                                    containersToBeSent.add(new Container(combination.getDirectOre().getName(), combination.getDirectOre().getType(), container.getCapacity()));
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }
                }
            }
            return containersToBeSent;
        } catch (Exception exception) {
            throw new ExceptionHandler(ApplicationConstants.CONTAINER_CREATION_FAILED);
        }
    }
}
