package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.CargoRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Controller class responsible for managing the process of building a new station.
 * <p>
 * This class handles the selection of scenarios, station types, positioning, naming,
 * and creation of stations, abstracting domain logic from the UI layer.
 */
public class BuildStationController {

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
        return Repositories.getInstance().getScenarioRepository().getScenarios();
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map getMap(int scenarioIndex){
        return getScenarioByIndex(scenarioIndex).getMap();
    }

    /**
     * Finds the closest city to the given position.
     *
     * @param pos Position to find the nearest city to.
     * @return Closest City instance or null if no cities exist.
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
     * Returns the list of available station types as strings.
     *
     * @return List of station type names.
     */
    public List<String> getAvailableStationTypes() {
        StationTypeMapper stationTypeMapper = new StationTypeMapper();
            List<StationType> types = Arrays.asList(StationType.values());
            List<StationTypeDTO> dtos = stationTypeMapper.toDTOList(types);

            List<String> names = new ArrayList<>();
            for (StationTypeDTO dto : dtos) {
                names.add(dto.toString());
            }

            return names;
        }


    /**
     * Checks if the currently selected station type is "Station".
     *
     * @return true if selected type is Station; false otherwise.
     */
    public boolean isSelectedTypeStation(int typeIndex) {
        return getStationTypeByIndex(typeIndex).name().equals("STATION");
    }

    public Position createPosition(int x, int y){
        return Station.createPosition(x, y);
    }

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

    public Scenario getScenarioByIndex(int index) {
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();

        if (index < 0 || index >= scenarios.size()) {
            throw new IndexOutOfBoundsException("Invalid scenario index: " + index);
        }

        return scenarios.get(index);
    }



    /**
     * Sets the center position of the station relative to (x, y) using compass coordinates.
     * This method is applicable only if the selected station type is Station.
     *
     * @param direction Compass direction ("NE", "SE", "NW", or "SW").
     * @param x            X coordinate of the station base position.
     * @param y            Y coordinate of the station base position.
     * @throws IllegalStateException    if the station type or position is not properly set.
     * @throws IllegalArgumentException if the compass coordinate is invalid.
     */
    public StationCenter getCenter(String direction, int x, int y, int typeIndex) {
        if (getStationTypeByIndex(typeIndex) == null || x == 0 || y == 0) {
            throw new IllegalStateException("Station type and position must be selected before setting compass center.");
        }
        if (direction.isEmpty()){
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
     * Returns the StationType at the given index.
     *
     * @param index Index of the desired StationType.
     * @return StationType at the given index, or null if index is invalid.
     */
    public StationType getStationTypeByIndex(int index) {
        StationType[] types = StationType.values();
        if (index >= 0 && index < types.length) {
            return types[index];
        }
        return null;
    }

    public void createStationGUI(Position position, String name, StationCenter center, StationType type, List<Cargo> demandedCargoes, List <Cargo> suppliedCargoes, Player player, int scenarioIndex , int typeIndex, Map map, Scenario scenario) {
        player.deductBudget(getStationTypeByIndex(typeIndex).getCost());
        Station station = Repositories.getInstance().getStationRepository().createStation(position, name, center, type, demandedCargoes, suppliedCargoes, map, scenario);
        getScenarioByIndex(scenarioIndex).addStation(station);
    }

    public void createStation(StationDTO stationDTO) {
        StationMapper stationMapper = new StationMapper();
        player.deductBudget(stationDTO.getStationType().getCost());
        stationDTO.getScenario().addStation(stationMapper.toStation(stationDTO));
    }

    public Player getPlayer() {
        return player;
    }
}
