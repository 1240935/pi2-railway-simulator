package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Route;
import pt.ipp.isep.dei.repository.RouteRepository;

/**
 * Mapper class responsible for converting a {@link RouteDTO}
 * into a {@link Route} domain object.
 */
public class RouteMapper {

    /**
     * Converts a {@link RouteDTO} to a {@link Route} domain object.
     *
     * This method delegates the creation of the {@code Route} to the {@link RouteRepository}.
     *
     * @param routeDTO the {@code RouteDTO} to convert
     * @return the created {@code Route} domain object
     */
    public Route toRoute(RouteDTO routeDTO) {
        return RouteRepository.createRoute(
                routeDTO.getLocomotive(),
                routeDTO.getPointsOfRoute()
        );
    }
}
