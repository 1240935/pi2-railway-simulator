package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.ScenarioDTO;
import pt.ipp.isep.dei.dto.ScenarioListMapper;
import pt.ipp.isep.dei.dto.StationDTO;
import pt.ipp.isep.dei.dto.StationListMapper;
import pt.ipp.isep.dei.repository.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for managing the viewing of stations within scenarios.
 *
 * <p>This controller provides functionality to:
 * <ul>
 *   <li>Retrieve available scenarios and their descriptions.</li>
 *   <li>Select a scenario.</li>
 *   <li>Retrieve stations and their details (such as buildings and cargo information) within a scenario.</li>
 * </ul>
 *
 * <p>It interacts with repositories to fetch domain data and supports UI components by providing
 * relevant information in simple formats like lists of strings or domain objects.</p>
 */
public class ViewStationDetailsController {

    /**
     * Default constructor for the ViewStationDetailsController.
     */
    public ViewStationDetailsController() {
    }

    /**
     * Retrieves all available scenarios from the scenario repository.
     *
     * @return a list of all scenarios currently stored in the repository.
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Returns a list of descriptive strings representing each available scenario.
     * Each description typically includes the map name and the scenario year.
     *
     * @return list of scenario descriptions as strings.
     */
    public List<String> getAvailableScenarioNames() {
        ScenarioListMapper scenarioListMapper = new ScenarioListMapper();
        List<Scenario> scenarios = getAvailableScenarios();
        List<ScenarioDTO> scenarioDTOS = scenarioListMapper.toDTOList(scenarios);

        List<String> names = new ArrayList<>();
        for (ScenarioDTO dto : scenarioDTOS) {
            names.add(dto.toString());
        }
        return names;
    }

    /**
     * Returns a list of names of all stations available within the specified scenario.
     *
     * @param scenarioIndex the index of the scenario in the scenario list.
     * @return list of station names/descriptions in the specified scenario.
     */
    public List<String> getAvailableStationNames(int scenarioIndex) {
        StationListMapper stationListMapper = new StationListMapper();
        Scenario scenario = setScenario(scenarioIndex);
        List<Station> stations = getAvailableStations(scenario);
        List<StationDTO> stationDTOS = stationListMapper.toDTOList(stations);

        List<String> names = new ArrayList<>();
        for (StationDTO dto : stationDTOS) {
            names.add(dto.toString());
        }
        return names;
    }

    /**
     * Retrieves a specific station by index within the selected scenario.
     *
     * @param stationIndex the index of the station within the scenario's station list.
     * @param scenarioIndex the index of the scenario.
     * @return the Station object if the index is valid, otherwise null.
     */
    public Station getStationByIndex(int stationIndex, int scenarioIndex) {
        Scenario scenario = setScenario(scenarioIndex);
        List<Station> stations = getAvailableStations(scenario);
        if (stationIndex >= 0 && stationIndex < stations.size()) {
            return stations.get(stationIndex);
        }
        return null;
    }

    /**
     * Selects and returns a scenario by its index.
     *
     * @param index the index of the scenario to select.
     * @return the Scenario object at the specified index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public Scenario setScenario(int index){
        return getAvailableScenarios().get(index);
    }

    /**
     * Retrieves the list of buildings associated with a specific station in a scenario.
     *
     * @param stationIndex the index of the station.
     * @param scenarioIndex the index of the scenario.
     * @return list of Building objects for the station, or null if station not found.
     */
    public List<Building> getBuildingDetails(int stationIndex, int scenarioIndex) {
        Station station = getStationByIndex(stationIndex, scenarioIndex);
        if (station != null) {
            return station.getBuildings();
        }
        return null;
    }

    /**
     * Provides a formatted string containing details about all buildings in a specified station.
     *
     * @param stationIndex the index of the station.
     * @param scenarioIndex the index of the scenario.
     * @return a multi-line string with building details or a message if none are available.
     */
    public String getBuildingDetailsString(int stationIndex, int scenarioIndex) {
        List<Building> buildings = getBuildingDetails(stationIndex, scenarioIndex);
        if (buildings != null && !buildings.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Building b : buildings) {
                sb.append(b.toString()).append("\n");
            }
            return sb.toString().trim();
        }
        return "No building details available.";
    }

    /**
     * Retrieves cargo information for a given station within a selected scenario.
     *
     * @param stationIndex the index of the station.
     * @param scenarioIndex the index of the scenario.
     * @return a string representation of the cargo status or a message if none is available.
     */
    public String getCargoInfo(int stationIndex, int scenarioIndex) {
        Station station = getStationByIndex(stationIndex, scenarioIndex);
        Scenario scenario = setScenario(scenarioIndex);
        if (station != null) {
            CargoStatus cargoStatus = getCargoInfo(station, scenario);
            if (cargoStatus != null) {
                return cargoStatus.toString();
            }
        }
        return "No cargo information available.";
    }

    /**
     * Retrieves the list of stations available within the specified scenario.
     *
     * @param scenario the scenario whose stations are to be retrieved.
     * @return list of Station objects associated with the scenario.
     */
    public List<Station> getAvailableStations(Scenario scenario) {
        return scenario.getStations();
    }

    /**
     * Retrieves cargo information of the given station relative to the provided scenario.
     *
     * @param station Station for which cargo information is requested.
     * @param scenario Scenario context to determine industries and cities relevant for cargo.
     * @return CargoStatus object containing cargo information for the station.
     * @throws IllegalStateException if the scenario is not set.
     */
    public CargoStatus getCargoInfo(Station station, Scenario scenario) {
        if (scenario == null) {
            throw new IllegalStateException("Scenario not set");
        }
        List<Industry> industries = scenario.getMap().getIndustries();
        List<City> cities = scenario.getMap().getCities();
        return station.getCargoes(industries, cities);
    }
}
