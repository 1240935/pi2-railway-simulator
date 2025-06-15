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
        Map map = new Map(10, mapName, 2);
        List<LocomotiveType> locoTypes = new ArrayList<>();
        List<ResourceType> importResources = new ArrayList<>();
        List<ResourceType> exportResources = new ArrayList<>();
        int changer = 2;
        return new Scenario(map, year, locoTypes, importResources, exportResources, changer);
    }

    @Test
    void addScenario_ShouldAddScenario_WhenValidAndNotDuplicate() {
        Scenario scenario = createScenario("Mapa A", 2025);
        repository.addScenario(scenario);

        List<Scenario> scenarios = repository.getScenarios();
        assertEquals(1, scenarios.size());
        assertTrue(scenarios.contains(scenario));
    }

    @Test
    void addScenario_ShouldNotAddScenario_WhenNull() {
        repository.addScenario(null);
        assertTrue(repository.getScenarios().isEmpty());
    }

    @Test
    void addScenario_ShouldNotAddScenario_WhenDuplicate() {
        Scenario scenario = createScenario("Mapa B", 2030);
        repository.addScenario(scenario);
        repository.addScenario(scenario); // Duplicate

        List<Scenario> scenarios = repository.getScenarios();
        assertEquals(1, scenarios.size());
    }

    @Test
    void getAll_ShouldReturnCopy_NotAffectingOriginalList() {
        Scenario scenario1 = createScenario("Mapa C", 2020);
        Scenario scenario2 = createScenario("Mapa D", 2022);

        repository.addScenario(scenario1);
        repository.addScenario(scenario2);

        ArrayList<Scenario> all = repository.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(scenario1));
        assertTrue(all.contains(scenario2));

        // Modify the returned list
        all.remove(scenario1);
        assertEquals(2, repository.getScenarios().size(), "Internal list should not be affected");
    }

    @Test
    void saveAndLoadScenario_ShouldPersistAndRetrieveScenarioCorrectly() {
        Scenario scenario = createScenario("Mapa Persist", 2040);
        String filePath = "test_scenario.ser";

        boolean saved = repository.saveScenarioToSerializedFile(scenario, filePath);
        assertTrue(saved);

        Scenario loaded = repository.loadScenarioFromSerializedFile(filePath);
        assertNotNull(loaded);
        assertEquals(scenario.toString(), loaded.toString());
        assertEquals(scenario.getYear(), loaded.getYear());
        assertEquals(scenario.getMap().getName(), loaded.getMap().getName());

        // Cleanup (optional, comment out if you want to inspect the file)
        new java.io.File(filePath).delete();
    }
}
