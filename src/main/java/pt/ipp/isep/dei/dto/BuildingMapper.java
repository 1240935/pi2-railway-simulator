package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Building;
import pt.ipp.isep.dei.repository.BuildingRepository;

/**
 * Mapper class responsible for converting {@link BuildingDTO} objects
 * into {@link Building} domain objects.
 */
public class BuildingMapper {

    /**
     * Converts a {@link BuildingDTO} to a {@link Building} object.
     * <p>
     * Uses {@link BuildingRepository} to create a new {@code Building}
     * instance based on the building type provided in the DTO.
     * </p>
     *
     * @param dto the {@code BuildingDTO} to convert
     * @return a new {@code Building} object corresponding to the DTO
     */
    public Building toBuilding(BuildingDTO dto) {
        return BuildingRepository.createBuilding(dto.getBuildingType());
    }
}
