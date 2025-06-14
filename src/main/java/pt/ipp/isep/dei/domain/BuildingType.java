package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents different types of buildings that can be constructed at a station.
 * Each building has a cost, an availability year, and may belong to an upgrade group.
 * Buildings in the same upgrade group are mutually exclusive.
 */
public enum BuildingType implements Serializable {
    TELEGRAPH(100, 1850, "COMMUNICATION"),
    TELEPHONE(150, 1910, "COMMUNICATION"),

    SMALL_CAFE(200, 1850, "CAFE"),
    LARGE_CAFE(300, 1900, "CAFE"),

    SMALL_RESTAURANT(300, 1855, "RESTAURANT"),
    LARGE_RESTAURANT(400, 1910, "RESTAURANT"),

    CUSTOMS(500, 1880, null),
    POST_OFFICE(250, 1870, null),

    SMALL_HOTEL(400, 1890, "HOTEL"),
    LARGE_HOTEL(600, 1920, "HOTEL"),

    SILO(350, 1850, null),
    LIQUID_STORAGE(450, 1900, null);

    private final int cost;
    private final int availabilityYear;
    private final String upgradeGroup;

    /**
     * Constructs a BuildingType with the given cost, availability year, and upgrade group.
     *
     * @param cost the cost to build this building
     * @param availabilityYear the year from which this building type is available
     * @param upgradeGroup the upgrade group this building belongs to (null if none)
     */
    BuildingType(int cost, int availabilityYear, String upgradeGroup) {
        this.cost = cost;
        this.availabilityYear = availabilityYear;
        this.upgradeGroup = upgradeGroup;
    }

    /**
     * Returns the cost of this building type.
     *
     * @return the building cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Returns the year from which this building type is available.
     *
     * @return the availability year
     */
    public int getAvailabilityYear() {
        return availabilityYear;
    }

    /**
     * Returns the upgrade group this building belongs to, or null if none.
     * Buildings in the same upgrade group are mutually exclusive.
     *
     * @return the upgrade group string or null
     */
    public String getUpgradeGroup() {
        return upgradeGroup;
    }

    /**
     * Checks if this building type is mutually exclusive with another,
     * meaning both belong to the same upgrade group.
     *
     * @param other another building type to compare
     * @return true if both belong to the same upgrade group, false otherwise
     */
    public boolean isMutuallyExclusiveWith(BuildingType other) {
        if (this.upgradeGroup == null || other.upgradeGroup == null) return false;
        return this.upgradeGroup.equals(other.upgradeGroup);
    }

    /**
     * Checks if this building type is a newer upgrade from another building type,
     * meaning both belong to the same upgrade group and this building
     * has a later availability year.
     *
     * @param other another building type to compare
     * @return true if this building is a newer upgrade, false otherwise
     */
    public boolean isUpgradeFrom(BuildingType other) {
        return isMutuallyExclusiveWith(other) && this.availabilityYear > other.availabilityYear;
    }

    /**
     * Returns all building types that are mutually exclusive with this one,
     * i.e., all other building types in the same upgrade group.
     *
     * @return an array of mutually exclusive building types
     */
    public BuildingType[] getMutuallyExclusiveTypes() {
        if (this.upgradeGroup == null) return new BuildingType[0];

        BuildingType[] all = BuildingType.values();
        int count = 0;

        // Count mutually exclusive types
        for (BuildingType type : all) {
            if (type != this && this.upgradeGroup.equals(type.upgradeGroup)) {
                count++;
            }
        }

        BuildingType[] result = new BuildingType[count];
        int i = 0;

        // Collect mutually exclusive types
        for (BuildingType type : all) {
            if (type != this && this.upgradeGroup.equals(type.upgradeGroup)) {
                result[i++] = type;
            }
        }

        return result;
    }

    /**
     * Returns a string representation of this building type
     * including its name and cost.
     *
     * @return a string describing this building type
     */
    @Override
    public String toString() {
        return name() + " (Cost: " + cost + ")";
    }
}
