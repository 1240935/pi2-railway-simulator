package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a railway scenario within the system.
 * A scenario is characterized by a map, a year, and several types
 * including locomotive types, industry types, industry changers,
 * and resource types for importation and exportation.
 */
public class Scenario implements Serializable {
    private Map map;
    private int year;
    private List<LocomotiveType> locomotiveTypes;
    private int industryChanger;
    private List<ResourceType> importation;
    private List<ResourceType> exportation;
    private List<Station> stations;
    private List<Locomotive> locomotives;
    private List<Route> routes;

    /**
     * Constructs a new {@code Scenario} with the specified parameters.
     *
     * @param map the map associated with the scenario
     * @param year the year in which the scenario takes place
     * @param locomotiveTypes the list of locomotive types allowed in the scenario
     * @param importation the list of resource types imported in the scenario
     * @param exportation the list of resource types exported in the scenario
     */
    public Scenario(Map map, int year, List<LocomotiveType> locomotiveTypes,
                     List<ResourceType> importation, List<ResourceType> exportation, int industryChanger) {
        this.map = map;
        this.year = year;
        this.locomotiveTypes = locomotiveTypes;
        this.industryChanger = industryChanger;
        this.importation = importation;
        this.exportation = exportation;
        this.stations = new ArrayList<>();
        this.locomotives = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    public int getIndustryChanger() {
        return industryChanger;
    }

    /**
     * Returns the list of resource types imported in this scenario.
     *
     * @return the list of imported resource types
     */
    public List<ResourceType> getImportation() {
        return importation;
    }

    /**
     * Returns the list of resource types exported in this scenario.
     *
     * @return the list of exported resource types
     */
    public List<ResourceType> getExportation() {
        return exportation;
    }

    /**
     * Returns the map associated with this scenario.
     *
     * @return the scenario's map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the year in which this scenario takes place.
     *
     * @return the scenario year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the list of locomotive types allowed in this scenario.
     *
     * @return the list of allowed locomotive types
     */
    public List<LocomotiveType> getLocomotiveTypes() {
        return locomotiveTypes;
    }

    /**
     * Adds a station to the scenario.
     *
     * @param station the station to addScenario
     */
    public void addStation(Station station) {
        stations.add(station);
    }

    /**
     * Returns an unmodifiable list of stations in the scenario.
     *
     * @return a list of stations
     */
    public List<Station> getStations() {
        return new ArrayList<>(stations);
    }

    /**
     * Adds a locomotive to the scenario.
     *
     * @param locomotive the locomotive to addScenario
     */
    public void addLocomotive(Locomotive locomotive) {
        locomotives.add(locomotive);
    }

    /**
     * Returns an unmodifiable list of locomotives in the scenario.
     *
     * @return a list of locomotives
     */
    public List<Locomotive> getLocomotives() {
        return new ArrayList<>(locomotives);
    }

    /**
     * Returns an unmodifiable list of routes in the scenario.
     *
     * @return a list of routes
     */
    public List<Route> getRoutes() {
        return new ArrayList<>(routes);
    }

    /**
     * Adds a route to the scenario.
     *
     * @param route the route to addScenario
     */
    public void addRoute(Route route) {
        routes.add(route);
    }

    /**
     * Returns a string representation of the scenario.
     *
     * @return the scenario's map name and year as a formatted string
     */
    @Override
    public String toString() {
        return map.getName() + " (" + year + ")";
    }
}
