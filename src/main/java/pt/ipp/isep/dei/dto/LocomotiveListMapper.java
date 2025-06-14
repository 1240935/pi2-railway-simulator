package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.ArrayList;
import java.util.List;

public class LocomotiveListMapper {
    public List<LocomotiveDTO> toDTOList(List<Locomotive> locomotives) {
        List<LocomotiveDTO> result = new ArrayList<>();
        for (Locomotive locomotive : locomotives) {
            LocomotiveDTO dto = new LocomotiveDTO();
            dto.setLocomotiveModel(locomotive.getLocomotiveModel());
            result.add(dto);
        }
        return result;
    }
}
