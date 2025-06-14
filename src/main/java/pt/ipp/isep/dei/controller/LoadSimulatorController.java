package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Simulator;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.*;

public class LoadSimulatorController {
    private final SimulatorRepository repository;

    public LoadSimulatorController() {
        this.repository = Repositories.getInstance().getSimulatorRepository();
    }

    public boolean loadSerializedSimulator(String filePath) {
        Simulator simulator = repository.loadSimulatorFromFile(filePath);
        return simulator != null;
    }
}