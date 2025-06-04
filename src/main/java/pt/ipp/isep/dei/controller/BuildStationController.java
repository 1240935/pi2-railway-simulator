package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.CargoRepository;
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
    /**
     * List of all available scenarios from the repository.
     */
    private final List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();

    private Scenario selectedScenario;

    private StationType selectedType;

    private StationCenter center;

    private String name;

    private Position position;

    private BuildingType buildingtype;

    private List<Cargo> demandedCargoes;

    private List<Cargo> suppliedCargoes;

    private Player player;

    /**
     * Default constructor.
     */
    public BuildStationController() {}

    /**
     * Returns the list of available scenarios.
     *
     * @return List of Scenario objects available.
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
     * Creates a new Position object with the given coordinates.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return New Position instance representing the coordinates.
     */
    public Position createPosition(int x, int y) {
        return new Position(x, y);
    }

    /**
     * Returns the map associated with the provided scenario.
     *
     * @param selectedScenario Scenario to retrieve the map from.
     * @return Map object from the scenario.
     */
    public Map getMap(Scenario selectedScenario){
        return selectedScenario.getMap();
    }

    /**
     * Validates whether the given coordinates are valid within the currently selected scenario's map.
     *
     * @param x X coordinate to check.
     * @param y Y coordinate to check.
     * @return true if the coordinates are within the map boundaries; false otherwise.
     */

    public boolean checkOverbuilding(int x, int y) {
        if (selectedScenario == null || getMap(selectedScenario) == null) {
            return false;
        }

        Map map = getMap(selectedScenario);
        Position pos = new Position(x, y);

        if (!map.checkCoordinates(pos)) {
            return false;
        }

        for (City city : map.getCities()) {
            if (city.getPosition().equals(pos)) {
                return false;
            }
        }

        for (Industry industry : map.getIndustries()) {
            if (industry.getPosition().equals(pos)) {
                return false;
            }
        }

        for (Station station : selectedScenario.getStations()) {
            if (station.getPosition().equals(pos)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds the closest city to the given position.
     *
     * @param pos Position to find the nearest city to.
     * @return Closest City instance or null if no cities exist.
     */
    public City getClosestCity(Position pos) {
        List<City> cities = selectedScenario.getMap().getCities();
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
     * Generates a recommended station name based on the closest city and selected station type.
     *
     * @param x X coordinate of the station position.
     * @param y Y coordinate of the station position.
     * @return Recommended station name or an empty string if prerequisites are not met.
     */
    public String getRecommendedName(int x, int y) {
        if (selectedType == null || selectedScenario == null) return "";
        int radius = selectedType.getRadius();
        Position position = new Position(x, y);
        City closestCity = getClosestCity(position);

        if (closestCity == null || closestCity.getPosition().euclideanDistance(position) <= radius) return selectedType.name(); // fallback

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
     * Sets the selected station type by its index in the StationType enum.
     *
     * @param index Index of the station type.
     * @return true if the index is valid and the type was set; false otherwise.
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
        return selectedType.name().equals("STATION");
    }

    /**
     * Retrieves the name of a given city.
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
     * @param name Name string to assign to the station.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Determines the demanded and supplied cargoes for the station based on its position
     * and the selected station type's radius.
     *
     * This method queries all industries within the radius and converts their imported
     * and exported ResourceTypes to Cargo objects using the CargoRepository.
     *
     * @param x x coordinate of the station.
     * @param y y coordinate of the station.
     * @throws IllegalStateException if station type or scenario are not selected.
     */
    public void determineStationCargoes(int x, int y) {
        Position pos = new Position(x, y);
        demandedCargoes = new ArrayList<>();
        suppliedCargoes = new ArrayList<>();

        if (selectedType == null) {
            throw new IllegalStateException("Station type must be selected before determining cargoes.");
        }

        if (selectedScenario == null) {
            throw new IllegalStateException("Scenario must be selected before determining cargoes.");
        }

        int radius = selectedType.getRadius();
        List<Industry> industries = selectedScenario.getMap().getIndustries();
        CargoRepository cargoRepo = Repositories.getInstance().getCargoRepository();

        for (Industry industry : industries) {
            if (industry.getPosition().euclideanDistance(pos) <= radius) {

                // Convert demanded ResourceTypes to Cargo
                for (ResourceType r : industry.getType().getImportation()) {
                    Cargo c = cargoRepo.getCargoForResourceType(r);
                    if (c != null && !demandedCargoes.contains(c)) {
                        demandedCargoes.add(c);
                    }
                }

                // Convert supplied ResourceTypes to Cargo
                for (ResourceType r : industry.getType().getExportation()) {
                    Cargo c = cargoRepo.getCargoForResourceType(r);
                    if (c != null && !suppliedCargoes.contains(c)) {
                        suppliedCargoes.add(c);
                    }
                }
            }
        }
    }

    /**
     * Sets the center position of the station relative to (x, y) using compass coordinates.
     * This method is applicable only if the selected station type is Station.
     *
     * @param compassCoord Compass direction ("NE", "SE", "NW", or "SW").
     * @param x            X coordinate of the station base position.
     * @param y            Y coordinate of the station base position.
     * @throws IllegalStateException    if the station type or position is not properly set.
     * @throws IllegalArgumentException if the compass coordinate is invalid.
     */
    public void setCompassCenter(String compassCoord, int x, int y) {
        if (selectedType == null || x == 0 || y == 0) {
            throw new IllegalStateException("Station type and position must be selected before setting compass center.");
        }
        int radius = selectedType.getRadius();

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
     * Sets the geometric center of the station equal to its position.
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
     * Sets the position of the station.
     *
     * @param x X coordinate of the station.
     * @param y Y coordinate of the station.
     */
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
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

    /**
     * Returns the currently selected station type.
     *
     * @return Selected StationType.
     */
    public StationType getSelectedType() {
        return selectedType;
    }

    /**
     * Creates and adds a new Station instance with the current attributes.
     * Deducts the station cost from the player's budget if affordable.
     *
     * @param x X coordinate for the station.
     * @param y Y coordinate for the station.
     * @return true if the station was created successfully; false if player cannot afford it.
     */
    public boolean createStation(int x, int y) {
        Position pos = new Position(x, y);
        if (!player.canAfford(selectedType.getCost())) {
            return false;
        }
        player.deductBudget(selectedType.getCost());
        Station station = new Station(name, center, pos, selectedType, demandedCargoes, suppliedCargoes);
        selectedScenario.addStation(station);
        Repositories.getInstance().getStationRepository().addStation(station);
        return true;
    }
}
