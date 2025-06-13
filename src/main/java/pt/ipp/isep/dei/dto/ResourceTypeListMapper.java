package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class ResourceTypeListMapper {
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
