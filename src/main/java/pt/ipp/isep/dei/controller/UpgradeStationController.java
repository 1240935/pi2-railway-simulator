package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
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
     * The player performing the upgrade operation.
     */
    private Player player;


    /**
     * Default constructor for UpgradeStationController.
     */
    public UpgradeStationController() {
    }

    /**
     * Retrieves the list of all available scenarios from the repository.
     *
     * @return list of scenarios
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    public Scenario getScenarioByIndex(int index) {
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();

        if (index < 0 || index >= scenarios.size()) {
            throw new IndexOutOfBoundsException("Invalid scenario index: " + index);
        }

        return scenarios.get(index);
    }

    public Station getStationByIndex(int index) {
        List<Station> stations = Repositories.getInstance().getStationRepository().getStations();

        if (index < 0 || index >= stations.size()) {
            throw new IndexOutOfBoundsException("Invalid scenario index: " + index);
        }

        return stations.get(index);
    }

    public BuildingType getBuildingTypeByIndex(int scenarioIndex, int stationIndex, int buildingTypeIndex) {
        List<BuildingType> buildingTypes = getAvailableBuildingTypes(scenarioIndex, stationIndex);
        if (buildingTypeIndex < 0 || buildingTypeIndex >= buildingTypes.size()) {
            throw new IndexOutOfBoundsException("Invalid Building Type index: " + buildingTypeIndex);
        }

        return buildingTypes.get(buildingTypeIndex);
    }

    /**
     * Retrieves the list of stations available in the selected scenario.
     *
     * @return list of stations in the selected scenario, or empty list if no scenario selected
     */
    public List<Station> getAvailableStations(int scenarioIndex) {
        return getScenarioByIndex(scenarioIndex).getStations();
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
    public List<String> getAvailableStationNames(int scenarioIndex) {
        List<String> names = new ArrayList<>();
        for (Station station : getAvailableStations(scenarioIndex)) {
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
    public List<String> getAvailableBuildingTypeNames(int scenarioIndex, int stationIndex) {
        List<String> names = new ArrayList<>();
        List<BuildingType> types = getAvailableBuildingTypes(scenarioIndex, stationIndex);
        BuildingTypeListMapper typeMapper = new BuildingTypeListMapper();
        List<BuildingTypeDTO> dtos = typeMapper.toDTOList(types);

        for (BuildingTypeDTO dto : dtos) {
            names.add(dto.toString());
        }
        return names;
    }

    public List<BuildingType> getAvailableBuildingTypes(int scenarioIndex, int stationIndex) {
        Scenario scenario = getScenarioByIndex(scenarioIndex);
        Station station = getStationByIndex(stationIndex);
        int year = scenario.getYear();

        List<Building> existingBuildings = station.getBuildings();
        List<BuildingType> availableTypes = Repositories.getInstance()
                .getBuildingTypeRepository()
                .getAvailableBuildingTypes();

        List<BuildingType> result = new ArrayList<>();

        boolean telephoneAvailable = false;
        for (BuildingType buildingType : availableTypes) {
            if ("Telephone".equalsIgnoreCase(buildingType.name()) && buildingType.getAvailabilityYear() <= year) {
                telephoneAvailable = true;
                break;
            }
        }

        for (BuildingType candidate : availableTypes) {
            if (candidate.getAvailabilityYear() > year) continue;

            if (telephoneAvailable && "Telegraph".equalsIgnoreCase(candidate.name())) continue;

            boolean isExclusive = false;
            for (Building existing : existingBuildings) {
                if (candidate.isMutuallyExclusiveWith(existing.getBuildingType())) {
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

    public void createBuilding(BuildingDTO buildingDTO, int stationIndex){
        BuildingMapper buildingMapper = new BuildingMapper();
        player.deductBudget(buildingDTO.getBuildingType().getCost());
        Building building = buildingMapper.toBuilding(buildingDTO);
        getStationByIndex(stationIndex).addBuilding(building);
    }

}
