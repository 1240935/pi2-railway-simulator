package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.CargoRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for managing the process of building a new station.
 * <p>
 * This class handles scenario selection, station type selection, positioning,
 * naming, and creation of stations. It abstracts the domain logic from the UI layer,
 * enabling easier testing and separation of concerns.
 */
public class BuildStationController {

    private Player player;

    /**
     * Default constructor for BuildStationController.
     */
    public BuildStationController() {}

    /**
     * Returns the list of available scenarios from the scenario repository.
     *
     * @return List of available {@link Scenario} objects.
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Returns a list of human-readable descriptions for all available scenarios.
     * Each description is formatted as "Map: [map name], Year: [year]".
     *
     * @return List of scenario description strings.
     */
    public List<String> getAvailableScenarioDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Scenario s : getAvailableScenarios()) {
            descriptions.add("Map: " + s.getMap().getName() + ", Year: " + s.getYear());
        }
        return descriptions;
    }

    /**
     * Sets the current player using this controller.
     *
     * @param player The player currently interacting with the system.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Retrieves the map associated with a given scenario index.
     *
     * @param scenarioIndex Index of the scenario.
     * @return The {@link Map} object associated with the scenario.
     */
    public Map getMap(int scenarioIndex) {
        return getScenarioByIndex(scenarioIndex).getMap();
    }

    /**
     * Finds the closest city to a given position on the map.
     *
     * @param pos           The position to search from.
     * @param scenarioIndex Index of the scenario to use.
     * @return Closest {@link City}, or null if no cities exist.
     */
    public City getClosestCity(Position pos, int scenarioIndex) {
        List<City> cities = getScenarioByIndex(scenarioIndex).getMap().getCities();
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
     * Suggests a recommended name for the station based on nearby cities and station type.
     *
     * @param x             X coordinate of the station.
     * @param y             Y coordinate of the station.
     * @param scenarioIndex Index of the selected scenario.
     * @param typeIndex     Index of the selected station type.
     * @return Suggested name, or null if no city is within radius.
     */
    public String getRecommendedName(int x, int y, int scenarioIndex, int typeIndex) {
        if (getStationTypeByIndex(typeIndex) == null || getScenarioByIndex(scenarioIndex) == null) return null;

        int radius = getStationTypeByIndex(typeIndex).getRadius();
        Position position = new Position(x, y);
        City closestCity = getClosestCity(position, scenarioIndex);

        if (closestCity == null || closestCity.getPosition().euclideanDistance(position) > radius) {
            System.out.println("No nearby city found within the allowed radius. Please enter a name manually.");
            return null;
        }

        return closestCity.getName() + " " + getStationTypeByIndex(typeIndex).name();
    }

    /**
     * Returns the list of available station types as string descriptions.
     *
     * @return List of station type names.
     */
    public List<String> getAvailableStationTypes() {
        StationTypeListMapper stationTypeMapper = new StationTypeListMapper();
        List<StationType> types = Repositories.getInstance().getStationTypeRepository().getStationTypes();
        List<StationTypeDTO> dtos = stationTypeMapper.toDTOList(types);

        List<String> names = new ArrayList<>();
        for (StationTypeDTO dto : dtos) {
            names.add(dto.toString());
        }

        return names;
    }

    /**
     * Checks if the selected station type is of type STATION.
     *
     * @param typeIndex Index of the station type.
     * @return {@code true} if the station type is STATION, {@code false} otherwise.
     */
    public boolean isSelectedTypeStation(int typeIndex) {
        return getStationTypeByIndex(typeIndex).name().equals("STATION");
    }

    /**
     * Creates a new {@link Position} object with the given coordinates.
     *
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return New {@link Position} object.
     */
    public Position createPosition(int x, int y) {
        return Station.createPosition(x, y);
    }

    /**
     * Retrieves a list of cargoes demanded by nearby industries at a given location.
     *
     * @param x             X coordinate.
     * @param y             Y coordinate.
     * @param scenarioIndex Index of the scenario.
     * @param typeIndex     Index of the station type.
     * @return List of demanded {@link Cargo} objects.
     */
    public List<Cargo> getDemandedCargoes(int x, int y, int scenarioIndex, int typeIndex) {
        Position pos = new Position(x, y);
        int radius = getStationTypeByIndex(typeIndex).getRadius();
        List<Industry> industries = getScenarioByIndex(scenarioIndex).getMap().getIndustries();
        CargoRepository cargoRepo = Repositories.getInstance().getCargoRepository();

        List<Cargo> demanded = new ArrayList<>();
        for (Industry industry : industries) {
            if (industry.getPosition().euclideanDistance(pos) <= radius) {
                for (ResourceType r : industry.getType().getImportation()) {
                    Cargo c = cargoRepo.getCargoForResourceType(r);
                    if (c != null && !demanded.contains(c)) {
                        demanded.add(c);
                    }
                }
            }
        }
        return demanded;
    }

    /**
     * Retrieves a list of cargoes supplied by nearby industries at a given location.
     *
     * @param x             X coordinate.
     * @param y             Y coordinate.
     * @param scenarioIndex Index of the scenario.
     * @param typeIndex     Index of the station type.
     * @return List of supplied {@link Cargo} objects.
     */
    public List<Cargo> getSuppliedCargoes(int x, int y, int scenarioIndex, int typeIndex) {
        Position pos = new Position(x, y);
        int radius = getStationTypeByIndex(typeIndex).getRadius();
        List<Industry> industries = getScenarioByIndex(scenarioIndex).getMap().getIndustries();
        CargoRepository cargoRepo = Repositories.getInstance().getCargoRepository();

        List<Cargo> supplied = new ArrayList<>();
        for (Industry industry : industries) {
            if (industry.getPosition().euclideanDistance(pos) <= radius) {
                for (ResourceType r : industry.getType().getExportation()) {
                    Cargo c = cargoRepo.getCargoForResourceType(r);
                    if (c != null && !supplied.contains(c)) {
                        supplied.add(c);
                    }
                }
            }
        }
        return supplied;
    }

    /**
     * Retrieves the {@link Scenario} at the specified index.
     *
     * @param index Index of the scenario to retrieve.
     * @return The {@link Scenario} object.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public Scenario getScenarioByIndex(int index) {
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();
        if (index < 0 || index >= scenarios.size()) {
            throw new IndexOutOfBoundsException("Invalid scenario index: " + index);
        }
        return scenarios.get(index);
    }

    /**
     * Calculates and returns the station's center position based on compass direction.
     * Used only for stations of type STATION.
     *
     * @param direction Compass direction (NE, SE, NW, SW).
     * @param x         X coordinate.
     * @param y         Y coordinate.
     * @param typeIndex Index of the station type.
     * @return Computed {@link StationCenter}.
     * @throws IllegalStateException    if prerequisites are not set.
     * @throws IllegalArgumentException if direction is invalid.
     */
    public StationCenter getCenter(String direction, int x, int y, int typeIndex) {
        if (getStationTypeByIndex(typeIndex) == null || x == 0 || y == 0) {
            throw new IllegalStateException("Station type and position must be selected before setting compass center.");
        }

        if (direction.isEmpty()) {
            return new StationCenter(createPosition(x, y));
        } else {
            int radius = getStationTypeByIndex(typeIndex).getRadius();

            int centerX, centerY;

            switch (direction.toUpperCase()) {
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
            return new StationCenter(centerPosition);
        }
    }

    /**
     * Returns the {@link StationType} at the specified index.
     *
     * @param index Index of the station type.
     * @return {@link StationType} object, or null if index is invalid.
     */
    public StationType getStationTypeByIndex(int index) {
        List<StationType> types = Repositories.getInstance().getStationTypeRepository().getStationTypes();
        if (index >= 0 && index < types.size()) {
            return types.get(index);
        }
        return null;
    }

    /**
     * Creates a new station using parameters gathered from the GUI.
     * Deducts the cost from the player budget and adds the station to the scenario.
     *
     * @param position         Station position.
     * @param name             Station name.
     * @param center           Station center.
     * @param type             Station type.
     * @param demandedCargoes  List of demanded cargoes.
     * @param suppliedCargoes  List of supplied cargoes.
     * @param player           Player who builds the station.
     * @param scenarioIndex    Index of the scenario.
     * @param typeIndex        Index of the station type.
     * @param map              The map associated with the station.
     * @param scenario         The scenario in which to place the station.
     */
    public void createStationGUI(Position position, String name, StationCenter center, StationType type,
                                 List<Cargo> demandedCargoes, List<Cargo> suppliedCargoes,
                                 Player player, int scenarioIndex, int typeIndex, Map map, Scenario scenario) {
        player.deductBudget(getStationTypeByIndex(typeIndex).getCost());
        Station station = Repositories.getInstance().getStationRepository()
                .createStation(position, name, center, type, demandedCargoes, suppliedCargoes, map, scenario);
        getScenarioByIndex(scenarioIndex).addStation(station);
    }

    /**
     * Creates a new station using a {@link StationDTO}.
     * Deducts cost and adds the new station to the scenario.
     *
     * @param stationDTO DTO containing station information.
     */
    public void createStation(StationDTO stationDTO) {
        StationMapper stationMapper = new StationMapper();
        player.deductBudget(stationDTO.getStationType().getCost());
        stationDTO.getScenario().addStation(stationMapper.toStation(stationDTO));
    }

    /**
     * Returns the current {@link Player} instance.
     *
     * @return Player using the controller.
     */
    public Player getPlayer() {
        return player;
    }
}
