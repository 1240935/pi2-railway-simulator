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
     * Obtém a lista de pontos de rota armazenados no repositório.
     * @return lista de pontos de rota
     */
    public ArrayList<PointOfRoute> getPointsOfRoute() {
        return pointsOfRoute;
    }

    /**
     * Adiciona um novo ponto de rota ao repositório.
     * @param pointOfRoute ponto de rota a ser adicionado
     */
    public void addPointOfRoute(PointOfRoute pointOfRoute) {
        pointsOfRoute.add(pointOfRoute);
    }

    /**
     * Cria um novo ponto de rota validando seus parâmetros.
     * @param station estação do ponto de rota
     * @param cargo lista de cargas associadas ao ponto
     * @param cargoMode modo de carga utilizado no ponto
     * @return ponto de rota criado caso seja válido; caso contrário, retorna null
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
