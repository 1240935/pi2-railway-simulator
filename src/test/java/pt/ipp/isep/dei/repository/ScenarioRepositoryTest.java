package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.*;


import java.io.File;
import java.io.IOException;
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
        Map map = new Map(5, mapName, 24);
        List<LocomotiveType> locoTypes = new ArrayList<>();
        List<ResourceType> importResources = new ArrayList<>();
        List<ResourceType> exportResources = new ArrayList<>();
        int industryChanger = 1;

        return new Scenario(map, year, locoTypes, importResources, exportResources, industryChanger);
    }

    @Test
    void addScenario_ShouldAddScenarioScenario_WhenScenarioIsNotNullAndNotDuplicate() {
        // Testa adicionar cenário válido à lista
        Scenario scenario = createScenario("Mapa A", 2025);

        repository.addScenario(scenario);

        List<Scenario> scenarios = repository.getScenarios();
        assertEquals(1, scenarios.size());
        assertTrue(scenarios.contains(scenario));
    }

    @Test
    void addScenario_ShouldNotAdd_Scenario_WhenScenarioIsNull() {
        // Testa que cenário nulo não é adicionado
        repository.addScenario(null);

        assertTrue(repository.getScenarios().isEmpty(), "Não deve adicionar cenário nulo.");
    }

    @Test
    void addScenario_ShouldNotAddScenarioDuplicateScenario() {
        // Testa que cenário duplicado não é adicionado
        Scenario scenario = createScenario("Mapa B", 2030);

        repository.addScenario(scenario);
        repository.addScenario(scenario);

        List<Scenario> scenarios = repository.getScenarios();
        assertEquals(1, scenarios.size(), "Não deve adicionar cenários duplicados.");
    }

    @Test
    void getAll_ShouldReturnCopyOfScenarios() {
        // Testa que getAll() retorna cópia e não referência da lista interna
        Scenario scenario1 = createScenario("Mapa C", 2020);
        Scenario scenario2 = createScenario("Mapa D", 2022);

        repository.addScenario(scenario1);
        repository.addScenario(scenario2);

        ArrayList<Scenario> allScenarios = repository.getAll();
        assertEquals(2, allScenarios.size());
        assertTrue(allScenarios.contains(scenario1));
        assertTrue(allScenarios.contains(scenario2));
        allScenarios.remove(scenario1);
        assertEquals(2, repository.getScenarios().size(), "Remover de getAll() não deve alterar o repositório interno.");
    }

    @Test
    void testStaticCreateScenario_AddsScenario() {
        // Testa método estático createScenario retorna cenário válido
        Map map = new Map(5, "Map Static", 30);
        List<LocomotiveType> locoTypes = new ArrayList<>();
        List<ResourceType> importResources = new ArrayList<>();
        List<ResourceType> exportResources = new ArrayList<>();
        int changer = 1;

        Scenario scenario = ScenarioRepository.createScenario(map, 2023, locoTypes, importResources, exportResources, changer);

        assertNotNull(scenario, "Static createScenario should return a valid scenario.");
    }

    @Test
    void testSaveAndLoadScenarioSerialization() throws IOException {
        // Testa salvar e carregar cenário via serialização
        Scenario scenario = createScenario("Map Serialization", 2021);
        String filePath = "testScenario.ser";

        boolean saved = repository.saveScenarioToSerializedFile(scenario, filePath);
        assertTrue(saved, "Scenario should be saved successfully.");

        Scenario loaded = repository.loadScenarioFromSerializedFile(filePath);
        assertNotNull(loaded, "Scenario should be loaded successfully.");
        assertEquals(scenario.getYear(), loaded.getYear(), "Loaded scenario year should match saved.");

        // Apaga arquivo após o teste
        new File(filePath).delete();
    }

    @Test
    void testAddDuplicateDifferentInstanceScenario() {
        // Testa adicionar dois cenários diferentes com dados iguais (depende de equals/hashCode)
        Scenario scenario1 = createScenario("Duplicate Map", 2024);
        Scenario scenario2 = createScenario("Duplicate Map", 2024);

        repository.addScenario(scenario1);
        repository.addScenario(scenario2);

        assertEquals(1, repository.getScenarios().size(), "Duplicate scenarios with same data should not be added.");
    }
}
