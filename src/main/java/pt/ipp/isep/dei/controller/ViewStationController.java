package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
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
public class ViewStationController {

    private Scenario selectedScenario;

    /**
     * Default constructor for the ViewStationController.
     */
    public ViewStationController() {
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
    public List<String> getAvailableScenarioDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Scenario s : getAvailableScenarios()) {
            descriptions.add("Map: " + s.getMap().getName() + ", Year: " + s.getYear());
        }
        return descriptions;
    }

    /**
     * Sets the selected scenario by its index in the list of available scenarios.
     *
     * @param index Index of the scenario in the scenarios list.
     * @return true if the index is valid and a scenario was selected; false otherwise.
     */
    public boolean setScenario(int index) {
        List<Scenario> scenarios = getAvailableScenarios();
        if (index >= 0 && index < scenarios.size()) {
            this.selectedScenario = scenarios.get(index);
            return true;
        }
        return false;
    }

    /**
     * Returns a list of names of all available stations.
     *
     * @return List of station names.
     */
    public List<String> getAvailableStationNames() {
        List<Station> stations = getAvailableStations();
        List<String> stationNames = new ArrayList<>();
        for (Station station : stations) {
            stationNames.add(station.getName());
        }
        return stationNames;
    }

    /**
     * Returns the station at the given index in the list of available stations.
     *
     * @param index Index of the station in the stations list.
     * @return Station at the given index, or null if index is invalid.
     */
    public Station getStationByIndex(int index) {
        List<Station> stations = getAvailableStations();
        if (index >= 0 && index < stations.size()) {
            return stations.get(index);
        }
        return null;
    }

    /**
     * Returns the list of buildings of the station at the given index.
     *
     * @param index Index of the station in the stations list.
     * @return The list of buildings of the station at the given index, or null if index is invalid.
     */
    public List<Building> getBuildingDetails(int index) {
        Station station = getStationByIndex(index);
        if (station != null) {
            return station.getBuildings(); // assuming getBuildings() returns List<Building>
        }
        return null;
    }


    /**
     * Returns a string describing the building details of the station at the given index.
     *
     * @param index Index of the station.
     * @return Description of the buildings or a default message if none are available.
     */
    public String getBuildingDetailsString(int index) {
        List<Building> buildings = getBuildingDetails(index);
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
     * Returns a string describing the cargo information of the given station.
     *
     * @param index Index of the station.
     * @return Description of cargo or a default message if not available.
     */
    public String getCargoInfo(int index) {
        Station station = getStationByIndex(index);
        if (station != null) {
            CargoStatus cargoStatus = getCargoInfo(station);
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
    public List<Station> getAvailableStations() {
        return selectedScenario.getStations();
    }

    /**
     * Retrieves cargo information of the given station relative to the currently selected scenario.
     *
     * @param station Station for which cargo information is requested.
     * @return CargoStatus object containing cargo information.
     * @throws IllegalStateException if no scenario has been selected.
     */
    public CargoStatus getCargoInfo(Station station) {
        if (selectedScenario == null) {
            throw new IllegalStateException("Scenario not set");
        }
        List<Industry> industries = selectedScenario.getMap().getIndustries();
        List<City> cities = selectedScenario.getMap().getCities();
        return station.getCargoes(industries, cities);
    }
}
