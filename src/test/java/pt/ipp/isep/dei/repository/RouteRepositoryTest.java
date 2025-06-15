package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.LocomotiveModel;
import pt.ipp.isep.dei.domain.PointOfRoute;
import pt.ipp.isep.dei.domain.Route;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteRepositoryTest {

    private RouteRepository repository;

    @BeforeEach
    void setUp() {
        repository = new RouteRepository();
    }

    // Testa adicionar rotas e recuperar a lista com as rotas
    @Test
    void testAddRouteAndGetRoutes() {
        Route route1 = new Route(/* construtor com dados se precisar */);
        Route route2 = new Route(/* construtor com dados se precisar */);
        assertTrue(repository.getRoutes().isEmpty(), "The route list should start empty.");
        repository.addRoute(route1);
        repository.addRoute(route2);
        ArrayList<Route> routes = repository.getRoutes();
        assertEquals(2, routes.size(), "The route list should contain 2 elements.");
        assertTrue(routes.contains(route1), "The list should contain route1.");
        assertTrue(routes.contains(route2), "The list should contain route2.");
    }

    // Testa se getRoutes() retorna a mesma lista interna (referência direta)
    @Test
    void testGetRoutes_returnsDirectReference() {
        ArrayList<Route> returnedRoutes = repository.getRoutes();
        Route route = new Route(new Locomotive(4, LocomotiveModel.BOBO), new ArrayList<>());

        returnedRoutes.add(route);
        assertEquals(1, repository.getRoutes().size(), "Modifications to the returned list should affect the repository.");
    }

    // Testa o método estático createRoute para rota válida
    @Test
    void testCreateRoute_validRoute() {
        Locomotive loco = new Locomotive(3, LocomotiveModel.EE);
        List<PointOfRoute> points = new ArrayList<>(); // Assuma pontos válidos aqui
        Route route = RouteRepository.createRoute(loco, points);

        assertNotNull(route, "A valid route should be created.");
        assertEquals(loco, route.getLocomotive());
        assertEquals(points, route.getPointsOfRoute());
    }

    // Testa o método estático createRoute para rota inválida
    @Test
    void testCreateRoute_invalidRoute() {
        Locomotive loco = null;
        List<PointOfRoute> points = null;

        Route route = RouteRepository.createRoute(loco, points);
        assertNull(route, "An invalid route should return null.");
    }

    // Testa adicionar null na lista
    @Test
    void testAddNullRoute() {
        assertDoesNotThrow(() -> repository.addRoute(null), "Adding null should not throw an exception.");
        assertTrue(repository.getRoutes().contains(null), "The list should contain null after adding.");
    }
}
