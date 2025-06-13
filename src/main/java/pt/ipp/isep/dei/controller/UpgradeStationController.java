package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller responsible for managing the upgrading of stations within a selected scenario.
 *
 * <p>Allows selection of scenario, station, and building type to upgrade a station's building.
 * Also manages player budget verification and building creation.</p>
 */
public class UpgradeStationController {





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
     * Retrieves the list of all available scenarios from the repository.
     *
     * @return list of scenarios
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }


    /**
     * Retrieves the list of stations available in the selected scenario.
     *
     * @return list of stations in the selected scenario, or empty list if no scenario selected
     */
    public List<Station> getAvailableStations(Scenario scenario) {
        if (scenario == null) {
            return new ArrayList<>();
        }
        return scenario.getStations();
    }


    /**
     * Returns the string representations of all available scenarios.
     *
     * @return list of scenario names/descriptions
     */
    public List<String> getAvailableScenarioNames() {
            ScenarioListMapper scenarioListMapper = new ScenarioListMapper();
            List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();
            List<ScenarioDTO> scenarioDTOS = scenarioListMapper.toDTOList(scenarios);

            List<String> names = new ArrayList<>();
            for (int i = 0; i < scenarioDTOS.size(); i++) {
                names.add(scenarioDTOS.get(i).toString());
            }

            return names;
    }

    /**
     * Returns the string representations of all available stations
     * within the selected scenario.
     *
     * @return list of station names/descriptions
     */
    public List<String> getAvailableStationNames(Scenario scenario) {
        StationListMapper stationListMapper = new StationListMapper();
        List<Station> stations = getAvailableStations(scenario);
        List<StationDTO> stationDTOS = stationListMapper.toDTOList(stations);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < stationDTOS.size(); i++) {
            names.add(stationDTOS.get(i).toString());
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
    public List<String> getAvailableBuildingNames(Scenario scenario, Station station) {
        List<String> names = new ArrayList<>();
        for (BuildingType buildingType : getAvailableBuildingTypes(scenario, station)) {
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
    public List<BuildingType> getAvailableBuildingTypes(Scenario scenario, Station station) {
        List<BuildingType> result = new ArrayList<>();

        if (scenario == null || station == null) return result;

        int year = scenario.getYear();
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
     * Sets the selected scenario by its index in the available scenarios list.
     *
     * @param index index of the scenario to select
     */
    public Scenario setScenarioByIndex(int index) {
        return getAvailableScenarios().get(index);
    }

    /**
     * Sets the selected station by its index in the available stations list.
     *
     * @param index index of the station to select
     */
    public Station setStationByIndex(int index, Scenario scenario){
        return getAvailableStations(scenario).get(index);
    }

    /**
     * Sets the selected building type by its index in the available building types list.
     *
     * @param index index of the building type to select
     */
    public BuildingType setBuildingTypeByIndex(int index, Scenario scenario, Station station) {
        return getAvailableBuildingTypes(scenario,station).get(index);
    }

    /**
     * Retrieves the cost of the currently selected building type.
     *
     * @return cost of selected building type
     * @throws IllegalStateException if no building type is selected
     */
    public int getSelectedBuildingCost(BuildingType buildingType) {
        if (buildingType != null) {
            return (buildingType.getCost());
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
     * Attempts to create a building of the selected type and assign it to the station.
     * Deducts the cost from the player's budget if affordable.
     * Adds the new building to the building repository.
     *
     * @return true if building was successfully created and assigned; false if player cannot afford
     */
    public boolean possibleBuilding(BuildingTypeDTO buildingTypeDTO, Station station) {
        // List of upgrade groups to check
        List<String> validUpgradeGroups = Arrays.asList("COMMUNICATION", "CAFE", "RESTAURANT", "HOTEL");

        // Check if the building's upgrade group is valid
        if (validUpgradeGroups.contains(buildingTypeDTO.getUpgradeGroup())) {
            // Get the buildings at the station
            List<Building> buildings = station.getBuildings();

            // Check if any building at the station has the same type and a smaller cost
            for (Building building : buildings) {
                if ( building.getBuildingType().getCost() < buildingTypeDTO.getCost()) {
                    return false; // There is a building of the same type with a lower cost, return false
                }
            }
        }


        // Check if the player can afford the building
        return player.canAfford(buildingTypeDTO.getCost());
    }



    public void createAndSetBuilding(BuildingDTO buildingDTO, Station station){
        BuildingMapper buildingMapper = new BuildingMapper();
        player.deductBudget(buildingDTO.getBuildingType().getCost());
        Building building = buildingMapper.toBuilding(buildingDTO);
        station.addBuilding(building);
    }

}
