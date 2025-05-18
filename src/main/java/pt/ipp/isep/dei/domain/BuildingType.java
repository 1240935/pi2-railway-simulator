package pt.ipp.isep.dei.domain;

/**
 * Represents different types of buildings that can be constructed at a station.
 * Each building has a cost, an availability year, and may belong to an upgrade group.
 * Buildings in the same upgrade group are mutually exclusive.
 */
public enum BuildingType {
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

    BuildingType(int cost, int availabilityYear, String upgradeGroup) {
        this.cost = cost;
        this.availabilityYear = availabilityYear;
        this.upgradeGroup = upgradeGroup;
    }

    public int getCost() {
        return cost;
    }

    public int getAvailabilityYear() {
        return availabilityYear;
    }

    public String getUpgradeGroup() {
        return upgradeGroup;
    }

    /**
     * Returns true if this building is mutually exclusive with another,
     * based on matching upgrade groups.
     */
    public boolean isMutuallyExclusiveWith(BuildingType other) {
        if (this.upgradeGroup == null || other.upgradeGroup == null) return false;
        return this.upgradeGroup.equals(other.upgradeGroup);
    }

    /**
     * Returns true if this building is a newer upgrade from another
     * in the same upgrade group.
     */
    public boolean isUpgradeFrom(BuildingType other) {
        return isMutuallyExclusiveWith(other) && this.availabilityYear > other.availabilityYear;
    }

    /**
     * Returns an array of building types that are mutually exclusive with this one.
     */
    public BuildingType[] getMutuallyExclusiveTypes() {
        if (this.upgradeGroup == null) return new BuildingType[0];

        BuildingType[] all = BuildingType.values();
        int count = 0;

        // First count how many are mutually exclusive
        for (BuildingType type : all) {
            if (type != this && this.upgradeGroup.equals(type.upgradeGroup)) {
                count++;
            }
        }

        BuildingType[] result = new BuildingType[count];
        int i = 0;

        for (BuildingType type : all) {
            if (type != this && this.upgradeGroup.equals(type.upgradeGroup)) {
                result[i++] = type;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return name() + " (Cost: " + cost + ")";
    }
}
