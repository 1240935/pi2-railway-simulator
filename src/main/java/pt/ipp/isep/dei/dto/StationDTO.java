package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.*;
import java.util.List;

/**
 * Data Transfer Object for Station entity.
 * Holds data related to a station including type, position, name, center,
 * supplies, demands, associated scenario, and map.
 */
public class StationDTO {
    private StationType stationType;
    private Position position;
    private String name;
    private StationCenter center;
    private List<Cargo> supplies;
    private List<Cargo> demands;
    private Scenario scenario;
    private Map map;

    /**
     * Constructs a StationDTO with all fields.
     *
     * @param stationType the type of the station
     * @param position the geographical position
     * @param name the station name
     * @param center the station center
     * @param supplies list of supplied cargos
     * @param demands list of demanded cargos
     * @param scenario associated scenario
     * @param map the map where the station is located
     */
    public StationDTO(StationType stationType, Position position, String name, StationCenter center,
                      List<Cargo> supplies, List<Cargo> demands, Scenario scenario, Map map) {
        this.stationType = stationType;
        this.position = position;
        this.name = name;
        this.center = center;
        this.supplies = supplies;
        this.demands = demands;
        this.scenario = scenario;
        this.map = map;
    }

    /**
     * Default constructor.
     */
    public StationDTO() {}

    /** @return the name of the station */
    public String getName() {
        return name;
    }

    /** @return the cargos demanded by the station */
    public List<Cargo> getDemands() {
        return demands;
    }

    /** @return the cargos supplied by the station */
    public List<Cargo> getSupplies() {
        return supplies;
    }

    /** @return the geographic position of the station */
    public Position getPosition() {
        return position;
    }

    /** @return the center of the station */
    public StationCenter getCenter() {
        return center;
    }

    /** @return the type of the station */
    public StationType getStationType() {
        return stationType;
    }

    /** @return the scenario associated with the station */
    public Scenario getScenario() {
        return scenario;
    }

    /** @return the map where the station is located */
    public Map getMap() {
        return map;
    }

    /** Sets the name of the station
     * @param name the station name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Sets the center of the station
     * @param center the station center
     */
    public void setCenter(StationCenter center) {
        this.center = center;
    }

    /** Sets the demands list
     * @param demands cargos demanded by the station
     */
    public void setDemands(List<Cargo> demands) {
        this.demands = demands;
    }

    /** Sets the position of the station
     * @param position geographic position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /** Sets the station type
     * @param stationType the type of the station
     */
    public void setStationType(StationType stationType) {
        this.stationType = stationType;
    }

    /** Sets the supplies list
     * @param supplies cargos supplied by the station
     */
    public void setSupplies(List<Cargo> supplies) {
        this.supplies = supplies;
    }

    /** Sets the scenario
     * @param scenario associated scenario
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /** Sets the map
     * @param map the map where the station is located
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Returns the station name as string representation.
     * @return station name
     */
    @Override
    public String toString() {
        return name;
    }
}
