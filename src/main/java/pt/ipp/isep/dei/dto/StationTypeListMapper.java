package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.StationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class to convert a list of StationType domain objects
 * into a list of StationTypeDTO objects.
 */
public class StationTypeListMapper {

    /**
     * Converts a list of StationType objects to a list of StationTypeDTOs.
     *
     * @param types the list of StationType domain objects
     * @return a list of corresponding StationTypeDTO objects
     */
    public List<StationTypeDTO> toDTOList(List<StationType> types) {
        List<StationTypeDTO> result = new ArrayList<>();
        for (StationType type : types) {
            StationTypeDTO dto = new StationTypeDTO();
            dto.setName(type.name());
            dto.setRadius(type.getRadius());
            dto.setCost(type.getCost());
            result.add(dto);
        }
        return result;
    }
}
