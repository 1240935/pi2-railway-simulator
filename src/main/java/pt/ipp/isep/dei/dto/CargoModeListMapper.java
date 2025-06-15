package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.CargoMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link CargoMode} domain objects
 * into a list of {@link CargoModeDTO} data transfer objects.
 */
public class CargoModeListMapper {

    /**
     * Converts a list of {@link CargoMode} objects to a list of {@link CargoModeDTO} objects.
     * <p>
     * Each {@code CargoMode} is mapped to a {@code CargoModeDTO} by copying its name.
     * </p>
     *
     * @param modes the list of {@code CargoMode} domain objects to convert
     * @return a list of corresponding {@code CargoModeDTO} objects
     */
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
