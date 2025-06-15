package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Locomotive;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link Locomotive}
 * domain objects into a list of {@link LocomotiveDTO} objects.
 */
public class LocomotiveListMapper {

    /**
     * Converts a list of {@link Locomotive} objects to a list of {@link LocomotiveDTO} objects.
     *
     * @param locomotives the list of {@code Locomotive} domain objects to convert
     * @return a list of corresponding {@code LocomotiveDTO} objects
     */
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
