package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Simulator;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.SimulatorRepository;

/**
 * Controller responsible for loading a serialized Simulator from a file.
 */
public class LoadSimulatorController {

    /**
     * Reference to the simulator repository.
     */
    private final SimulatorRepository repository;

    /**
     * Constructs a LoadSimulatorController and initializes the simulator repository.
     */
    public LoadSimulatorController() {
        this.repository = Repositories.getInstance().getSimulatorRepository();
    }

    /**
     * Loads a Simulator instance from the given file path.
     * Returns true if loading was successful (i.e., simulator is not null), false otherwise.
     *
     * @param filePath the path to the serialized simulator file
     * @return true if simulator was loaded successfully; false otherwise
     */
    public boolean loadSerializedSimulator(String filePath) {
        Simulator simulator = repository.loadSimulatorFromFile(filePath);
        return simulator != null;
    }
}
