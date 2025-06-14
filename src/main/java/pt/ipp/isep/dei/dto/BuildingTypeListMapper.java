package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.BuildingType;

import java.util.ArrayList;
import java.util.List;

public class BuildingTypeListMapper {
    public List<BuildingTypeDTO> toDTOList(List<BuildingType> types) {
        List<BuildingTypeDTO> result = new ArrayList<>();
        for (BuildingType type : types) {
            BuildingTypeDTO dto = new BuildingTypeDTO();
            dto.setCost(type.getCost());
            dto.setAvailabilityYear(type.getAvailabilityYear());
            dto.setUpgradeGroup(type.getUpgradeGroup());
            dto.setName(type.name());
            result.add(dto);
        }
        return result;
    }
}
