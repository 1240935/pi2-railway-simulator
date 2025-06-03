package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.IndustryType;

import java.util.ArrayList;
import java.util.List;

public class IndustryTypeListMapper {
    public List<IndustryTypeDTO> toDTOList(List<IndustryType> types) {
        List<IndustryTypeDTO> result = new ArrayList<IndustryTypeDTO>();
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
