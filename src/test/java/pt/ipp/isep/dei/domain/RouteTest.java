package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    private Station startStation;
    private Station endStation;
    private List<Station> stops;
    private Locomotive locomotive;
    private List<Cargo> cargo;
    private Route route;

    @BeforeEach
    void setUp() {
        startStation = new Station("StartStation", StationType.DEPOT);
        endStation = new Station("EndStation", StationType.TERMINAL);
        stops = new ArrayList<>(List.of(
                new Station("Stop1", StationType.STATION),
                new Station("Stop2", StationType.STATION)
        ));
        locomotive = new Locomotive(0, LocomotiveModel.GG1);
        cargo = new ArrayList<>();
        cargo.add(new Cargo(ResourceType.COAL, 100));
        cargo.add(new Cargo(ResourceType.STEEL, 50));

        route = new Route();
        route.setStartingStation(startStation);
        route.setEndingStation(endStation);
        route.setStops(stops);
        route.setLocomotive(locomotive);
        route.setCargo(cargo);
    }

    @Test
    void getStartingStation() {
        assertEquals(startStation, route.getStartingStation());
    }

    @Test
    void setStartingStation() {
        Station newStart = new Station("NewStart", StationType.DEPOT);
        route.setStartingStation(newStart);
        assertEquals(newStart, route.getStartingStation());
    }

    @Test
    void getEndingStation() {
        assertEquals(endStation, route.getEndingStation());
    }

    @Test
    void setEndingStation() {
        Station newEnd = new Station("NewEnd", StationType.TERMINAL);
        route.setEndingStation(newEnd);
        assertEquals(newEnd, route.getEndingStation());
    }

    @Test
    void getStops() {
        assertEquals(stops, route.getStops());
    }

    @Test
    void setStops() {
        List<Station> newStops = List.of(new Station("NewStop", StationType.STATION));
        route.setStops(newStops);
        assertEquals(newStops, route.getStops());
    }

    @Test
    void getLocomotive() {
        assertEquals(locomotive, route.getLocomotive());
    }

    @Test
    void setLocomotive() {
        Locomotive newLoco = new Locomotive(2, LocomotiveModel.MIKADO);
        route.setLocomotive(newLoco);
        assertEquals(newLoco, route.getLocomotive());
    }

    @Test
    void getCargo() {
        assertEquals(cargo, route.getCargo());
    }

    @Test
    void setCargo() {
        List<Cargo> newCargo = List.of(new Cargo(ResourceType.IRON, 75));
        route.setCargo(newCargo);
        assertEquals(newCargo, route.getCargo());
    }

    @Test
    void getRouteStations() {
        List<Station> expected = new ArrayList<>();
        expected.add(startStation);
        expected.addAll(stops);
        expected.add(endStation);
        assertEquals(expected, route.getRouteStations());
    }

    @Test
    void testToString() {
        String str = route.toString();
        assertNotNull(str);
        assertTrue(str.contains("StartStation"));
        assertTrue(str.contains("EndStation"));
    }
}
