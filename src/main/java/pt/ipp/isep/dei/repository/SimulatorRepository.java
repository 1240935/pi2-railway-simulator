package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Simulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimulatorRepository {
    private Simulator activeSimulator = null;
    private final List<Simulator> simulators = new ArrayList<>();
    /**
     * Saves a scenario object to a serialized file (.ser)
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
     * Loads a serialized simulator from a file
     *
     * @param filepath path to the serialized file
     * @return deserialized simulator object or null if failed
     */
    public Simulator loadSimulatorFromFile(String filepath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))) {
            Simulator simulator = (Simulator) in.readObject();
            setActiveSimulator(simulator); // Define como ativo
            return simulator;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading simulation: " + e.getMessage());
            return null;
        }
    }
    public void addSimulator(Simulator simulator) {
        simulators.add(simulator);
    }

    public List<Simulator> getSimulators() {
        return new ArrayList<>(simulators);
    }

    public Simulator getSimulatorByName(String name) {
        return simulators.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void setActiveSimulator(Simulator simulator) {
        activeSimulator = simulator;
    }

    public Simulator getActiveSimulator() {
        return activeSimulator;
    }

    public boolean hasActiveSimulator() {
        return activeSimulator != null;
    }
}
