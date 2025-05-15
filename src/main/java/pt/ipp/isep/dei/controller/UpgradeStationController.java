package pt.ipp.isep.dei.controller;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;
import java.util.List;

public class UpgradeStationController {
    public UpgradeStationController() {
        // Constructor logic
    }

    public List<Scenario> getAvailableScenarios() {
        // return available scenarios
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    public void setScenario(Scenario scenario) {
        // set selected scenario
    }

    public List<Station> getAvailableStations() {
        // return stations from selected scenario
        return Repositories.getInstance().getStationRepository().getStations();
    }

    public void setStation(Station station) {
        // set selected station
    }

    public List<BuildingType> getAvailableBuildingTypes() {
        // return available building types
        return new BuildingTypeRepository().getAvailableBuildingTypes();
    }

    public void setType(BuildingType type) {
        // set selected building type
    }

    public Building createBuilding(BuildingType type) {
        return new Building(type);
    }

    public void setBuilding(Building building) {
        // set building in station
    }
}