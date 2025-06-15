package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Cargo;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link Cargo} domain objects
 * into a list of {@link CargoDTO} data transfer objects.
 */
public class CargoListMapper {

    /**
     * Converts a list of {@link Cargo} objects to a list of {@link CargoDTO} objects.
     * <p>
     * Each {@code Cargo} object is mapped by copying its quantity and resource type
     * into a new {@code CargoDTO} object.
     * </p>
     *
     * @param cargos the list of {@code Cargo} domain objects to convert
     * @return a list of corresponding {@code CargoDTO} objects
     */
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
