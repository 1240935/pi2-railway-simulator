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
 * It provides functionality to:
 * - Retrieve available scenarios and their descriptions.
 * - Select a scenario.
 * - Retrieve stations and their details (such as buildings and cargo information).
 *
 * This controller interacts with repositories to fetch domain data,
 * and supports UI components by providing relevant information in simple formats.
 */
public class ViewStationDetailsController {

    /**
     * Default constructor for the ViewStationController.
     */
    public ViewStationDetailsController() {
    }

    /**
     * Retrieves all available scenarios from the repository.
     *
     * @return A list of all scenarios.
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Returns a list of descriptive strings representing each available scenario.
     * Each description contains the map name and the year of the scenario.
     *
     * @return List of scenario descriptions.
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
     * Returns a list of names of all available stations.
     *
     * @return List of station names.
     */
    public List<String> getAvailableStationNames(int scenarioIndex) {
        StationListMapper stationListMapper = new StationListMapper();
        Scenario scenario = setScenario(scenarioIndex);
        List<Station> stations = getAvailableStations(scenario);
        List<StationDTO> stationDTOS = stationListMapper.toDTOList(stations);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < stationDTOS.size(); i++) {
            names.add(stationDTOS.get(i).toString());
        }

        return names;
    }


    public Station getStationByIndex(int stationIndex, int scenarioIndex) {
        Scenario scenario = setScenario(scenarioIndex);
        List<Station> stations = getAvailableStations(scenario);
        if (stationIndex >= 0 && stationIndex < stations.size()) {
            return stations.get(stationIndex);
        }
        return null;
    }

    public Scenario setScenario(int index){
            return getAvailableScenarios().get(index);
    }


    public List<Building> getBuildingDetails(int stationIndex, int scenarioIndex) {
        Station station = getStationByIndex(stationIndex, scenarioIndex);
        if (station != null) {
            return station.getBuildings();
        }
        return null;
    }

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

    public String getCargoInfo(int stationIndex, int scenarioIndex) {
        Station station = getStationByIndex(stationIndex, scenarioIndex);
        Scenario scenario = setScenario(scenarioIndex);
        if (station != null) {
            CargoStatus cargoStatus = getCargoInfo(station,scenario );
            if (cargoStatus != null) {
                return cargoStatus.toString();
            }
        }
        return "No cargo information available.";
    }

    /**
     * Retrieves all available stations from the repository.
     *
     * @return A list of all stations.
     */
    public List<Station> getAvailableStations(Scenario scenario) {
        return scenario.getStations();
    }

    /**
     * Retrieves cargo information of the given station relative to the currently selected scenario.
     *
     * @param station Station for which cargo information is requested.
     * @return CargoStatus object containing cargo information.
     * @throws IllegalStateException if no scenario has been selected.
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
