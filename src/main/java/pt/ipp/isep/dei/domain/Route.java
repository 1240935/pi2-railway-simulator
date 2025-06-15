package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Represents a railway route assigned to a locomotive,
 * consisting of an ordered list of points, where each point
 * includes a station and associated cargo details.
 */
public class Route implements Serializable {

    /**
     * The locomotive assigned to this route.
     */
    private Locomotive locomotive;

    /**
     * The ordered list of points that compose the route.
     */
    private List<PointOfRoute> pointsOfRoute;

    /**
     * Constructs a Route with a specified locomotive and points.
     *
     * @param locomotive the locomotive assigned to the route
     * @param pointsOfRoute the ordered points of the route
     */
    public Route(Locomotive locomotive, List<PointOfRoute> pointsOfRoute) {
        this.locomotive = locomotive;
        this.pointsOfRoute = pointsOfRoute;
    }

    /**
     * Default constructor initializing an empty list of points.
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
     * Returns the ordered list of points of this route.
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

    /**
     * Validates the given list of points of route.
     * Checks for null, emptiness, and internal validity of each point's station, cargo, and cargo mode.
     *
     * @param pointsOfRoute the list of points to validate
     * @return true if all points are valid, false otherwise
     */
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

    /**
     * Validates the locomotive object.
     * Checks if the locomotive is non-null, has a non-null model, and has a positive id.
     *
     * @param locomotive the locomotive to validate
     * @return true if locomotive is valid, false otherwise
     */
    public boolean checkLocomotive(Locomotive locomotive) {
        return locomotive != null && locomotive.getLocomotiveModel() != null && locomotive.getId() > 0;
    }

    /**
     * Returns a string representation of this route including the locomotive and points.
     *
     * @return string representation of the route
     */
    @Override
    public String toString() {
        return "Route:" +
                "\n  Locomotive: " + locomotive +
                "\n  Points of Route: " + pointsOfRoute;
    }

    /**
     * Determines possible starting stations for an Eulerian path or circuit
     * based on the current railway graph.
     * <p>
     * If all stations have even degree, returns all stations (Eulerian circuit possible).
     * If exactly two stations have an odd degree, returns those two stations (Eulerian path possible).
     * Otherwise, returns null (no Eulerian path or circuit).
     * </p>
     *
     * @param graph the railway graph containing stations and lines
     * @return list of possible starting stations, or null if none
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
