package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.CargoMode;

import java.util.ArrayList;
import java.util.List;

public class CargoModeListMapper {
    public List<CargoModeDTO> toDTOList(List<CargoMode> modes) {
        List<CargoModeDTO> result = new ArrayList<>();
        for (CargoMode mode : modes) {
            CargoModeDTO dto = new CargoModeDTO();
            dto.setName(mode.name());
            result.add(dto);
        }
        return result;
    }
}
