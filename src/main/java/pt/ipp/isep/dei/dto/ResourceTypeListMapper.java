package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.ResourceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link ResourceType} domain objects
 * into a list of {@link ResourceTypeDTO} data transfer objects.
 */
public class ResourceTypeListMapper {

    /**
     * Converts a list of {@link ResourceType} objects into a list of {@link ResourceTypeDTO} objects.
     *
     * @param types the list of {@code ResourceType} to convert
     * @return a list of {@code ResourceTypeDTO} representing the given resource types
     */
    public List<ResourceTypeDTO> toDTOList(List<ResourceType> types) {
        List<ResourceTypeDTO> result = new ArrayList<>();
        for (ResourceType type : types) {
            ResourceTypeDTO dto = new ResourceTypeDTO();
            dto.setName(type.name());
            dto.setValue(type.getValue());
            result.add(dto);
        }
        return result;
    }
}
