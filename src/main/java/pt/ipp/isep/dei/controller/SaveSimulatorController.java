package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.SimulatorRepository;

/**
 * Controller responsible for managing saving operations related to the Simulator.
 */
public class SaveSimulatorController {

    /**
     * Repository instance to access and manage Simulator data.
     */
    private final SimulatorRepository repository;

    /**
     * Constructs a SaveSimulatorController and initializes the Simulator repository.
     */
    public SaveSimulatorController() {
        this.repository = Repositories.getInstance().getSimulatorRepository();
    }

    /**
     * Checks if there is an active simulator currently loaded or running.
     *
     * @return true if there is an active simulator, false otherwise.
     */
    public boolean hasActiveSimulator() {
        return repository.hasActiveSimulator();
    }

    /**
     * Saves the current active simulator state to the specified file path.
     *
     * @param filePath The destination file path where the simulator state will be saved.
     * @return true if the save operation was successful, false otherwise.
     */
    public boolean saveSimulatorToFile(String filePath) {
        return repository.saveSimulatorToFile(filePath);
    }
}
