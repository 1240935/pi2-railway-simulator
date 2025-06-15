package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.PointOfRoute;
import pt.ipp.isep.dei.domain.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for storing and managing {@link Route} objects.
 * Provides methods to add routes and retrieve the list of stored routes.
 */
public class RouteRepository {

    /** Internal list that holds the stored routes. */
    private final ArrayList<Route> routes = new ArrayList<>();

    /**
     * Returns the list of all routes stored in the repository.
     * Note: this returns a direct reference to the internal list,
     * so external modifications will affect the repository's state.
     *
     * @return the list of {@link Route} objects
     */
    public ArrayList<Route> getRoutes() {
        return routes;
    }

    /**
     * Creates a new {@link Route} if parameters are valid.
     *
     * @param locomotive the locomotive associated with the route
     * @param pointsOfRoute the list of points in the route
     * @return a new Route object if parameters are valid; otherwise null
     */
    public static Route createRoute(Locomotive locomotive, List<PointOfRoute> pointsOfRoute) {
        Route route = new Route(locomotive, pointsOfRoute);
        if (route.checkLocomotive(locomotive) && route.checkPointsOfRoute(pointsOfRoute)) {
            System.out.println("Route created successfully!");
            return route;
        } else {
            System.out.println("Invalid route parameters.");
            return null;
        }
    }

    /**
     * Adds a {@link Route} to the repository.
     *
     * @param route the {@link Route} to be added
     */
    public void addRoute(Route route) {
        routes.add(route);
    }
}
