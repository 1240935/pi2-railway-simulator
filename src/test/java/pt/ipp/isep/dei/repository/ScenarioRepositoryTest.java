package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioRepositoryTest {

    private ScenarioRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ScenarioRepository();
    }

    private Scenario createScenario(String mapName, int year) {
        Map map = new Map(mapName, 10); // supondo que Map tenha construtor com nome
        List<LocomotiveType> locoTypes = new ArrayList<>();
        List<IndustryType> industryTypes = new ArrayList<>();
        List<IndustryChanger> industryChangers = new ArrayList<>();
        List<ResourceType> importResources = new ArrayList<>();
        List<ResourceType> exportResources = new ArrayList<>();

        return new Scenario(map, year, locoTypes, industryTypes, industryChangers, importResources, exportResources);
    }

    @Test
    void addScenario_ShouldAddScenario_WhenScenarioIsNotNullAndNotDuplicate() {
        Scenario scenario = createScenario("Mapa A", 2025);

        repository.add(scenario);

        List<Scenario> scenarios = repository.getScenarios();
        assertEquals(1, scenarios.size());
        assertTrue(scenarios.contains(scenario));
    }

    @Test
    void addScenario_ShouldNotAdd_WhenScenarioIsNull() {
        repository.add(null);

        assertTrue(repository.getScenarios().isEmpty(), "Não deve adicionar cenário nulo.");
    }

    @Test
    void addScenario_ShouldNotAddDuplicateScenario() {
        Scenario scenario = createScenario("Mapa B", 2030);

        repository.add(scenario);
        repository.add(scenario);

        List<Scenario> scenarios = repository.getScenarios();
        assertEquals(1, scenarios.size(), "Não deve adicionar cenários duplicados.");
    }

    @Test
    void getAll_ShouldReturnCopyOfScenarios() {
        Scenario scenario1 = createScenario("Mapa C", 2020);
        Scenario scenario2 = createScenario("Mapa D", 2022);

        repository.add(scenario1);
        repository.add(scenario2);

        ArrayList<Scenario> allScenarios = repository.getAll();
        assertEquals(2, allScenarios.size());
        assertTrue(allScenarios.contains(scenario1));
        assertTrue(allScenarios.contains(scenario2));
        allScenarios.remove(scenario1);
        assertEquals(2, repository.getScenarios().size(), "Remover de getAll() não deve alterar o repositório interno.");
    }
}
