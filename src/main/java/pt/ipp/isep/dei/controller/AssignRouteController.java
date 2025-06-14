package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

public class AssignRouteController {

    private final List<PointOfRoute> pointsOfRoute = new ArrayList<>();

    public AssignRouteController() {
    }

    public List<String> getAvailableScenarioNames() {
        ScenarioListMapper scenarioListMapper = new ScenarioListMapper();
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();
        List<ScenarioDTO> scenarioDTOS = scenarioListMapper.toDTOList(scenarios);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < scenarioDTOS.size(); i++) {
            names.add(scenarioDTOS.get(i).toString());
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
        StationListMapper stationListMapper = new StationListMapper();
        Scenario scenario = getScenarioByIndex(scenarioIndex);
        List<Station> stations = scenario.getStations();
        List<StationDTO> stationDTOS = stationListMapper.toDTOList(stations);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < stationDTOS.size(); i++) {
            names.add(stationDTOS.get(i).toString());
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
        CargoListMapper cargoListMapper = new CargoListMapper();
        List<Industry> industries = getScenarioByIndex(scenarioIndex).getMap().getIndustries();
        List<City> cities = getScenarioByIndex(scenarioIndex).getMap().getCities();
        List<Cargo> cargoList = getStationByIndex(scenarioIndex, stationIndex).getSuppliesOnly(industries, cities);
        List<String> names = new ArrayList<>();
        List<CargoDTO> cargoDTOS = cargoListMapper.toDTOList(cargoList);
        for (int i = 0; i < cargoDTOS.size(); i++) {
            names.add(cargoDTOS.get(i).toString());
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
        List<CargoMode> modes = Repositories.getInstance().getCargoModeRepository().getCargoModes();
        if (index < 1 || index > modes.size()) {
            throw new IllegalArgumentException("Invalid cargo mode index.");
        }
        return modes.get(index - 1);
    }


    public List<String> getCargoModeNames() {
        CargoModeListMapper cargoModeListMapper = new CargoModeListMapper();
        List<CargoMode> modes = Repositories.getInstance().getCargoModeRepository().getCargoModes();
        List<String> names = new ArrayList<>();
        List<CargoModeDTO> cargoModeDTOS = cargoModeListMapper.toDTOList(modes);
        for (CargoModeDTO mode : cargoModeDTOS) {
            names.add(mode.getName());
        }
        return names;
    }


    public void createPointOfRoute(PointOfRouteDTO pointOfRouteDTO) {
        PointsOfRouteMapper pointsOfRouteMapper = new PointsOfRouteMapper();
        PointOfRoute pointOfRoute = pointsOfRouteMapper.toPointOfRoute(pointOfRouteDTO);
        Repositories.getInstance().getPointOfRouteRepository().addPointOfRoute(pointOfRoute);
        pointsOfRoute.add(pointOfRoute);
    }

    public List<PointOfRoute> getPointsOfRoute() {
        return new ArrayList<>(pointsOfRoute);
    }


    public List<String> getAvailableLocomotiveNames(int scenarioIndex) {
        List<String> names = new ArrayList<>();
        LocomotiveListMapper locomotiveListMapper = new LocomotiveListMapper();
        List<Locomotive> locomotives = getScenarioByIndex(scenarioIndex).getLocomotives();
        List<LocomotiveDTO> locomotiveDTOS = locomotiveListMapper.toDTOList(locomotives);

        for (LocomotiveDTO locomotive : locomotiveDTOS) {
            names.add(locomotive.getLocomotiveModel().name());
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

    public void createRoute(RouteDTO routeDTO, int scenarioIndex) {
        RouteMapper routeMapper = new RouteMapper();
        Route route = routeMapper.toRoute(routeDTO);
        Scenario scenario = getScenarioByIndex(scenarioIndex);
        scenario.addRoute(route);
        Repositories.getInstance().getRouteRepository().addRoute(route);
    }
}
