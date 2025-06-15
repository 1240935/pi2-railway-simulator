package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for saving scenarios to files and retrieving scenario-related information.
 */
public class SaveScenarioController {

    /**
     * Repository instance to access and manage scenarios.
     */
    private final ScenarioRepository repository;

    /**
     * Constructs a SaveScenarioController and initializes the scenario repository.
     */
    public SaveScenarioController() {
        this.repository = Repositories.getInstance().getScenarioRepository();
    }

    /**
     * Saves the given scenario to the specified file path as a serialized object.
     *
     * @param scenario The scenario to be saved.
     * @param filePath The destination file path.
     * @return true if the scenario was successfully saved, false otherwise.
     */
    public boolean saveScenarioToFile(Scenario scenario, String filePath) {
        return repository.saveScenarioToSerializedFile(scenario, filePath);
    }

    /**
     * Retrieves the total number of scenarios stored in the repository.
     *
     * @return The number of available scenarios.
     */
    public int getScenarioCount() {
        return repository.getScenarios().size();
    }

    /**
     * Retrieves the names of all stored scenarios.
     * Uses the toString() method of Scenario, which returns a representation in the format "mapName (year)".
     *
     * @return A list of scenario names.
     */
    public List<String> getScenarioNames() {
        List<String> names = new ArrayList<>();
        for (Scenario scenario : repository.getScenarios()) {
            names.add(scenario.toString());
        }
        return names;
    }

    /**
     * Saves the scenario at the specified index to the given file path.
     *
     * @param index    The index of the scenario in the list.
     * @param filePath The destination file path.
     * @return true if the scenario was saved successfully, false otherwise.
     */
    public boolean saveScenarioByIndex(int index, String filePath) {
        List<Scenario> scenarios = repository.getScenarios();
        if (index < 0 || index >= scenarios.size()) {
            return false;
        }
        Scenario selectedScenario = scenarios.get(index);
        return repository.saveScenarioToSerializedFile(selectedScenario, filePath);
    }
}
