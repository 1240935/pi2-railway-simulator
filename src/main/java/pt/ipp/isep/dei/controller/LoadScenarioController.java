package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.Repositories;

public class LoadScenarioController {

    public boolean loadSerializedScenario(String filePath) {
        Scenario scenario = Repositories.getInstance().getScenarioRepository().loadScenarioFromSerializedFile(filePath);
        if (scenario == null) {
            System.out.println("Failed to load scenario from file.");
            return false;
        }

        // Verificar duplicado usando representação string (mapName + year)
        if (scenarioExists(scenario.toString())) {
            System.out.println("A scenario '" + scenario.toString() + "' already exists.");
            return false;
        }

        Repositories.getInstance().getScenarioRepository().addScenario(scenario);
        System.out.println("Scenario loaded successfully from file.");
        return true;
    }

    private boolean scenarioExists(String scenarioIdentifier) {
        return Repositories.getInstance()
                .getScenarioRepository()
                .getScenarios()
                .stream()
                .anyMatch(s -> s.toString().equalsIgnoreCase(scenarioIdentifier));
    }
}