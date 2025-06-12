package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Cargo;
import pt.ipp.isep.dei.domain.CargoMode;
import pt.ipp.isep.dei.domain.PointOfRoute;
import pt.ipp.isep.dei.domain.Station;

import java.util.ArrayList;
import java.util.List;

public class PointOfRouteRepository {
    private final ArrayList<PointOfRoute> pointsOfRoute = new ArrayList<>();

    public ArrayList<PointOfRoute> getPointsOfRoute() {
        return pointsOfRoute;
    }

    public void addPointOfRoute(PointOfRoute pointOfRoute) {
        pointsOfRoute.add(pointOfRoute);
    }

    public static PointOfRoute createPointOfRoute(Station station, List<Cargo> cargo, CargoMode cargoMode){
        PointOfRoute pointOfRoute = new PointOfRoute(station, cargo, cargoMode);
        if (pointOfRoute.checkStation(station) && pointOfRoute.checkCargo(cargo) && pointOfRoute.checkCargoMode(cargoMode)) {
            System.out.println("Point of Route created successfully!");
            return pointOfRoute;
        } else {
            System.out.println("Invalid point of route parameters.");
            return null;
        }
    }
}
