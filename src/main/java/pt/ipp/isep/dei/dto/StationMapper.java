package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.repository.StationRepository;

/**
 * Mapper class to convert StationDTO to Station domain object.
 */
public class StationMapper {

    /**
     * Converts a StationDTO to a Station domain object.
     *
     * @param stationDTO the StationDTO to convert
     * @return a Station domain object created from the DTO data
     */
    public Station toStation(StationDTO stationDTO) {
        return StationRepository.createStation(
                stationDTO.getPosition(),
                stationDTO.getName(),
                stationDTO.getCenter(),
                stationDTO.getStationType(),
                stationDTO.getDemands(),
                stationDTO.getSupplies(),
                stationDTO.getMap(),
                stationDTO.getScenario()
        );
    }
}
