package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Route;
import pt.ipp.isep.dei.repository.RouteRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RouteRepositoryTest {

    private RouteRepository repository;

    @BeforeEach
    void setUp() {
        repository = new RouteRepository();
    }

    @Test
    void testAddRouteAndGetRoutes() {
        Route route1 = new Route(/* construtor com dados se precisar */);
        Route route2 = new Route(/* construtor com dados se precisar */);
        assertTrue(repository.getRoutes().isEmpty(), "A lista de rotas deve iniciar vazia.");
        repository.addRoute(route1);
        repository.addRoute(route2);
        ArrayList<Route> routes = repository.getRoutes();
        assertEquals(2, routes.size(), "A lista de rotas deve conter 2 elementos.");
        assertTrue(routes.contains(route1), "A lista deve conter route1.");
        assertTrue(routes.contains(route2), "A lista deve conter route2.");
    }
}
