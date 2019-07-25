package implementation;

import constants.ApplicationConstants;
import controller.UserInput;
import exception.ExceptionHandler;
import model.AllPossibleOreCombination;
import model.Container;
import model.Ore;
import model.OreCombination;

import java.util.ArrayList;

public class OreSelection extends Exception {

    /**
     * Will take all the possible ore combinations and will output the needed ores based on the following conditions
     * 1. If direct ore is present and corresponding container is present, then it will be added to the output ore. Else step 2
     * 2. If combinations are present, then if the ore is present in the equation directly and corresponding container is present,
     * then it is added as a direct ore. Else the equation satisfying maximum capacity of the needed ore and the required container
     * is added to the output ore.
     *
     * @param allPossibleOreCombinations
     * @param containers
     * @return List of Ores
     */
    public ArrayList<OreCombination> getBestOptionForTheNeededOres(
            ArrayList<AllPossibleOreCombination> allPossibleOreCombinations, ArrayList<Container> containers) throws ExceptionHandler {
        try {

            ArrayList<OreCombination> oreCombinationsToBeSent = new ArrayList<>();
            ArrayList<OreCombination> oreCombinationsResolvedFromEquation;
            ArrayList<Ore> ores;
            ArrayList<AllPossibleOreCombination> ifRHSOresExists;

            for (AllPossibleOreCombination allPossibleOreCombination : allPossibleOreCombinations) {
                boolean isDirectOrePresent = false;
                // If needed ore is present as a direct ore
                if (allPossibleOreCombination.getDirectOre() != null) {
                    for (Container container : containers) {
                        if (allPossibleOreCombination.getDirectOre().getType().equals(container.getType())) {
                            oreCombinationsToBeSent.add(new OreCombination(allPossibleOreCombination.getDirectOre(), null));
                            isDirectOrePresent = true;
                        }
                    }
                }
                // If needed ore is present as a combination
                if (!isDirectOrePresent) {
                    for (OreCombination oreCombination : allPossibleOreCombination.getOreCombinations()) {
                        // If ore is present on the LHS of the equation
                        if (oreCombination.getDirectOre().getName().equals(allPossibleOreCombination.getName())) {
                            // Check if the ores on the RHS of the equation exists in asteroid
                            ifRHSOresExists = checkIfRHSExists(oreCombination.getCombinations(), UserInput.resourcesOnAsteroid, UserInput.oreCombinations);
                            isDirectOrePresent = true;
                            boolean isContainerPresent = false;

                            // Check if container exists for the needed ore (if present as parent ore)
                            for (Container container : containers) {
                                if (oreCombination.getDirectOre().getType().equals(container.getType())) {
                                    isContainerPresent = true;
                                }
                            }

                            // If any ore of the combination is not available on asteroid
                            for (AllPossibleOreCombination possibleCombinations : ifRHSOresExists) {
                                if (possibleCombinations.getDirectOre() == null) {
                                    isDirectOrePresent = false;
                                }
                            }

                            // If any ore needed in the RHS of the equation is missing or if there is no container present for the needed ore (if present as parent ore)
                            if (!isDirectOrePresent || !isContainerPresent) {
                                // Recursive call to resolve the dependencies for the needed ores and returns the substituted form of the equation
                                for (Ore ore : oreCombination.getCombinations()) {
                                    // Iterate for each ore
                                    ores = new ArrayList<>();
                                    ores.add(ore);
                                    oreCombinationsResolvedFromEquation = resolveOresInRHSOfEquation(ores, UserInput.resourcesOnAsteroid, UserInput.oreCombinations,UserInput.containers);
                                    // Remove the combinations which doesn't have the appropriate containers
                                    for (int i = 0; i < oreCombinationsResolvedFromEquation.size(); i++) {
                                        boolean isContainerPresentForCombination = false;
                                        for (Container container : containers) {
                                            if (oreCombinationsResolvedFromEquation.get(i).getDirectOre() != null && oreCombinationsResolvedFromEquation.get(i).getDirectOre().getType().equals(container.getType())) {
                                                isContainerPresentForCombination = true;
                                            }
                                        }
                                        if (!isContainerPresentForCombination) {
                                            oreCombinationsResolvedFromEquation.remove(i);
                                        }
                                    }
                                    // The first element is needed, as it is prioritized and sorted based on extractable amount.
                                    oreCombinationsResolvedFromEquation = new ArrayList<>(oreCombinationsResolvedFromEquation.subList(0, 1));
                                    oreCombinationsToBeSent.addAll(oreCombinationsResolvedFromEquation);
                                }
                            } else {
                                oreCombinationsToBeSent.add(oreCombination);
                            }
                        }
                        // If ore is present on the RHS of the equation
                        else {

                            for (Ore oreInCombination : oreCombination.getCombinations()) {
                                if (oreInCombination.getName().equals(allPossibleOreCombination.getName())) {
                                    boolean isContainerPresent = false;
                                    for (Container container : containers) {
                                        if (oreInCombination.getType().equals(container.getType())) {
                                            ores = new ArrayList<>();
                                            ores.add(oreCombination.getDirectOre());
                                            ifRHSOresExists = checkIfRHSExists(ores, UserInput.resourcesOnAsteroid, UserInput.oreCombinations);

                                            // Check if the parent ore is present and if the child ore can be extracted
                                            if (ifRHSOresExists.size() > 0 && ifRHSOresExists.get(0).getDirectOre() != null) {
                                                oreInCombination.setCapacity(100f);
                                                oreCombinationsToBeSent.add(new OreCombination(oreInCombination, null));
                                                isContainerPresent = true;
                                            }
                                        }
                                        break;
                                    }
                                    // If no parent ore present or if no container for child exists
                                    if (!isContainerPresent) {
                                        isContainerPresent = false;
                                        for (Container container : containers) {
                                            if (oreCombination.getDirectOre().getType().equals(container.getType())) {
                                                isContainerPresent = true;
                                            }
                                        }
                                        if (isContainerPresent) {
                                            oreCombinationsToBeSent.add(new OreCombination(oreCombination.getDirectOre(), oreCombination.getCombinations()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return oreCombinationsToBeSent;

        } catch (Exception e) {
            throw new ExceptionHandler(ApplicationConstants.RESOURCES_UNAVAILABLE);
        }
    }

    /**
     * Checks if the requested resources are available and returns the simplified form of the equation by recursive call
     *
     * @param resourcesNeededInEquation
     * @return List of oreCombinations
     */
    public ArrayList<OreCombination> resolveOresInRHSOfEquation(ArrayList<Ore> resourcesNeededInEquation, ArrayList<Ore> resourcesOnAsteroid, ArrayList<OreCombination> oreCombinations,ArrayList<Container> containers) throws ExceptionHandler {
        try {
            ArrayList<AllPossibleOreCombination> allPossibleOreCombinations = checkIfRHSExists(resourcesNeededInEquation, resourcesOnAsteroid, oreCombinations);
            ArrayList<OreCombination> combinations = new OreSelection().getBestOptionForTheNeededOres(allPossibleOreCombinations, containers);
            return combinations;
        } catch (Exception e) {
            throw new ExceptionHandler(ApplicationConstants.RESOURCES_UNAVAILABLE);
        }
    }

    /**
     * Checks if the requested resources are available
     *
     * @param resourcesNeededInEquation
     * @return List of oreCombinations
     */
    public ArrayList<AllPossibleOreCombination> checkIfRHSExists(ArrayList<Ore> resourcesNeededInEquation, ArrayList<Ore> resourcesOnAsteroid, ArrayList<OreCombination> oreCombinations) throws
            ExceptionHandler {
        try {
            return new OreAvailability().getAllPossibleOreCombinations(resourcesNeededInEquation, resourcesOnAsteroid, oreCombinations);
        } catch (Exception e) {
            throw new ExceptionHandler(ApplicationConstants.RESOURCES_UNAVAILABLE);
        }
    }
}

