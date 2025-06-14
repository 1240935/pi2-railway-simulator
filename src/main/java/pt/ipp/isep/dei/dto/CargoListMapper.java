package pt.ipp.isep.dei.dto;


import pt.ipp.isep.dei.domain.Cargo;

import java.util.ArrayList;
import java.util.List;

public class CargoListMapper {
    public List<CargoDTO> toDTOList(List<Cargo> cargos) {
        List<CargoDTO> result = new ArrayList<>();
        for (Cargo cargo : cargos) {
            CargoDTO dto = new CargoDTO();
            dto.setQuantity(cargo.getQuantity());
            dto.setType(cargo.getType());
            result.add(dto);
        }
        return result;
    }
}
