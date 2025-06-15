package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class to convert a list of Station domain objects into a list of StationDTOs.
 */
public class StationListMapper {

    /**
     * Converts a list of Station domain objects to a list of StationDTOs.
     *
     * @param stations the list of Station objects to convert
     * @return a list of StationDTOs representing the input stations
     */
    public List<StationDTO> toDTOList(List<Station> stations) {
        List<StationDTO> result = new ArrayList<>();
        for (Station station : stations) {
            StationDTO dto = new StationDTO();
            dto.setName(station.getName());
            dto.setSupplies(station.getSupplies());
            dto.setDemands(station.getDemands());
            dto.setStationType(station.getType());
            dto.setCenter(station.getCenter());
            dto.setPosition(station.getPosition());
            // Note: scenario and map are not set here, consider if needed
            result.add(dto);
        }
        return result;
    }
}
