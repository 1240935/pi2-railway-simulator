package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for assigning routes between stations,
 * managing locomotives, cargo, and scenarios.
 * <p>
 * This controller allows selecting a scenario, stations (first, last, and stops),
 * locomotives, and cargo supplies to create and store routes within the system.
 * </p>
 */
public class AssignRouteController {

    private Scenario selectedScenario;
    private Station firstStation;
    private Station lastStation;
    private List<Station> stops;
    private Route selectedRoute;
    private Locomotive selectedLocomotive;
    private List<Cargo> selectedCargo;
    private List<Cargo> supplies;

    /**
     * Constructs a new {@code AssignRouteController} with empty lists
     * for stops and selected cargo.
     */
    public AssignRouteController() {
        this.stops = new ArrayList<>();
        this.selectedCargo = new ArrayList<>();
    }

    /**
     * Returns the list of locomotives available in the currently selected scenario.
     *
     * @return list of available locomotives
     */
    public List<Locomotive> getAvailableLocomotives() {
        return selectedScenario.getLocomotives();
    }

    /**
     * Returns a list of string representations (names) of available locomotives.
     *
     * @return list of locomotive names as strings
     */
    public List<String> getAvailableLocomotivesNames() {
        List<String> names = new ArrayList<>();
        for (Locomotive locomotive : getAvailableLocomotives()) {
            names.add(locomotive.toString());
        }
        return names;
    }

    /**
     * Retrieves all scenarios from the repository.
     *
     * @return list of all scenarios
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Returns a list of string representations of all available scenarios.
     *
     * @return list of scenario descriptions as strings
     */
    public List<String> getAvailableScenarioNames() {
        List<String> names = new ArrayList<>();
        for (Scenario scenario : getAvailableScenarios()) {
            names.add(scenario.toString());
        }
        return names;
    }

    /**
     * Returns the list of stations available in the currently selected scenario.
     *
     * @return list of available stations
     */
    public List<Station> getAvailableStations() {
        return selectedScenario.getStations();
    }

    /**
     * Returns a list of string representations (names) of available stations.
     *
     * @return list of station names as strings
     */
    public List<String> getAvailableStationNames() {
        List<String> names = new ArrayList<>();
        for (Station station : getAvailableStations()) {
            names.add(station.toString());
        }
        return names;
    }

    /**
     * Chooses a scenario from a list by a 1-based index.
     *
     * @param scenarios the list of available scenarios
     * @param choice    the 1-based index of the scenario to choose
     * @return the selected Scenario
     */
    public Scenario chooseScenario(List<Scenario> scenarios, int choice) {
        return scenarios.get(choice - 1);
    }

    /**
     * Chooses a locomotive from a list by a 1-based index.
     *
     * @param locomotives the list of available locomotives
     * @param choice      the 1-based index of the locomotive to choose
     * @return the selected Locomotive
     */
    public Locomotive chooseLocomotive(List<Locomotive> locomotives, int choice) {
        return locomotives.get(choice - 1);
    }

    /**
     * Chooses a station from a list by a 1-based index.
     *
     * @param stations the list of available stations
     * @param choice   the 1-based index of the station to choose
     * @return the selected Station
     */
    public Station chooseStation(List<Station> stations, int choice) {
        return stations.get(choice - 1);
    }

    /**
     * Adds a station to the list of intermediate stops on the route.
     *
     * @param station the Station to add as a stop
     */
    public void addStation(Station station) {
        stops.add(station);
    }

    /**
     * Sets the first station (start point) of the route.
     *
     * @param selectedStation the Station to set as the first station
     */
    public void setFirstStation(Station selectedStation) {
        this.firstStation = selectedStation;
    }

    /**
     * Sets the last station (end point) of the route.
     *
     * @param lastStation the Station to set as the last station
     */
    public void setLastStation(Station lastStation) {
        this.lastStation = lastStation;
    }

    /**
     * Retrieves a combined list of cargo supplies available at the first station,
     * last station, and all intermediate stops in the route.
     *
     * @return list of available Cargo supplies
     */
    public List<Cargo> getSupply() {
        List<Industry> industries = selectedScenario.getMap().getIndustries();
        List<City> cities = selectedScenario.getMap().getCities();

        List<Cargo> supplies = new ArrayList<>();

        if (stops != null) {
            for (Station stop : stops) {
                supplies.addAll(stop.getSuppliesOnly(industries, cities));
            }
        }

        if (firstStation != null) {
            supplies.addAll(firstStation.getSuppliesOnly(industries, cities));
        }

        if (lastStation != null) {
            supplies.addAll(lastStation.getSuppliesOnly(industries, cities));
        }

        return supplies;
    }

    /**
     * Returns a display list of cargo supply types with index numbers.
     *
     * @return list of strings describing cargo supply types
     */
    public List<String> getSupplyDisplayList() {
        List<Cargo> supplies = getSupply();
        List<String> displayList = new ArrayList<>();
        int counter = 1;

        for (Cargo cargo : supplies) {
            if (cargo != null && cargo.getType() != null) {
                displayList.add(counter++ + ". Type: " + cargo.getType().getName());
            }
        }
        return displayList;
    }

    /**
     * Selects a cargo from the supply list by 1-based index and adds it to the list of selected cargo.
     *
     * @param index 1-based index of cargo to select
     * @return true if selection was successful, false if index was invalid
     */
    public boolean selectCargoByIndex(int index) {
        List<Cargo> supply = getSupply();
        if (index < 1 || index > supply.size()) {
            return false;
        }
        Cargo selected = supply.get(index - 1);
        if (!selectedCargo.contains(selected)) {
            selectedCargo.add(selected);
        }
        return true;
    }

    /**
     * Sets the locomotive selected for the route.
     *
     * @param selectedLocomotive the Locomotive to assign
     */
    public void setSelectedLocomotive(Locomotive selectedLocomotive) {
        this.selectedLocomotive = selectedLocomotive;
    }

    /**
     * Sets the scenario currently being worked on.
     *
     * @param selectedScenario the Scenario to set
     */
    public void setSelectedScenario(Scenario selectedScenario) {
        this.selectedScenario = selectedScenario;
    }

    /**
     * Creates a route with only first and last stations, the selected locomotive, and selected cargo,
     * then adds it to the scenario and repository.
     */
    public void createRoute() {
        selectedRoute = new Route(firstStation, lastStation, selectedLocomotive, selectedCargo);
        selectedScenario.addRoute(selectedRoute);
        Repositories.getInstance().getRouteRepository().addRoute(selectedRoute);
    }

    /**
     * Creates a route including intermediate stops, selected locomotive, and cargo,
     * then adds it to the scenario and repository.
     */
    public void createRoute2() {
        selectedRoute = new Route(firstStation, lastStation, selectedLocomotive, selectedCargo);
        selectedRoute.setStops(stops);
        selectedScenario.addRoute(selectedRoute);
        Repositories.getInstance().getRouteRepository().addRoute(selectedRoute);
    }
}
