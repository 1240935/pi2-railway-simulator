package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

    private Map map;
    private Scenario scenario;

    @BeforeEach
    void setUp() {
        map = new Map(10, "TestMap", 40);

        scenario = new Scenario(
                map,
                2025,
                List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL),
                List.of(ResourceType.COAL, ResourceType.IRON), // importation
                List.of(ResourceType.STEEL),                   // exportation
                2 // exemplo de industryChanger (int)
        );
    }

    // Testa se o mapa retornado é o mesmo que foi atribuído
    @Test
    void testGetMap() {
        assertEquals(map, scenario.getMap());
    }

    // Testa se o ano retornado é o ano do cenário
    @Test
    void testGetYear() {
        assertEquals(2025, scenario.getYear());
    }

    // Testa se os tipos de locomotiva são os esperados
    @Test
    void testGetLocomotiveTypes() {
        assertEquals(List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL), scenario.getLocomotiveTypes());
    }

    // Testa se os tipos de recurso importados estão corretos
    @Test
    void testGetImportation() {
        assertEquals(List.of(ResourceType.COAL, ResourceType.IRON), scenario.getImportation());
    }

    // Testa se os tipos de recurso exportados estão corretos
    @Test
    void testGetExportation() {
        assertEquals(List.of(ResourceType.STEEL), scenario.getExportation());
    }

    // Testa se uma estação é adicionada corretamente
    @Test
    void testAddStation() {
        Station station = new Station("Central", StationType.TERMINAL);
        scenario.addStation(station);
        assertTrue(scenario.getStations().contains(station));
    }

    // Testa se uma locomotiva é adicionada corretamente
    @Test
    void testAddLocomotive() {
        LocomotiveModel model = LocomotiveModel.PACIFIC;
        Locomotive locomotive = new Locomotive(1, model);
        scenario.addLocomotive(locomotive);
        assertTrue(scenario.getLocomotives().contains(locomotive));
    }

    // Testa se uma rota é adicionada corretamente ao cenário
    @Test
    void testAddRoute() {
        Station s1 = new Station("S1", StationType.TERMINAL);
        Station s2 = new Station("S2", StationType.DEPOT);
        Locomotive locomotive = new Locomotive(1, LocomotiveModel.CLASS_55_DELTIC);

        List<Cargo> cargo1 = List.of(new Cargo(ResourceType.COAL, 100));
        List<Cargo> cargo2 = List.of(new Cargo(ResourceType.STEEL, 50));

        PointOfRoute point1 = new PointOfRoute(s1, cargo1, CargoMode.FULL);
        PointOfRoute point2 = new PointOfRoute(s2, cargo2, CargoMode.AVAILABLE);

        List<PointOfRoute> points = List.of(point1, point2);

        Route route = new Route(locomotive, points);

        scenario.addRoute(route);
        assertTrue(scenario.getRoutes().contains(route));
    }

    // Testa a representação textual do cenário (toString)
    @Test
    void testToString() {
        assertEquals("TestMap (2025)", scenario.toString());
    }

    // Testa adição de múltiplas estações e locomotivas
    @Test
    void testMultipleAdditions() {
        scenario.addStation(new Station("One", StationType.STATION));
        scenario.addStation(new Station("Two", StationType.DEPOT));
        assertEquals(2, scenario.getStations().size());

        scenario.addLocomotive(new Locomotive(1, LocomotiveModel.A3));
        scenario.addLocomotive(new Locomotive(2, LocomotiveModel.JOHN_BULL));
        assertEquals(2, scenario.getLocomotives().size());
    }

    // Testa o toString com valores diferentes de mapa e ano
    @Test
    void testToStringDifferentValues() {
        Map anotherMap = new Map(15, "SecondMap", 26);
        Scenario anotherScenario = new Scenario(anotherMap, 1980,
                List.of(LocomotiveType.ELECTRIC),
                List.of(ResourceType.OIL),
                List.of(ResourceType.STEEL),
                3);
        assertEquals("SecondMap (1980)", anotherScenario.toString());
    }
}
