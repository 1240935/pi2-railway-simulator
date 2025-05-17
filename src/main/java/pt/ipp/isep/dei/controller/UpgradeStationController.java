package pt.ipp.isep.dei.controller;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UpgradeStationController {

    private Scenario selectedScenario;
    private Station selectedStation;
    private Building selectedBuilding;
    private BuildingType selectedBuildingType;


    public UpgradeStationController() {
        // Constructor logic
    }
    public void upgradeStation(){
        selectedStation.setBuilding(selectedBuilding);
    }

    public List<Scenario> getAvailableScenarios() {
        // return available scenarios
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    public void setScenario(Scenario scenario) {
        this.selectedScenario = scenario;
    }

    public List<Station> getAvailableStations() {
        // return stations from selected scenario
        return Repositories.getInstance().getStationRepository().getStations();
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
        for (BuildingType buildingType: getAvailableBuildingTypes()) {
            names.add(buildingType.toString());
        }
        return names;
    }


    public List<BuildingType> getAvailableBuildingTypes() {
        // return available building types
        return new BuildingTypeRepository().getAvailableBuildingTypes();
    }

    public Scenario chooseScenario(List<Scenario> scenarios, int choice){
        Scenario scenario = scenarios.get(choice-1);
        return scenario;
    }

    public BuildingType chooseBuildingType(List<BuildingType> buildingTypes, int choice){
        BuildingType buildingType = buildingTypes.get(choice-1);
        return buildingType;
    }

    public Station chooseStation(List<Station> stations, int choice){
        Station station = stations.get(choice-1);
        return station;
    }

    public void setType(BuildingType type) {
        selectedBuildingType = type;
    }

    public Building createBuilding() {
        return new Building(selectedBuildingType);
    }

    public void setBuilding(Building building) {
        this.selectedBuilding = building;
    }
}