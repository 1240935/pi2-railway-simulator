package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Simulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for managing {@link Simulator} objects,
 * including saving/loading from files and managing the active simulator.
 */
public class SimulatorRepository {

    /** Currently active {@link Simulator}, or null if none set. */
    private Simulator activeSimulator = null;

    /** List storing all simulators added to the repository. */
    private final List<Simulator> simulators = new ArrayList<>();

    /**
     * Saves the currently active simulator to a serialized file (.ser).
     *
     * @param filepath the destination file path
     * @return true if successful, false otherwise
     */
    public boolean saveSimulatorToFile(String filepath) {
        if (activeSimulator == null) {
            System.err.println("No active simulator to save");
            return false;
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath))) {
            out.writeObject(activeSimulator);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving simulation: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads a simulator from a serialized file and sets it as the active simulator.
     *
     * @param filepath path to the serialized file
     * @return the deserialized {@link Simulator} object, or null if loading fails
     */
    public Simulator loadSimulatorFromFile(String filepath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))) {
            Simulator simulator = (Simulator) in.readObject();
            setActiveSimulator(simulator); // Set as active simulator
            return simulator;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading simulation: " + e.getMessage());
            return null;
        }
    }

    /**
     * Adds a simulator to the repository.
     *
     * @param simulator the {@link Simulator} to add
     */
    public void addSimulator(Simulator simulator) {
        simulators.add(simulator);
    }

    /**
     * Returns a new list containing all simulators in the repository.
     * Modifications to the returned list will not affect the repository.
     *
     * @return list of all simulators
     */
    public List<Simulator> getSimulators() {
        return new ArrayList<>(simulators);
    }

    /**
     * Retrieves a simulator by its name (case-insensitive).
     *
     * @param name the name of the simulator to find
     * @return the matching {@link Simulator} or null if none found
     */
    public Simulator getSimulatorByName(String name) {
        return simulators.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Sets the currently active simulator.
     *
     * @param simulator the {@link Simulator} to set as active
     */
    public void setActiveSimulator(Simulator simulator) {
        activeSimulator = simulator;
    }

    /**
     * Returns the currently active simulator.
     *
     * @return the active {@link Simulator} or null if none is set
     */
    public Simulator getActiveSimulator() {
        return activeSimulator;
    }

    /**
     * Checks if there is a currently active simulator.
     *
     * @return true if an active simulator is set, false otherwise
     */
    public boolean hasActiveSimulator() {
        return activeSimulator != null;
    }
}
