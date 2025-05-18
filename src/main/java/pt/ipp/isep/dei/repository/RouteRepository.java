package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Route;

import java.util.ArrayList;

public class RouteRepository {

    private ArrayList<Route> routes = new ArrayList<>();

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }
}
