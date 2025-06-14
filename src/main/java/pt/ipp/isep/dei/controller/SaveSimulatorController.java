package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.SimulatorRepository;


public class SaveSimulatorController {
    private final SimulatorRepository repository;

    public SaveSimulatorController() {
        this.repository = Repositories.getInstance().getSimulatorRepository();
    }

    public boolean hasActiveSimulator() {
        return repository.hasActiveSimulator();
    }

    public boolean saveSimulatorToFile(String filePath) {
        return repository.saveSimulatorToFile(filePath);
    }
}