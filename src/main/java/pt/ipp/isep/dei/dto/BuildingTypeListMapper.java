package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.BuildingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link BuildingType} domain objects
 * into a list of {@link BuildingTypeDTO} data transfer objects.
 */
public class BuildingTypeListMapper {

    /**
     * Converts a list of {@link BuildingType} objects to a list of {@link BuildingTypeDTO} objects.
     * <p>
     * Each {@code BuildingType} is mapped to a corresponding {@code BuildingTypeDTO}
     * by copying its attributes.
     * </p>
     *
     * @param types the list of {@code BuildingType} objects to convert
     * @return a list of {@code BuildingTypeDTO} objects
     */
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
