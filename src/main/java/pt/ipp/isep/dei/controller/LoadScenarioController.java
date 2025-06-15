package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for loading a serialized Scenario object from a file.
 * Ensures that the scenario is valid and does not already exist in the repository.
 */
public class LoadScenarioController {

    /**
     * Loads a serialized scenario from the specified file path.
     * Validates the scenario and checks for duplicates based on its string representation.
     *
     * @param filePath the path to the serialized scenario file
     * @return true if the scenario was successfully loaded and added; false otherwise
     */
    public boolean loadSerializedScenario(String filePath) {
        Scenario scenario = Repositories.getInstance().getScenarioRepository().loadScenarioFromSerializedFile(filePath);
        if (scenario == null) {
            System.out.println("Failed to load scenario from file.");
            return false;
        }

        // Check for duplicate scenario using its string representation (e.g., map name + year)
        if (scenarioExists(scenario.toString())) {
            System.out.println("A scenario '" + scenario.toString() + "' already exists.");
            return false;
        }

        // Add the scenario to the repository
        Repositories.getInstance().getScenarioRepository().addScenario(scenario);
        System.out.println("Scenario loaded successfully from file.");
        return true;
    }

    /**
     * Checks whether a scenario with the given identifier (usually string representation) already exists.
     *
     * @param scenarioIdentifier the unique string identifier of the scenario (e.g., map name + year)
     * @return true if a matching scenario exists; false otherwise
     */
    private boolean scenarioExists(String scenarioIdentifier) {
        return Repositories.getInstance()
                .getScenarioRepository()
                .getScenarios()
                .stream()
                .anyMatch(s -> s.toString().equalsIgnoreCase(scenarioIdentifier));
    }
}
