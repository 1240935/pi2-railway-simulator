package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.ScenarioRepository;

import java.util.ArrayList;
import java.util.List;

public class SaveScenarioController {

    private final ScenarioRepository repository;

    public SaveScenarioController() {
        this.repository = Repositories.getInstance().getScenarioRepository();
    }

    public boolean saveScenarioToFile(Scenario scenario, String filePath) {
        return repository.saveScenarioToSerializedFile(scenario, filePath);
    }

    public int getScenarioCount() {
        return repository.getScenarios().size();
    }

    public List<String> getScenarioNames() {
        List<String> names = new ArrayList<>();
        for (Scenario scenario : repository.getScenarios()) {
            names.add(scenario.toString()); // Usa toString() que retorna "mapName (year)"
        }
        return names;
    }

    public boolean saveScenarioByIndex(int index, String filePath) {
        List<Scenario> scenarios = repository.getScenarios();
        if (index < 0 || index >= scenarios.size()) {
            return false;
        }
        Scenario selectedScenario = scenarios.get(index);
        return repository.saveScenarioToSerializedFile(selectedScenario, filePath);
    }
}