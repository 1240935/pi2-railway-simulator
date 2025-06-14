package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents a Building associated with a Station in the railway system.
 * <p>
 * Each building has a type that describes its purpose.
 */
public class Building implements Serializable {

    /**
     * The type of the building.
     */
    private BuildingType buildingType;

    private double revenueBonus;

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

    public double getRevenueBonus() {
        if (buildingType == BuildingType.LARGE_HOTEL || buildingType == BuildingType.LARGE_CAFE || buildingType == BuildingType.LARGE_RESTAURANT){
            revenueBonus = 10;
        } else {
            revenueBonus = 5;
        }
        return revenueBonus;
    }


    /**
     * Returns a string representation of the building.
     *
     * @return a string in the format: Building : 'type'
     */
    @Override
    public String toString() {
        return buildingType.name();
    }

}
