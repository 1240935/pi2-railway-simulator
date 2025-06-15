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
     * @return list of all Scenario objects available.
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Retrieves a Scenario by its index in the repository.
     *
     * @param index index of the scenario
     * @return Scenario at the given index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Scenario getScenarioByIndex(int index) {
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();

        if (index < 0 || index >= scenarios.size()) {
            throw new IndexOutOfBoundsException("Invalid scenario index: " + index);
        }

        return scenarios.get(index);
    }

    /**
     * Retrieves a Station by its index in the repository.
     *
     * @param index index of the station
     * @return Station at the given index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Station getStationByIndex(int index) {
        List<Station> stations = Repositories.getInstance().getStationRepository().getStations();

        if (index < 0 || index >= stations.size()) {
            throw new IndexOutOfBoundsException("Invalid station index: " + index);
        }

        return stations.get(index);
    }

    /**
     * Retrieves a BuildingType by its index among the available building types
     * for the given scenario and station.
     *
     * @param scenarioIndex    index of the scenario
     * @param stationIndex     index of the station
     * @param buildingTypeIndex index of the building type
     * @return BuildingType at the given index
     * @throws IndexOutOfBoundsException if buildingTypeIndex is invalid
     */
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
     * @param scenarioIndex index of the scenario
     * @return list of stations in the selected scenario
     */
    public List<Station> getAvailableStations(int scenarioIndex) {
        return getScenarioByIndex(scenarioIndex).getStations();
    }

    /**
     * Returns the string representations of all available scenarios.
     *
     * @return list of scenario names or descriptions
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
     * @param scenarioIndex index of the scenario
     * @return list of station names or descriptions
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
     * @param scenarioIndex index of the scenario
     * @param stationIndex  index of the station
     * @return list of building type names or descriptions
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

    /**
     * Retrieves the available building types for upgrade filtered by:
     * <ul>
     *     <li>Scenario year (only buildings available up to that year)</li>
     *     <li>Mutual exclusivity with existing buildings in the station</li>
     *     <li>Handles special case for Telephone vs Telegraph exclusivity</li>
     * </ul>
     *
     * @param scenarioIndex index of the scenario
     * @param stationIndex  index of the station
     * @return list of available BuildingType objects
     */
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

            // If Telephone is available, exclude Telegraph building type
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
     * @param player the Player instance to set
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Creates a new Building in the specified station and deducts
     * the cost from the player's budget.
     *
     * @param buildingDTO Data transfer object representing the building to create
     * @param stationIndex index of the station where the building will be added
     */
    public void createBuilding(BuildingDTO buildingDTO, int stationIndex){
        BuildingMapper buildingMapper = new BuildingMapper();
        player.deductBudget(buildingDTO.getBuildingType().getCost());
        Building building = buildingMapper.toBuilding(buildingDTO);
        getStationByIndex(stationIndex).addBuilding(building);
    }
}
