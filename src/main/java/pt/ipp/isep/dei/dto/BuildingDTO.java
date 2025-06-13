package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.BuildingType;

public class BuildingDTO {
    private BuildingType buildingType;

    public BuildingDTO(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public BuildingDTO(){}

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }
}
