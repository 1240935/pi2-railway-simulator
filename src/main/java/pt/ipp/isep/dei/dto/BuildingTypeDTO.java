package pt.ipp.isep.dei.dto;

public class BuildingTypeDTO {
    private int cost;
    private int availabilityYear;
    private String upgradeGroup;

    public BuildingTypeDTO(int cost, int availabilityYear, String upgradeGroup) {
        this.cost = cost;
        this.availabilityYear = availabilityYear;
        this.upgradeGroup = upgradeGroup;
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
}
