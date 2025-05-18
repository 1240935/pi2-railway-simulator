package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;

public class UpgradeStationController {

    private Scenario selectedScenario;
    private Station selectedStation;
    private Building selectedBuilding;
    private BuildingType selectedBuildingType;
    private Player player;

    public UpgradeStationController() {
        // Empty constructor
    }

    public void upgradeStation() {
        selectedStation.setBuilding(selectedBuilding);
    }

    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    public void setScenario(Scenario scenario) {
        this.selectedScenario = scenario;
    }

    public List<Station> getAvailableStations() {
        return selectedScenario.getStations();
    }

    public void setStation(Station station) {
        this.selectedStation = station;
    }

    public List<String> getAvailableScenarioNames() {
        List<String> names = new ArrayList<>();
        for (Scenario scenario : getAvailableScenarios()) {
            names.add(scenario.toString());
        }
        return names;
    }

    public List<String> getAvailableStationNames() {
        List<String> names = new ArrayList<>();
        for (Station station : getAvailableStations()) {
            names.add(station.toString());
        }
        return names;
    }

    public List<String> getAvailableBuildingNames() {
        List<String> names = new ArrayList<>();
        for (BuildingType buildingType : getAvailableBuildingTypes()) {
            names.add(buildingType.toString());
        }
        return names;
    }

    /**
     * Returns only building types available in the scenario's year
     * and not mutually exclusive with any existing buildings in the station.
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

    public void setType(BuildingType type) {
        this.selectedBuildingType = type;
    }

    public void setScenarioByIndex(int index) {
        this.selectedScenario = getAvailableScenarios().get(index);
    }

    public void setStationByIndex(int index) {
        this.selectedStation = getAvailableStations().get(index);
    }

    public void setBuildingTypeByIndex(int index) {
        this.selectedBuildingType = getAvailableBuildingTypes().get(index);
    }

    public int getSelectedBuildingCost() {
        if (selectedBuildingType != null) {
            return selectedBuildingType.getCost();
        } else {
            throw new IllegalStateException("No building type selected.");
        }
    }

    /**
     * Returns the current player associated with the build station operation.
     *
     * @return Player instance.
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Sets the player who is building the station.
     *
     * @param player Player instance.
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    public void setBuilding(Building building) {
        this.selectedBuilding = building;
    }

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
