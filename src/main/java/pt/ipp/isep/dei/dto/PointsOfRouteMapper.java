package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.PointOfRoute;
import pt.ipp.isep.dei.repository.PointOfRouteRepository;

/**
 * Mapper class responsible for converting a {@link PointOfRouteDTO}
 * into a {@link PointOfRoute} domain object.
 */
public class PointsOfRouteMapper {

    /**
     * Converts a {@link PointOfRouteDTO} to a {@link PointOfRoute} domain object.
     *
     * This method delegates the creation of the {@code PointOfRoute} to the {@link PointOfRouteRepository}.
     *
     * @param pointOfRouteDTO the {@code PointOfRouteDTO} to convert
     * @return the created {@code PointOfRoute} domain object
     */
    public PointOfRoute toPointOfRoute(PointOfRouteDTO pointOfRouteDTO) {
        return PointOfRouteRepository.createPointOfRoute(
                pointOfRouteDTO.getStation(),
                pointOfRouteDTO.getCargo(),
                pointOfRouteDTO.getCargoMode()
        );
    }
}
