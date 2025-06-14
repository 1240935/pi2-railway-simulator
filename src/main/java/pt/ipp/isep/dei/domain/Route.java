package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Route implements Serializable {

    /**
     * The locomotive assigned to this route.
     */
    private Locomotive locomotive;

    /**
     * The list of points of the route, each with its own station and cargo list.
     */
    private List<PointOfRoute> pointsOfRoute;

    /**
     * Constructs a Route with a specified locomotive and list of points of route.
     *
     * @param locomotive the locomotive assigned to the route
     * @param pointsOfRoute the ordered points of the route
     */
    public Route(Locomotive locomotive, List<PointOfRoute> pointsOfRoute) {
        this.locomotive = locomotive;
        this.pointsOfRoute = pointsOfRoute;
    }

    /**
     * Default constructor for Route.
     */
    public Route() {
        this.pointsOfRoute = new ArrayList<>();
    }

    /**
     * Returns the locomotive assigned to this route.
     *
     * @return the locomotive
     */
    public Locomotive getLocomotive() {
        return locomotive;
    }

    /**
     * Sets the locomotive assigned to this route.
     *
     * @param locomotive the locomotive to assign
     */
    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    /**
     * Returns the ordered list of points that make up the route.
     *
     * @return list of points of route
     */
    public List<PointOfRoute> getPointsOfRoute() {
        return pointsOfRoute;
    }

    /**
     * Sets the list of points for this route.
     *
     * @param pointsOfRoute the points to assign
     */
    public void setPointsOfRoute(List<PointOfRoute> pointsOfRoute) {
        this.pointsOfRoute = pointsOfRoute;
    }

    public boolean checkPointsOfRoute(List<PointOfRoute> pointsOfRoute) {
        if (pointsOfRoute == null || pointsOfRoute.isEmpty()) {
            return false;
        }

        for (PointOfRoute point : pointsOfRoute) {
            if (point == null
                    || !point.checkStation(point.getStation())
                    || !point.checkCargo(point.getCargoList())
                    || !point.checkCargoMode(point.getCargoMode())) {
                return false;
            }
        }

        return true;
    }

    public boolean checkLocomotive(Locomotive locomotive) {
        return locomotive != null && locomotive.getLocomotiveModel() != null && locomotive.getId() > 0;
    }

    /**
     * Returns a string representation of this route.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "Route:" +
                "\n  Locomotive: " + locomotive +
                "\n  Points of Route: " + pointsOfRoute;
    }

    /**
     * Finds possible starting stations for a maintenance route (Eulerian path)
     * based on the current railway graph.
     *
     * @param graph the railway graph containing stations and lines
     * @return a list of possible starting stations if an Eulerian path or circuit is possible;
     *         null if no such path exists
     */
    public List<Station> findEulerianPath(GraphC graph) {
        List<Station> stations = graph.getStations();
        List<Line> lines = graph.getLines();

        List<Station> oddDegreeStations = new ArrayList<>();

        for (Station current : stations) {
            int degree = 0;
            for (Line line : lines) {
                if (line.getStart().equals(current) || line.getEnd().equals(current)) {
                    degree++;
                }
            }
            if (degree % 2 != 0) {
                oddDegreeStations.add(current);
            }
        }

        if (oddDegreeStations.isEmpty()) {
            return stations;
        } else if (oddDegreeStations.size() == 2) {
            return oddDegreeStations;
        } else {
            return null;
        }
    }
}
