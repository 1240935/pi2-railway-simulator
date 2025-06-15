package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        Map map = new Map(10, "TestMap", 20);

        scenario = new Scenario(
                map,
                2025,
                List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL),
                List.of(ResourceType.COAL, ResourceType.IRON),
                List.of(ResourceType.STEEL),
                1
        );

        station1 = new Station("Station1", StationType.TERMINAL);
        station2 = new Station("Station2", StationType.DEPOT);

        station1.getSupplies().add(new Cargo(ResourceType.COAL, 0));
        station1.getSupplies().add(new Cargo(ResourceType.IRON, 0));
        station1.getDemands().add(new Cargo(ResourceType.COAL, 3));

        station2.getSupplies().add(new Cargo(ResourceType.COAL, 0));
        station2.getDemands().add(new Cargo(ResourceType.IRON, 1));

        Locomotive locomotive = new Locomotive(1, LocomotiveModel.PACIFIC);

        List<Cargo> routeCargo = List.of(
                new Cargo(ResourceType.COAL, 1),
                new Cargo(ResourceType.IRON, 1)
        );

        PointOfRoute point1 = new PointOfRoute(station1, routeCargo, CargoMode.FULL);
        PointOfRoute point2 = new PointOfRoute(station2, routeCargo, CargoMode.AVAILABLE);
        route = new Route(locomotive, List.of(point1, point2));

        player = new Player("testUser", "password", 1000);

        simulator = new Simulator(List.of(route), player, scenario);
    }

    // Verifica se ao simular um mês o mês avança e o orçamento do jogador aumenta ou permanece igual
    @Test
    void testSimulateMonth_IncrementsMonthAndAddsRevenue() {
        int initialMonth = simulator.getCurrentSimulationDate().contains("Month 1") ? 1 : -1;
        int initialBudget = player.getBudget();

        simulator.simulateMonth();

        assertNotEquals("Month " + initialMonth, simulator.getCurrentSimulationDate());
        assertTrue(player.getBudget() >= initialBudget, "Player budget should not decrease after month simulation");
    }

    // Verifica se o resumo do último mês é atualizado corretamente após simulação
    @Test
    void testGetLastMonthSummary_ReturnsSummaryAfterSimulation() {
        String beforeSummary = simulator.getLastMonthSummary();
        assertEquals("No month simulated yet.", beforeSummary);

        simulator.simulateMonth();
        String afterSummary = simulator.getLastMonthSummary();

        assertTrue(afterSummary.contains("Summary for Month"));
        assertTrue(afterSummary.contains("Player budget"));
    }

    // Verifica se o nome do simulador começa corretamente com o nome do mapa e o mês
    @Test
    void testNameGeneration() {
        String name = simulator.getName();
        assertTrue(name.startsWith("TestMap-"), "Simulator name should start with map name followed by month");
    }

    // Verifica se a data da simulação muda após simular um mês
    @Test
    void testCurrentSimulationDate_ChangesEachMonth() {
        String dateBefore = simulator.getCurrentSimulationDate();
        simulator.simulateMonth();
        String dateAfter = simulator.getCurrentSimulationDate();

        assertNotEquals(dateBefore, dateAfter, "Simulation date should change after simulating a month");
    }

    // Verifica se simular um mês com rotas vazias não causa erros e gera resumo correto
    @Test
    void testSimulateMonth_WithNoRoutes_ShouldNotCrash() {
        Simulator emptyRouteSimulator = new Simulator(List.of(), player, scenario);
        assertDoesNotThrow(emptyRouteSimulator::simulateMonth);
        assertEquals("Summary for Month 1:\nPlayer budget: 1000", emptyRouteSimulator.getLastMonthSummary());
    }

    // Verifica que simular um mês com carga inválida (excesso) é tratado sem crash e orçamento não aumenta
    @Test
    void testSimulateMonth_WithInvalidCargo_ShouldHandleGracefully() {
        Locomotive weakLocomotive = new Locomotive(1, LocomotiveModel.SDP40);
        PointOfRoute point1 = new PointOfRoute(station1, List.of(new Cargo(ResourceType.COAL, 100)), CargoMode.FULL);
        PointOfRoute point2 = new PointOfRoute(station2, List.of(new Cargo(ResourceType.COAL, 100)), CargoMode.AVAILABLE);

        Route overloadedRoute = new Route(weakLocomotive, List.of(point1, point2));
        Simulator overloadedSimulator = new Simulator(List.of(overloadedRoute), player, scenario);

        overloadedSimulator.simulateMonth();

        assertTrue(player.getBudget() <= 1000);
    }

    // Verifica se simular um mês com rota que tem múltiplos pontos funciona corretamente e aumenta orçamento
    @Test
    void testSimulateMonth_WithMultipleRoutePoints() {
        Station midStation = new Station("MidStation", StationType.DEPOT);

        PointOfRoute point1 = new PointOfRoute(station1, List.of(new Cargo(ResourceType.COAL, 1)), CargoMode.FULL);
        PointOfRoute point2 = new PointOfRoute(midStation, List.of(), CargoMode.HALF);
        PointOfRoute point3 = new PointOfRoute(station2, List.of(new Cargo(ResourceType.COAL, 1)), CargoMode.AVAILABLE);

        Route complexRoute = new Route(new Locomotive(2, LocomotiveModel.PACIFIC), List.of(point1, point2, point3));
        Simulator complexSimulator = new Simulator(List.of(complexRoute), player, scenario);

        complexSimulator.simulateMonth();

        assertTrue(player.getBudget() > 1000);
    }
}
