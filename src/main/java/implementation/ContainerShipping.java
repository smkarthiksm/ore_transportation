package implementation;

import constants.ApplicationConstants;
import exception.ExceptionHandler;
import model.Container;
import model.Ore;
import model.ProhibitedCombination;
import model.Trip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ContainerShipping extends Exception {

    /**
     * Will take all the containers and segregates them as trips, so that they're not dangerous to be transported
     *
     * @param containersToBeSent
     * @param prohibitedCombinations
     * @param availableContainers
     * @return List of trips
     */
    public ArrayList<Trip> getNumberOfTrips(ArrayList<Container> containersToBeSent, ArrayList<ProhibitedCombination> prohibitedCombinations, ArrayList<Container> availableContainers) throws ExceptionHandler {
        try {
            Set<String> uniqueOres = new HashSet<>();
            ArrayList<Container> containers;
            ArrayList<ProhibitedCombination> filteredProhibitedCombinations = new ArrayList<>();
            ArrayList<Trip> trips;
            ArrayList<Container> containerToBeAdded;
            ArrayList<String> prohibitedOres;

            for (Container container : containersToBeSent) {
                uniqueOres.add(container.getName());
            }
            // Remove the unnecessary ore shipping restrictions
            for (String name : uniqueOres) {
                for (ProhibitedCombination prohibitedCombination : prohibitedCombinations) {
                    for (Ore ore : prohibitedCombination.getOres()) {
                        if (ore.getName().equals(name)) {
                            filteredProhibitedCombinations.add(prohibitedCombination);
                        }
                    }
                }
            }
            uniqueOres = new HashSet<>();
            trips = new ArrayList<>();
            // Iterate through the containers to find the compatible containers
            for (int i = 0; i < containersToBeSent.size(); i++) {
                containers = new ArrayList<>(availableContainers);
                Trip trip;
                containerToBeAdded = new ArrayList<>();
                for (int j = 0; j < containersToBeSent.size(); j++) {
                    // find the prohibited ores that shouldn't be shipped together with the current ore
                    boolean canAdd = false;
                    for (ProhibitedCombination prohibitedCombination : filteredProhibitedCombinations) {
                        prohibitedOres = new ArrayList<>();
                        for (Ore ore : prohibitedCombination.getOres()) {
                            if (ore.getName().equals(containersToBeSent.get(j).getName())) {
                                canAdd = true;
                            }
                            prohibitedOres.add(ore.getName());
                            prohibitedOres.remove(containersToBeSent.get(j).getName());
                        }
                        if (canAdd) {
                            uniqueOres.addAll(prohibitedOres);
                        }
                    }

                    boolean isDangerousToAdd = false;
                    // Iterate the prohibited list and pick the non violating containers
                    for (String name : uniqueOres) {
                        if (containersToBeSent.get(j).getName().equals(name)) {
                            isDangerousToAdd = true;
                        }
                    }
                    if (!isDangerousToAdd) {
                        Container containerToBeCreated = null;
                        /**
                         * If a valid container, add it to the single trip
                         * and remove that from the list
                         * and remove the container from the available containers list
                         * and decrement the iterator variable
                         */
                        for (Container container : containers) {
                            if (containersToBeSent.get(j).getType().equals(container.getType())) {
                                containerToBeCreated = container;
                                containerToBeCreated.setOre(containersToBeSent.get(j).getName());
                                containerToBeAdded.add(containerToBeCreated);
                                containersToBeSent.remove(j);
                                j--;
                                break;
                            }
                        }
                        // To avoid current modification exception
                        if (containerToBeCreated != null) {
                            containers.remove(containerToBeCreated);
                        }
                    }
                }

                // Add the containers to the trip
                if (!containerToBeAdded.isEmpty()) {
                    trip = new Trip(containerToBeAdded);
                    trips.add(trip);
                }
            }
            return trips;
        } catch (Exception e) {
            throw new ExceptionHandler(ApplicationConstants.CONTAINER_SHIPPING_FAILED);
        }
    }
}
