package pt.ipp.isep.dei.repository;
import pt.ipp.isep.dei.domain.Scenario;
import java.util.ArrayList;
import java.util.List;

public class ScenarioRepository {
    private final ArrayList<Scenario> scenarios = new ArrayList<>();

    public void add(Scenario scenario) {
        if (scenario != null && !scenarios.contains(scenario)) {
            scenarios.add(scenario);
        }
    }

    public ArrayList<Scenario> getAll() {
        return new ArrayList<>(scenarios);
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }
}
