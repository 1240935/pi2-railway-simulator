package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.IndustryType;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link IndustryType}
 * domain objects into a list of {@link IndustryTypeDTO} objects.
 */
public class IndustryTypeListMapper {

    /**
     * Converts a list of {@link IndustryType} objects to a list of {@link IndustryTypeDTO} objects.
     *
     * @param types the list of {@code IndustryType} domain objects to convert
     * @return a list of corresponding {@code IndustryTypeDTO} objects
     */
    public List<IndustryTypeDTO> toDTOList(List<IndustryType> types) {
        List<IndustryTypeDTO> result = new ArrayList<>();
        for (IndustryType type : types) {
            IndustryTypeDTO dto = new IndustryTypeDTO();
            dto.setName(type.name());
            dto.setImportation(type.getImportation());
            dto.setExportation(type.getExportation());
            result.add(dto);
        }
        return result;
    }
}
