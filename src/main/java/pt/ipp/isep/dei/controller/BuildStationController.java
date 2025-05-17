package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for managing the process of building a new station.
 * <p>
 * This class handles the selection of scenarios, station types, positioning, naming,
 * and creation of stations, abstracting domain logic from the UI layer.
 */
public class BuildStationController {
    private final List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();

    private Scenario selectedScenario;

    private StationType selectedType;

    private StationCenter center;

    private String name;

    private Position position;

    /**
     * Default constructor.
     */
    public BuildStationController() {}

    /**
     * Returns the list of available scenarios.
     *
     * @return List of scenarios.
     */
    public List<Scenario> getAvailableScenarios() {
        return scenarios;
    }

    /**
     * Returns a list of descriptive strings for each available scenario,
     * formatted as "Map: [map name], Year: [year]".
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
     * Sets the selected scenario by its index in the list.
     *
     * @param index Index of the scenario in the available scenarios list.
     * @return true if the index is valid and a scenario is set; false otherwise.
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
     * Creates a Position object representing coordinates (x, y).
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return New Position instance.
     */
    public Position createPosition(int x, int y) {
        return new Position(x, y);
    }

    /**
     * Returns the map associated with the given scenario.
     *
     * @param selectedScenario Scenario to get the map from.
     * @return The Map of the scenario.
     */
    public Map getMap(Scenario selectedScenario){
        return selectedScenario.getMap();
    }

    /**
     * Checks if the given coordinates (x, y) are valid within the currently selected scenario's map.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return true if coordinates are valid; false otherwise.
     */
    public boolean checkCoordinates(int x, int y) {
        if (getMap(selectedScenario) == null) return false;
        return getMap(selectedScenario).checkCoordinates(new Position(x, y));
    }

    /**
     * Finds the closest city to the given position.
     *
     * @param pos Position to compare to.
     * @return Closest City instance, or null if no cities are available.
     */
    public City getClosestCity(Position pos) {
        List<City> cities = Repositories.getInstance().getCityRepository().getCities();
        City closest = null;
        double minDistance = Double.MAX_VALUE;

        for (City city : cities) {
            double distance = city.getPosition().euclideanDistance(pos);
            if (distance < minDistance) {
                minDistance = distance;
                closest = city;
            }
        }

        return closest;
    }

    /**
     * Generates a recommended station name based on the closest city and the selected station type.
     *
     * @param x X coordinate of the station position.
     * @param y Y coordinate of the station position.
     * @return Recommended station name, or an empty string if prerequisites are missing.
     */
    public String getRecommendedName(int x, int y) {
        if (selectedType == null || selectedScenario == null) return "";

        Position position = new Position(x, y);
        City closestCity = getClosestCity(position);

        if (closestCity == null) return selectedType.name(); // fallback

        return closestCity.getName() + " " + selectedType.name();
    }

    /**
     * Returns the list of available station types as strings.
     *
     * @return List of station type names.
     */
    public List<String> getAvailableStationTypes() {
        List<String> types = new ArrayList<>();
        for (StationType type : StationType.values()) {
            types.add(type.name());
        }
        return types;
    }

    /**
     * Sets the selected station type by index.
     *
     * @param index Index of the station type in StationType enum.
     * @return true if index is valid and type is set; false otherwise.
     */
    public boolean setType(int index) {
        StationType[] types = StationType.values();
        if (index >= 0 && index < types.length) {
            this.selectedType = types[index];
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the currently selected station type is "Station".
     *
     * @return true if selected type is Station; false otherwise.
     */
    public boolean isSelectedTypeStation() {
        return selectedType != null && selectedType.name().equals("Station");
    }

    /**
     * Gets the name of a city.
     *
     * @param city City instance.
     * @return Name of the city.
     */
    public String getName(City city) {
        return city.getName();
    }

    /**
     * Sets the name of the new station.
     *
     * @param name Name to assign.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the center position of the station based on a compass coordinate relative to (x, y).
     * This method applies only if the selected type is Station.
     *
     * @param compassCoord Compass direction ("NE", "SE", "NW", "SW").
     * @param x X coordinate of station position.
     * @param y Y coordinate of station position.
     * @throws IllegalStateException if station type or position are invalid.
     * @throws IllegalArgumentException if compassCoord is invalid.
     */
    public void setCompassCenter(String compassCoord, int x, int y) {
        if (selectedType == null || x == 0 || y == 0) {
            throw new IllegalStateException("Station type and position must be selected before setting compass center.");
        }

        if (selectedType != StationType.STATION) {
            throw new IllegalStateException("Compass center is only used for Station type.");
        }

        int radius = selectedType.getRadius(); // radius must be accessible in StationType

        int centerX, centerY;

        switch (compassCoord.toUpperCase()) {
            case "NE":
                centerX = x + radius;
                centerY = y - radius;
                break;
            case "SE":
                centerX = x + radius;
                centerY = y + radius;
                break;
            case "NW":
                centerX = x - radius;
                centerY = y - radius;
                break;
            case "SW":
                centerX = x - radius;
                centerY = y + radius;
                break;
            default:
                throw new IllegalArgumentException("Invalid compass coordinate. Must be NE, SE, NW, or SW.");
        }

        Position centerPosition = new Position(centerX, centerY);
        this.center = new StationCenter(centerPosition);
    }

    /**
     * Sets the geometric center of the station to the same position as the station itself.
     *
     * @throws IllegalStateException if the station position is not set.
     */
    public void setGeometricCenter() {
        if (position == null) {
            throw new IllegalStateException("Position must be set before setting geometric center.");
        }
        this.center = new StationCenter(position);
    }

    /**
     * Creates and adds a new Station instance with the current properties.
     *
     * @param x X coordinate for the station position.
     * @param y Y coordinate for the station position.
     */
    public void createStation(int x, int y) {
        Position pos = new Position(x, y);

        Station station = new Station(name, pos, center, selectedType);
        Repositories.getInstance().getStationRepository().addStation(station);
    }
}
