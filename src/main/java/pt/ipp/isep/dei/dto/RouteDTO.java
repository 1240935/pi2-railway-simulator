package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.PointOfRoute;

import java.util.List;

public class RouteDTO {
    private List<PointOfRoute> pointsOfRoute;
    private Locomotive locomotive;

    public RouteDTO(){}

     public RouteDTO(List<PointOfRoute> pointsOfRoute, Locomotive locomotive){
        this.pointsOfRoute = pointsOfRoute;
        this.locomotive = locomotive;
     }

    public List<PointOfRoute> getPointsOfRoute() {
        return pointsOfRoute;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    public void setPointsOfRoute(List<PointOfRoute> pointsOfRoute) {
        this.pointsOfRoute = pointsOfRoute;
    }
}
