package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Route;
import pt.ipp.isep.dei.repository.RouteRepository;

public class RouteMapper {
    public Route toRoute(RouteDTO routeDTO) {
        return RouteRepository.createRoute(
                routeDTO.getLocomotive(),
                routeDTO.getPointsOfRoute()
        );
    }
}
