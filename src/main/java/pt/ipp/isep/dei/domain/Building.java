package pt.ipp.isep.dei.domain;

/**
 * Represents a Building associated with a Station in the railway system.
 * <p>
 * Each building has a type that describes its purpose.
 */
public class Building {

    /**
     * The type of the building.
     */
    private BuildingType buildingType;

    /**
     * Constructs a Building with the specified building type.
     *
     * @param buildingType the type of the building
     */
    public Building(BuildingType buildingType){
        this.buildingType = buildingType;
    }

    /**
     * Returns the building type.
     *
     * @return the building type
     */
    public BuildingType getBuildingType() {
        return buildingType;
    }

    /**
     * Sets the building type.
     *
     * @param buildingType the new building type to set
     */
    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public static boolean checkType(BuildingType type) {
        return type != null;
    }

    /**
     * Returns a string representation of the building.
     *
     * @return a string in the format: Building : 'type'
     */
    @Override
    public String toString() { return "Building : '" + buildingType + "'"; }
}
