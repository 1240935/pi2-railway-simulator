package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.Station;

import java.util.ArrayList;
import java.util.List;

public class StationListMapper {
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
            result.add(dto);
        }
        return result;
    }
}
