package pt.ipp.isep.dei.dto;

/**
 * Data Transfer Object (DTO) representing the type of a building.
 * Contains information such as cost, availability year, upgrade group, and name.
 */
public class BuildingTypeDTO {

    /**
     * The cost associated with the building type.
     */
    private int cost;

    /**
     * The year when the building type becomes available.
     */
    private int availabilityYear;

    /**
     * The upgrade group the building type belongs to.
     */
    private String upgradeGroup;

    /**
     * The name of the building type.
     */
    private String name;

    /**
     * Constructs a {@code BuildingTypeDTO} with specified attributes.
     *
     * @param cost the cost of the building type
     * @param availabilityYear the year the building becomes available
     * @param upgradeGroup the upgrade group of the building
     * @param name the name of the building type
     */
    public BuildingTypeDTO(int cost, int availabilityYear, String upgradeGroup, String name) {
        this.cost = cost;
        this.availabilityYear = availabilityYear;
        this.upgradeGroup = upgradeGroup;
        this.name = name;
    }

    /**
     * Default constructor for {@code BuildingTypeDTO}.
     */
    public BuildingTypeDTO() {}

    /**
     * Gets the cost of the building type.
     *
     * @return the building type cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of the building type.
     *
     * @param cost the new cost to set
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets the availability year of the building type.
     *
     * @return the year the building becomes available
     */
    public int getAvailabilityYear() {
        return availabilityYear;
    }

    /**
     * Sets the availability year of the building type.
     *
     * @param availabilityYear the new availability year to set
     */
    public void setAvailabilityYear(int availabilityYear) {
        this.availabilityYear = availabilityYear;
    }

    /**
     * Gets the upgrade group of the building type.
     *
     * @return the upgrade group
     */
    public String getUpgradeGroup() {
        return upgradeGroup;
    }

    /**
     * Sets the upgrade group of the building type.
     *
     * @param upgradeGroup the new upgrade group to set
     */
    public void setUpgradeGroup(String upgradeGroup) {
        this.upgradeGroup = upgradeGroup;
    }

    /**
     * Gets the name of the building type.
     *
     * @return the name of the building type
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the building type.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the building type,
     * consisting of its name and cost.
     *
     * @return a string in the format "name (cost)"
     */
    @Override
    public String toString() {
        return name + " (" + cost + ")";
    }
}
