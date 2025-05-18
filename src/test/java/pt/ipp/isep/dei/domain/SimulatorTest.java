package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorTest {

    private Scenario scenario;
    private Player player;
    private Simulator simulator;
    private Station station1;
    private Station station2;
    private Route route;

    @BeforeEach
    void setUp() {
        // Criar mapa
        Map map = new Map("TestMap", 10);

        // IndustryType e IndustryChanger para o Scenario
        List<IndustryType> industryTypes = List.of(IndustryType.COAL_MINE, IndustryType.STEEL_MILL);
        List<IndustryChanger> industryChangers = List.of(IndustryChanger.WAR, IndustryChanger.SCHOOLING_PROGRAM);

        // Criar cenário
        scenario = new Scenario(
                map,
                2025,
                List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL),
                industryTypes,
                industryChangers,
                new ArrayList<>(), // estações vazias inicialmente
                new ArrayList<>()  // rotas vazias inicialmente
        );

        // Criar estações
        station1 = new Station("Station1", StationType.TERMINAL);
        station2 = new Station("Station2", StationType.DEPOT);

        // Inicializar supplies e demands das estações
        station1.getSupplies().add(new Cargo(ResourceType.COAL, 0));
        station1.getSupplies().add(new Cargo(ResourceType.IRON, 0));
        station1.getDemands().add(new Cargo(ResourceType.COAL, 3));

        station2.getSupplies().add(new Cargo(ResourceType.COAL, 0));
        station2.getDemands().add(new Cargo(ResourceType.IRON, 1));

        // Criar locomotiva
        Locomotive locomotive = new Locomotive(1, LocomotiveModel.PACIFIC);

        // Criar rota com o construtor correto: startingStation, endingStation, locomotive, cargo
        List<Cargo> routeCargo = List.of(new Cargo(ResourceType.COAL, 1), new Cargo(ResourceType.IRON, 1));
        route = new Route(station1, station2, locomotive, routeCargo);

        // Se quiser, pode adicionar paradas intermediárias:
        // route.setStops(List.of(...));

        // Criar jogador
        player = new Player("testUser", "password", 1000);

        // Criar simulador
        simulator = new Simulator(List.of(route), player, scenario);
    }

    @Test
    void testSimulateDay_AddsCargoOnEvenDays() {
        simulator.simulateDay(2);  // dia par: adiciona cargos às estações

        assertTrue(station1.getSupplies().stream().anyMatch(c -> c.getType() == ResourceType.COAL && c.getQuantity() > 0));
        assertTrue(station1.getSupplies().stream().anyMatch(c -> c.getType() == ResourceType.IRON && c.getQuantity() > 0));
        assertTrue(station2.getSupplies().stream().anyMatch(c -> c.getType() == ResourceType.COAL && c.getQuantity() > 0));
    }

    @Test
    void testSimulateDay_DeliversAndPicksUpCargoOnMultipleOfStations() {
        int initialBudget = player.getBudget();

        simulator.simulateDay(2);  // gerar cargos
        simulator.simulateDay(2);  // entregar e recolher cargos

        assertTrue(player.getBudget() > initialBudget);

        for (Station station : List.of(station1, station2)) {
            for (Cargo cargo : station.getSupplies()) {
                assertEquals(0, cargo.getQuantity());
            }
        }
    }

    @Test
    void testGetSimulationSummary_ReturnsCorrectFormat() {
        simulator.simulateDay(2);
        simulator.simulateDay(2);

        String summary = simulator.getSimulationSummary(player);

        assertTrue(summary.contains("Total Resources Generated"));
        assertTrue(summary.contains("Total Resources Delivered"));
        assertTrue(summary.contains("Final Player Budget"));
    }
}
