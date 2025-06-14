package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.StationType;

import java.util.ArrayList;
import java.util.List;

public class StationTypeListMapper {
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
