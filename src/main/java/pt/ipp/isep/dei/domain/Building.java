package pt.ipp.isep.dei.domain;

public class Building {
    private BuildingType buildingType;

    public Building(BuildingType buildingType){
        this.buildingType = buildingType;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }
}
