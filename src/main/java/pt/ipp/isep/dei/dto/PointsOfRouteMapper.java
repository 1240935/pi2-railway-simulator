package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.PointOfRoute;
import pt.ipp.isep.dei.repository.PointOfRouteRepository;

public class PointsOfRouteMapper {
    public PointOfRoute toPointOfRoute(PointOfRouteDTO pointOfRouteDTO) {
        return PointOfRouteRepository.createPointOfRoute(
                pointOfRouteDTO.getStation(),
                pointOfRouteDTO.getCargo(),
                pointOfRouteDTO.getCargoMode()
        );
    }
}
