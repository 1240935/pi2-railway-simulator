package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Scenario;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for storing and managing {@link Scenario} objects.
 * Provides methods to add scenarios and retrieve all stored scenarios.
 */
public class ScenarioRepository {

    /** Internal list that stores the scenarios. */
    private final ArrayList<Scenario> scenarios = new ArrayList<>();

    /**
     * Adds a new {@link Scenario} to the repository if it is not null and not already present.
     *
     * @param scenario the {@link Scenario} to add
     */
    public void add(Scenario scenario) {
        if (scenario != null && !scenarios.contains(scenario)) {
            scenarios.add(scenario);
        }
    }

    /**
     * Returns a new {@link ArrayList} containing all the scenarios currently stored in the repository.
     * This prevents exposing the internal list directly for modification.
     *
     * @return a new {@link ArrayList} with all stored scenarios
     */
    public ArrayList<Scenario> getAll() {
        return new ArrayList<>(scenarios);
    }

    /**
     * Returns the internal list of scenarios.
     * Note: this method exposes the internal list directly, which may allow modifications.
     *
     * @return the internal {@link List} of scenarios
     */
    public List<Scenario> getScenarios() {
        return scenarios;
    }
}
