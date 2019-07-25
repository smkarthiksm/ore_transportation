package implementation;

import constants.ApplicationConstants;
import exception.ExceptionHandler;
import model.AllPossibleOreCombination;
import model.Ore;
import model.OreCombination;

import java.util.ArrayList;

public class OreAvailability {

    /**
     * Used to check if the resources needed in Mars are available in asteroid.
     * If available, all the possible combinations(both direct and mix) are returned.
     *
     * @param resourcesNeededOnMars
     * @param resourcesOnAsteroid
     * @param oreCombinations
     * @return AllPossibleOreCombination
     */
    public ArrayList<AllPossibleOreCombination> getAllPossibleOreCombinations(ArrayList<Ore> resourcesNeededOnMars, ArrayList<Ore> resourcesOnAsteroid, ArrayList<OreCombination> oreCombinations) throws ExceptionHandler {

        ArrayList<AllPossibleOreCombination> allPossibleOreCombinations = new ArrayList<>();
        ArrayList<OreCombination> combinationsToBeAdded = new ArrayList<>();
        ArrayList<OreCombination> combinations;
        AllPossibleOreCombination allPossibleOreCombination;

        try {
            // Check for ores needed in mars if present as a direct ore or as a combination
            for (Ore marsOre : resourcesNeededOnMars) {
                allPossibleOreCombination = new AllPossibleOreCombination();


                // Check for direct ore
                for (Ore asteroidOre : resourcesOnAsteroid) {
                    if (marsOre.getName().equals(asteroidOre.getName())) {
                        allPossibleOreCombination.setName(marsOre.getName());
                        allPossibleOreCombination.setDirectOre(asteroidOre);
                    }
                }
                // Check for combinations
                for (OreCombination ore : oreCombinations) {

                    // Check if the combination name is the ore name
                    if (marsOre.getName().equals(ore.getDirectOre().getName())) {
                        allPossibleOreCombination.setName(marsOre.getName());
                        combinationsToBeAdded.add(ore);
                    } else {

                        // Check for the ore in the mixings used in the combination
                        for (Ore oreList : ore.getCombinations()) {
                            if (marsOre.getName().equals(oreList.getName())) {
                                allPossibleOreCombination.setName(marsOre.getName());
                                combinationsToBeAdded.add(ore);
                            }
                        }
                    }
                }

                combinations = new ArrayList<>();
                // Sort the combinations based on priority. If the element is present in the LHS of the equation, it takes the highest priority
                int indexToBeRemoved = -1;
                for (int i = 0; i < combinationsToBeAdded.size(); i++) {
                    if (combinationsToBeAdded.get(i).getDirectOre().getName().equals(allPossibleOreCombination.getName())) {
                        combinations.add(combinationsToBeAdded.get(i));
                        indexToBeRemoved = i;
                    }
                }
                if (indexToBeRemoved > -1) {
                    combinationsToBeAdded.remove(indexToBeRemoved);
                }

                // Then combination with the maximum extractable amount takes the next highest priority. So sorted in descending order
                for (int i = 0; i < combinationsToBeAdded.size() - 1; i++) {
                    float outerMaxValue = 0;
                    for (int outer = 0; outer < combinationsToBeAdded.get(i).getCombinations().size(); outer++) {
                        if (combinationsToBeAdded.get(i).getCombinations().get(outer).getName().equals(allPossibleOreCombination.getName())) {
                            outerMaxValue = combinationsToBeAdded.get(i).getCombinations().get(outer).getCapacity();
                        }
                    }
                    for (int j = i + 1; j < combinationsToBeAdded.size(); j++) {
                        float innerMaxValue = 0;
                        for (int inner = 0; inner < combinationsToBeAdded.get(j).getCombinations().size(); inner++) {
                            if (combinationsToBeAdded.get(j).getCombinations().get(inner).getName().equals(allPossibleOreCombination.getName())) {
                                innerMaxValue = combinationsToBeAdded.get(j).getCombinations().get(inner).getCapacity();
                            }
                        }
                        if (outerMaxValue < innerMaxValue) {
                            OreCombination temp = combinationsToBeAdded.get(i);
                            combinationsToBeAdded.set(i, combinationsToBeAdded.get(j));
                            combinationsToBeAdded.set(j, temp);
                        }
                    }
                }
                combinations.addAll(combinationsToBeAdded);
                allPossibleOreCombination.setOreCombinations(combinations);
                allPossibleOreCombinations.add(allPossibleOreCombination);
            }

            return allPossibleOreCombinations;

        } catch (Exception e) {
            throw new ExceptionHandler(ApplicationConstants.RESOURCES_UNAVAILABLE);
        }
    }
}
