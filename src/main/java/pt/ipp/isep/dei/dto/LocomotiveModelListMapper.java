package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.LocomotiveModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link LocomotiveModel}
 * domain objects into a list of {@link LocomotiveModelDTO} objects.
 */
public class LocomotiveModelListMapper {

    /**
     * Converts a list of {@link LocomotiveModel} objects into a list of
     * {@link LocomotiveModelDTO} objects.
     *
     * @param models the list of locomotive models to convert
     * @return a list of corresponding locomotive model DTOs
     */
    public List<LocomotiveModelDTO> toDTO(List<LocomotiveModel> models) {
        List<LocomotiveModelDTO> result = new ArrayList<>();
        for (LocomotiveModel model : models) {
            LocomotiveModelDTO dto = new LocomotiveModelDTO();
            dto.setCost(model.getCost());
            dto.setAcceleration(model.getAcceleration());
            dto.setLocomotiveType(model.getLocomotiveType());
            dto.setPower(model.getPower());
            dto.setMaintenancePYear(model.getMaintenancePYear());
            dto.setTopSpeed(model.getTopSpeed());
            dto.setFuelCost(model.getFuelCost());
            dto.setOperationStartYear(model.getOperationStartYear());
            dto.setName(model.name());
            result.add(dto);
        }
        return result;
    }
}
