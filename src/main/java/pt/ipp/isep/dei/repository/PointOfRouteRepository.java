package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Cargo;
import pt.ipp.isep.dei.domain.CargoMode;
import pt.ipp.isep.dei.domain.PointOfRoute;
import pt.ipp.isep.dei.domain.Station;

import java.util.ArrayList;
import java.util.List;

public class PointOfRouteRepository {
    private final ArrayList<PointOfRoute> pointsOfRoute = new ArrayList<>();

    /**
     * Gets the list of points of route stored in the repository.
     * @return list of points of route
     */
    public ArrayList<PointOfRoute> getPointsOfRoute() {
        return pointsOfRoute;
    }

    /**
     * Adds a new point of route to the repository.
     * @param pointOfRoute point of route to be added
     */
    public void addPointOfRoute(PointOfRoute pointOfRoute) {
        pointsOfRoute.add(pointOfRoute);
    }

    /**
     * Creates a new point of route validating its parameters.
     * @param station station of the point of route
     * @param cargo list of cargos associated with the point
     * @param cargoMode cargo mode used at the point
     * @return created point of route if valid; otherwise, returns null
     */
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
