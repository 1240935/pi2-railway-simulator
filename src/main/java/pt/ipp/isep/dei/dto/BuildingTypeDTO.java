package pt.ipp.isep.dei.dto;

public class BuildingTypeDTO {
    private int cost;
    private int availabilityYear;
    private String upgradeGroup;
    private String name;

    public BuildingTypeDTO(int cost, int availabilityYear, String upgradeGroup, String name) {
        this.cost = cost;
        this.availabilityYear = availabilityYear;
        this.upgradeGroup = upgradeGroup;
        this.name = name;
    }

    public BuildingTypeDTO(){}

    public int getCost() {
        return cost;
    }

    public int getAvailabilityYear() {
        return availabilityYear;
    }

    public String getUpgradeGroup() {
        return upgradeGroup;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setAvailabilityYear(int availabilityYear) {
        this.availabilityYear = availabilityYear;
    }

    public void setUpgradeGroup(String upgradeGroup) {
        this.upgradeGroup = upgradeGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
