package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.BuildingType;

/**
 * Data Transfer Object (DTO) representing a Building.
 * This class is used to transfer building data between processes.
 */
public class BuildingDTO {

    /**
     * The type of the building.
     */
    private BuildingType buildingType;

    /**
     * Constructs a {@code BuildingDTO} with a specified {@code BuildingType}.
     *
     * @param buildingType the type of the building
     */
    public BuildingDTO(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    /**
     * Constructs a {@code BuildingDTO} with no initial building type.
     */
    public BuildingDTO() {}

    /**
     * Returns the type of the building.
     *
     * @return the building type
     */
    public BuildingType getBuildingType() {
        return buildingType;
    }

    /**
     * Sets the type of the building.
     *
     * @param buildingType the building type to set
     */
    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }
}
