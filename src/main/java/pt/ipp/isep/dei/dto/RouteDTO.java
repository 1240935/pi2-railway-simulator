package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.PointOfRoute;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a Route,
 * which consists of a list of points along the route and the locomotive used.
 */
public class RouteDTO {
    private List<PointOfRoute> pointsOfRoute;
    private Locomotive locomotive;

    /**
     * Default constructor.
     */
    public RouteDTO(){}

    /**
     * Constructs a RouteDTO with the specified points of route and locomotive.
     *
     * @param pointsOfRoute the list of points of the route
     * @param locomotive the locomotive associated with the route
     */
    public RouteDTO(List<PointOfRoute> pointsOfRoute, Locomotive locomotive){
        this.pointsOfRoute = pointsOfRoute;
        this.locomotive = locomotive;
    }

    /**
     * Returns the list of points in the route.
     *
     * @return list of {@link PointOfRoute}
     */
    public List<PointOfRoute> getPointsOfRoute() {
        return pointsOfRoute;
    }

    /**
     * Returns the locomotive assigned to the route.
     *
     * @return the {@link Locomotive}
     */
    public Locomotive getLocomotive() {
        return locomotive;
    }

    /**
     * Sets the locomotive for the route.
     *
     * @param locomotive the {@link Locomotive} to set
     */
    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    /**
     * Sets the list of points for the route.
     *
     * @param pointsOfRoute list of {@link PointOfRoute} to set
     */
    public void setPointsOfRoute(List<PointOfRoute> pointsOfRoute) {
        this.pointsOfRoute = pointsOfRoute;
    }
}
