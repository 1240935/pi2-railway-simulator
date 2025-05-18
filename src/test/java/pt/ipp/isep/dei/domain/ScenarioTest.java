package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

    private Map map;
    private Scenario scenario;

    @BeforeEach
    void setUp() {
        map = new Map("TestMap", 10);

        scenario = new Scenario(
                map,
                2025,
                List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL),
                List.of(IndustryType.COAL_MINE, IndustryType.CEMENT_PLANT),       // industryTypes
                List.of(IndustryChanger.WAR, IndustryChanger.SCHOOLING_PROGRAM), // industryChangers
                List.of(ResourceType.COAL, ResourceType.IRON),                    // importation
                List.of(ResourceType.STEEL)                                       // exportation
        );
    }


    @Test
    void testGetMap() {
        assertEquals(map, scenario.getMap());
    }

    @Test
    void testGetYear() {
        assertEquals(2025, scenario.getYear());
    }

    @Test
    void testGetLocomotiveTypes() {
        assertEquals(List.of(LocomotiveType.STEAM, LocomotiveType.DIESEL), scenario.getLocomotiveTypes());
    }

    @Test
    void testGetImportation() {
        assertEquals(List.of(ResourceType.COAL, ResourceType.IRON), scenario.getImportation());
    }

    @Test
    void testGetExportation() {
        assertEquals(List.of(ResourceType.STEEL), scenario.getExportation());
    }

    @Test
    void testAddStation() {
        Station station = new Station("Central", StationType.TERMINAL);
        scenario.addStation(station);
        assertTrue(scenario.getStations().contains(station));
    }

    @Test
    void testAddLocomotive() {
        // Ajusta conforme a implementação concreta de LocomotiveModel
        LocomotiveModel model = LocomotiveModel.PACIFIC; // Exemplo enum
        Locomotive locomotive = new Locomotive(1, model);
        scenario.addLocomotive(locomotive);
        assertTrue(scenario.getLocomotives().contains(locomotive));
    }

    @Test
    void testAddRoute() {
        Station s1 = new Station("S1", StationType.TERMINAL);
        Station s2 = new Station("S2", StationType.DEPOT);

        LocomotiveModel model = LocomotiveModel.CLASS_55_DELTIC; // Exemplo enum
        Locomotive locomotive = new Locomotive(1, model);

        List<Cargo> cargoList = new ArrayList<>();

        Route route = new Route(s1, s2, locomotive, cargoList);
        scenario.addRoute(route);
        assertTrue(scenario.getRoutes().contains(route));
    }

    @Test
    void testToString() {
        assertEquals("TestMap (2025)", scenario.toString());
    }
}
