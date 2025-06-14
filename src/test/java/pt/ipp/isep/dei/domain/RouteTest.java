package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    private Locomotive locomotive;
    private Route route;
    private List<PointOfRoute> pointsOfRoute;

    @BeforeEach
    void setUp() {
        Station station1 = new Station("Station1", StationType.STATION);
        Station station2 = new Station("Station2", StationType.STATION);
        List<Cargo> cargoList1 = List.of(new Cargo(ResourceType.COAL, 100));
        List<Cargo> cargoList2 = List.of(new Cargo(ResourceType.STEEL, 50));

        PointOfRoute point1 = new PointOfRoute(station1, cargoList1, CargoMode.FULL);
        PointOfRoute point2 = new PointOfRoute(station2, cargoList2, CargoMode.AVAILABLE);

        pointsOfRoute = List.of(point1, point2);
        locomotive = new Locomotive(1, LocomotiveModel.GG1);
        route = new Route(locomotive, pointsOfRoute);
    }

    // Testa se o getLocomotive retorna a locomotiva correta
    @Test
    void testGetLocomotive() {
        assertEquals(locomotive, route.getLocomotive());
    }

    // Testa se setLocomotive atualiza corretamente a locomotiva
    @Test
    void testSetLocomotive() {
        Locomotive newLocomotive = new Locomotive(2, LocomotiveModel.JOHN_BULL);
        route.setLocomotive(newLocomotive);
        assertEquals(newLocomotive, route.getLocomotive());
    }

    // Testa se getPointsOfRoute retorna corretamente os pontos da rota
    @Test
    void testGetPointsOfRoute() {
        assertEquals(pointsOfRoute, route.getPointsOfRoute());
    }

    // Testa se setPointsOfRoute atualiza corretamente os pontos da rota
    @Test
    void testSetPointsOfRoute() {
        Station newStation = new Station("Station3", StationType.TERMINAL);
        List<Cargo> newCargoList = List.of(new Cargo(ResourceType.IRON, 75));
        PointOfRoute newPoint = new PointOfRoute(newStation, newCargoList, CargoMode.HALF);

        List<PointOfRoute> newPoints = List.of(newPoint);
        route.setPointsOfRoute(newPoints);
        assertEquals(newPoints, route.getPointsOfRoute());
    }

    // Testa se checkPointsOfRoute retorna true para lista válida
    @Test
    void testCheckPointsOfRoute_Valid() {
        assertTrue(route.checkPointsOfRoute(pointsOfRoute));
    }

    // Testa se checkPointsOfRoute retorna false para lista nula
    @Test
    void testCheckPointsOfRoute_Invalid_NullList() {
        assertFalse(route.checkPointsOfRoute(null));
    }

    // Testa se checkPointsOfRoute retorna false para lista vazia
    @Test
    void testCheckPointsOfRoute_Invalid_EmptyList() {
        assertFalse(route.checkPointsOfRoute(new ArrayList<>()));
    }

    // Testa se checkLocomotive retorna true para uma locomotiva válida
    @Test
    void testCheckLocomotive_Valid() {
        assertTrue(route.checkLocomotive(locomotive));
    }

    // Testa se checkLocomotive retorna false para uma locomotiva nula
    @Test
    void testCheckLocomotive_Invalid_Null() {
        assertFalse(route.checkLocomotive(null));
    }

    // Testa se o método toString inclui as informações principais da rota
    @Test
    void testToStringContainsExpectedInfo() {
        String result = route.toString();
        assertTrue(result.contains("Route:"));
        assertTrue(result.contains("Locomotive"));
        assertTrue(result.contains("Points of Route"));
    }

    // Testa se checkPointsOfRoute retorna false quando há um ponto nulo na lista
    @Test
    void testCheckPointsOfRoute_InvalidPoint() {
        List<PointOfRoute> invalidPoints = new ArrayList<>(pointsOfRoute);
        invalidPoints.set(0, null);
        assertFalse(route.checkPointsOfRoute(invalidPoints));
    }
}
