package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

public class AssignRouteController {

    private final List<PointOfRoute> pointsOfRoute = new ArrayList<>();

    public AssignRouteController() {
    }

    public List<String> getAvailableScenarioNames() {
        List<String> names = new ArrayList<>();
        for (Scenario scenario : Repositories.getInstance().getScenarioRepository().getScenarios()) {
            names.add(scenario.toString());
        }
        return names;
    }

    public Scenario getScenarioByIndex(int scenarioIndex) {
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();
        if (scenarioIndex < 1 || scenarioIndex > scenarios.size()) {
            throw new IllegalArgumentException("Invalid scenario index.");
        }
        return scenarios.get(scenarioIndex - 1);
    }

    public List<String> getAvailableStationNames(int scenarioIndex) {
        List<String> names = new ArrayList<>();
        for (Station station : getScenarioByIndex(scenarioIndex).getStations()) {
            names.add(station.toString());
        }
        return names;
    }

    public Station getStationByIndex(int scenarioIndex, int stationIndex) {
        List<Station> stations = getScenarioByIndex(scenarioIndex).getStations();
        if (stationIndex < 1 || stationIndex > stations.size()) {
            throw new IllegalArgumentException("Invalid station index.");
        }
        return stations.get(stationIndex - 1);
    }

    public List<String> getAvailableCargoNames(int scenarioIndex, int stationIndex) {
        List<Industry> industries = getScenarioByIndex(scenarioIndex).getMap().getIndustries();
        List<City> cities = getScenarioByIndex(scenarioIndex).getMap().getCities();
        List<Cargo> cargoList = getStationByIndex(scenarioIndex, stationIndex).getSuppliesOnly(industries, cities);
        List<String> names = new ArrayList<>();
        for (Cargo c : cargoList) {
            names.add(c.toString());
        }
        return names;
    }

    public List<Cargo> getSelectedCargoList(List<Integer> selectedCargoIndices, List<Cargo> availableCargoList) {
        List<Cargo> selectedCargoList = new ArrayList<>();
        for (int index : selectedCargoIndices) {
            if (index >= 1 && index <= availableCargoList.size()) {
                selectedCargoList.add(availableCargoList.get(index - 1));
            }
        }

        return selectedCargoList;
    }

    public CargoMode getCargoModeByIndex(int index) {
        CargoMode[] modes = CargoMode.values();
        if (index < 1 || index > modes.length) {
            throw new IllegalArgumentException("Invalid cargo mode index.");
        }
        return modes[index - 1];
    }


    public List<String> getCargoModeNames() {
        List<String> modes = new ArrayList<>();
        for (CargoMode mode : CargoMode.values()) {
            modes.add(mode.name());
        }
        return modes;
    }


    public void createPointOfRoute(Station station, List<Cargo> cargoList, CargoMode cargoMode) {
        PointOfRoute pointOfRoute = Repositories.getInstance().getPointOfRouteRepository().createPointOfRoute(station, cargoList, cargoMode);
        Repositories.getInstance().getPointOfRouteRepository().addPointOfRoute(pointOfRoute);
        pointsOfRoute.add(pointOfRoute);
    }

    public List<PointOfRoute> getPointsOfRoute() {
        return new ArrayList<>(pointsOfRoute); // return a copy to protect the original list
    }


    public List<String> getAvailableLocomotiveNames(int scenarioIndex) {
        List<String> names = new ArrayList<>();
        for (Locomotive loco : getScenarioByIndex(scenarioIndex).getLocomotives()) {
            names.add(loco.toString());
        }
        return names;
    }

    public Locomotive getLocomotiveByIndex(int scenarioIndex, int index) {
        List<Locomotive> locos = getScenarioByIndex(scenarioIndex).getLocomotives();
        if (index < 1 || index > locos.size()) {
            throw new IllegalArgumentException("Invalid locomotive index.");
        }
        return locos.get(index - 1);
    }

    public void createRoute(Scenario scenario, Locomotive locomotive, List<PointOfRoute> pointsOfRoute) {
        Route route = Repositories.getInstance().getRouteRepository().createRoute(locomotive, pointsOfRoute);
        scenario.addRoute(route);
        Repositories.getInstance().getRouteRepository().addRoute(route);
    }
}
