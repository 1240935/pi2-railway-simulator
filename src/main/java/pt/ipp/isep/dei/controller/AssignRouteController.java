package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for assigning routes between stations,
 * managing locomotives, cargo, and scenarios.
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
     * Constructs a new {@code AssignRouteController} with empty cargo and stop lists.
     */
    public AssignRouteController() {
        this.stops = new ArrayList<>();
        this.selectedCargo = new ArrayList<>();
    }

    public List<Locomotive> getAvailableLocomotives() {
        return selectedScenario.getLocomotives();
    }

    public List<String> getAvailableLocomotivesNames() {
        List<String> names = new ArrayList<>();
        for (Locomotive locomotive : getAvailableLocomotives()) {
            names.add(locomotive.toString());
        }
        return names;
    }

    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    public List<String> getAvailableScenarioNames() {
        List<String> names = new ArrayList<>();
        for (Scenario scenario : getAvailableScenarios()) {
            names.add(scenario.toString());
        }
        return names;
    }

    public List<Station> getAvailableStations() {
        return selectedScenario.getStations();
    }

    public List<String> getAvailableStationNames() {
        List<String> names = new ArrayList<>();
        for (Station station : getAvailableStations()) {
            names.add(station.toString());
        }
        return names;
    }

    public Scenario chooseScenario(List<Scenario> scenarios, int choice) {
        return scenarios.get(choice - 1);
    }

    public Locomotive chooseLocomotive(List<Locomotive> locomotives, int choice) {
        return locomotives.get(choice - 1);
    }

    public Station chooseStation(List<Station> stations, int choice) {
        return stations.get(choice - 1);
    }

    public void addStation(Station station) {
        stops.add(station);
    }

    public void setFirstStation(Station selectedStation) {
        this.firstStation = selectedStation;
    }

    public void setLastStation(Station lastStation) {
        this.lastStation = lastStation;
    }

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

    public void setSelectedLocomotive(Locomotive selectedLocomotive) {
        this.selectedLocomotive = selectedLocomotive;
    }

    public void setSelectedScenario(Scenario selectedScenario) {
        this.selectedScenario = selectedScenario;
    }

    /**
     * Creates a route with just first and last stations, the selected locomotive, and cargo.
     */
    public void createRoute() {
        selectedRoute = new Route(firstStation, lastStation, selectedLocomotive, selectedCargo);
        selectedScenario.addRoute(selectedRoute);
        Repositories.getInstance().getRouteRepository().addRoute(selectedRoute);
    }

    /**
     * Creates a full route including intermediate stops.
     */
    public void createRoute2() {
        selectedRoute = new Route(firstStation, lastStation, selectedLocomotive, selectedCargo);
        selectedRoute.setStops(stops);
        selectedScenario.addRoute(selectedRoute);
        Repositories.getInstance().getRouteRepository().addRoute(selectedRoute);
    }
}
