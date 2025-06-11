package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.repository.StationRepository;

public class StationMapper {
    public Station toStation(StationDTO stationDTO){
        return StationRepository.createStation(
                stationDTO.getPosition(), stationDTO.getName(),
                stationDTO.getCenter(), stationDTO.getStationType(),
                stationDTO.getDemands(),stationDTO.getSupplies(),
                stationDTO.getMap(),stationDTO.getScenario()
        );
    }

}
