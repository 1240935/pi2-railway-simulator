package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the upgrading of stations within a selected scenario.
 *
 * <p>Allows selection of scenario, station, and building type to upgrade a station's building.
 * Also manages player budget verification and building creation.</p>
 */
public class UpgradeStationController {

    /**
     * The currently selected scenario.
     */
    private Scenario selectedScenario;

    /**
     * The currently selected station within the scenario.
     */
    private Station selectedStation;

    /**
     * The building to be assigned to the selected station.
     */
    private Building selectedBuilding;

    /**
     * The type of building to be created and assigned.
     */
    private BuildingType selectedBuildingType;

    /**
     * The player performing the upgrade operation.
     */
    private Player player;

    /**
     * Default constructor for UpgradeStationController.
     */
    public UpgradeStationController() {
        // Empty constructor
    }

    /**
     * Performs the upgrade operation by setting the selected building
     * on the currently selected station.
     */
    public void upgradeStation() {
        selectedStation.setBuilding(selectedBuilding);
    }

    /**
     * Retrieves the list of all available scenarios from the repository.
     *
     * @return list of scenarios
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Sets the selected scenario to be used in upgrade operations.
     *
     * @param scenario the Scenario to select
     */
    public void setScenario(Scenario scenario) {
        this.selectedScenario = scenario;
    }

    /**
     * Retrieves the list of stations available in the selected scenario.
     *
     * @return list of stations in the selected scenario, or empty list if no scenario selected
     */
    public List<Station> getAvailableStations() {
        if (selectedScenario == null) {
            return new ArrayList<>();
        }
        return selectedScenario.getStations();
    }

    /**
     * Sets the currently selected station within the selected scenario.
     *
     * @param station the Station to select
     */
    public void setStation(Station station) {
        this.selectedStation = station;
    }

    /**
     * Returns the string representations of all available scenarios.
     *
     * @return list of scenario names/descriptions
     */
    public List<String> getAvailableScenarioNames() {
        List<String> names = new ArrayList<>();
        for (Scenario scenario : getAvailableScenarios()) {
            names.add(scenario.toString());
        }
        return names;
    }

    /**
     * Returns the string representations of all available stations
     * within the selected scenario.
     *
     * @return list of station names/descriptions
     */
    public List<String> getAvailableStationNames() {
        List<String> names = new ArrayList<>();
        for (Station station : getAvailableStations()) {
            names.add(station.toString());
        }
        return names;
    }

    /**
     * Returns the string representations of all building types
     * available for the upgrade, filtered by scenario year and
     * building exclusivity.
     *
     * @return list of building type names/descriptions
     */
    public List<String> getAvailableBuildingNames() {
        List<String> names = new ArrayList<>();
        for (BuildingType buildingType : getAvailableBuildingTypes()) {
            names.add(buildingType.toString());
        }
        return names;
    }

    /**
     * Returns a list of building types that are available for construction
     * given the selected scenario year and existing buildings' exclusivity
     * constraints.
     *
     * @return list of available building types
     */
    public List<BuildingType> getAvailableBuildingTypes() {
        List<BuildingType> result = new ArrayList<>();

        if (selectedScenario == null || selectedStation == null) return result;

        int year = selectedScenario.getYear();
        List<Building> existing = Repositories.getInstance().getBuildingRepository().getBuildings();

        for (BuildingType candidate : BuildingType.values()) {
            if (candidate.getAvailabilityYear() > year) continue;

            boolean isExclusive = false;

            for (Building b : existing) {
                if (candidate.isMutuallyExclusiveWith(b.getBuildingType())) {
                    isExclusive = true;
                    break;
                }
            }

            if (!isExclusive) {
                result.add(candidate);
            }
        }

        return result;
    }

    /**
     * Sets the building type selected for the upgrade.
     *
     * @param type the BuildingType to select
     */
    public void setType(BuildingType type) {
        this.selectedBuildingType = type;
    }

    /**
     * Sets the selected scenario by its index in the available scenarios list.
     *
     * @param index index of the scenario to select
     */
    public void setScenarioByIndex(int index) {
        this.selectedScenario = getAvailableScenarios().get(index);
    }

    /**
     * Sets the selected station by its index in the available stations list.
     *
     * @param index index of the station to select
     */
    public void setStationByIndex(int index) {
        this.selectedStation = getAvailableStations().get(index);
    }

    /**
     * Sets the selected building type by its index in the available building types list.
     *
     * @param index index of the building type to select
     */
    public void setBuildingTypeByIndex(int index) {
        this.selectedBuildingType = getAvailableBuildingTypes().get(index);
    }

    /**
     * Retrieves the cost of the currently selected building type.
     *
     * @return cost of selected building type
     * @throws IllegalStateException if no building type is selected
     */
    public int getSelectedBuildingCost() {
        if (selectedBuildingType != null) {
            return selectedBuildingType.getCost();
        } else {
            throw new IllegalStateException("No building type selected.");
        }
    }

    /**
     * Retrieves the current player performing the upgrade operation.
     *
     * @return the Player instance
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Sets the player who is performing the upgrade operation.
     *
     * @param player the Player instance
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Sets the selected building instance to be assigned to the station.
     *
     * @param building the Building to assign
     */
    public void setBuilding(Building building) {
        this.selectedBuilding = building;
    }

    /**
     * Attempts to create a building of the selected type and assign it to the station.
     * Deducts the cost from the player's budget if affordable.
     * Adds the new building to the building repository.
     *
     * @return true if building was successfully created and assigned; false if player cannot afford
     */
    public boolean createAndSetBuilding() {
        if (!player.canAfford(selectedBuildingType.getCost())) {
            return false;
        }
        player.deductBudget(selectedBuildingType.getCost());
        this.selectedBuilding = new Building(selectedBuildingType);
        setBuilding(selectedBuilding);
        selectedStation.setBuilding(selectedBuilding);
        Repositories.getInstance().getBuildingRepository().addBuilding(selectedBuilding);
        return true;
    }

}
