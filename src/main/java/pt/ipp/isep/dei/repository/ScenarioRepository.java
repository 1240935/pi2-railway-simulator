package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.LocomotiveType;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.domain.ResourceType;
import pt.ipp.isep.dei.domain.Scenario;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Repository class responsible for storing and managing {@link Scenario} objects.
 * Provides methods to addScenario scenarios and retrieve all stored scenarios.
 */
public class ScenarioRepository {

    /** Internal list that stores the scenarios. */
    private final ArrayList<Scenario> scenarios = new ArrayList<>();

    /**
     * Adds a new {@link Scenario} to the repository if it is not null and not already present.
     *
     * @param scenario the {@link Scenario} to addScenario
     */
    public void addScenario(Scenario scenario) {
        if (scenario != null && !scenarios.contains(scenario)) {
            scenarios.add(scenario);
        }
    }

    /**
     * Creates a new {@link Scenario} object with the provided parameters,
     * adds it to a new repository instance, and returns it.
     *
     * @param map the {@link Map} object associated with the scenario
     * @param year the year of the scenario
     * @param locomotiveTypes list of {@link LocomotiveType} for the scenario
     * @param importation list of {@link ResourceType} imported in the scenario
     * @param exportation list of {@link ResourceType} exported in the scenario
     * @param changer an integer parameter (purpose based on domain logic)
     * @return the created {@link Scenario} object
     */
    public static Scenario createScenario(Map map, int year, List<LocomotiveType> locomotiveTypes,
                                          List<ResourceType> importation, List<ResourceType> exportation, int changer){
        ScenarioRepository scenarioRepository = new ScenarioRepository();
        Scenario scenario = new Scenario(map,year,locomotiveTypes,importation,exportation,changer);
        scenarioRepository.addScenario(scenario);
        return scenario;
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

    /**
     * Saves a scenario object to a serialized file (.ser).
     *
     * @param scenario the scenario to serialize
     * @param filePath the destination file path
     * @return true if successful, false otherwise
     */
    public boolean saveScenarioToSerializedFile(Scenario scenario, String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(scenario);
            return true;
        } catch (IOException e) {
            System.err.println("Error serializing scenario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads a serialized scenario from a file.
     *
     * @param filePath path to the serialized file
     * @return deserialized Scenario object or null if failed
     */
    public Scenario loadScenarioFromSerializedFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Scenario scenario = (Scenario) in.readObject();
            return scenario;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading scenario: " + e.getMessage());
            return null;
        }
    }

}
