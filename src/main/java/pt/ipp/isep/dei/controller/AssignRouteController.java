package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for assigning routes within a scenario.
 * Handles selection of scenarios, stations, cargo, cargo modes, locomotives, and construction of route points.
 */
public class AssignRouteController {

    // List to hold the current route points being constructed
    private final List<PointOfRoute> pointsOfRoute = new ArrayList<>();

    /**
     * Constructs an empty AssignRouteController.
     */
    public AssignRouteController() {
    }

    /**
     * Retrieves a list of available scenario names from the repository.
     *
     * @return list of scenario names
     */
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

    /**
     * Retrieves a Scenario object by index.
     *
     * @param scenarioIndex index of the scenario (1-based)
     * @return the corresponding Scenario object
     */
    public Scenario getScenarioByIndex(int scenarioIndex) {
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();
        if (scenarioIndex < 1 || scenarioIndex > scenarios.size()) {
            throw new IllegalArgumentException("Invalid scenario index.");
        }
        return scenarios.get(scenarioIndex - 1);
    }

    /**
     * Retrieves station names available in the selected scenario.
     *
     * @param scenarioIndex index of the selected scenario
     * @return list of station names
     */
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

    /**
     * Retrieves a station from a scenario by its index.
     *
     * @param scenarioIndex index of the scenario
     * @param stationIndex  index of the station
     * @return the corresponding Station object
     */
    public Station getStationByIndex(int scenarioIndex, int stationIndex) {
        List<Station> stations = getScenarioByIndex(scenarioIndex).getStations();
        if (stationIndex < 1 || stationIndex > stations.size()) {
            throw new IllegalArgumentException("Invalid station index.");
        }
        return stations.get(stationIndex - 1);
    }

    /**
     * Retrieves cargo names available for supply at a station within a scenario.
     *
     * @param scenarioIndex index of the scenario
     * @param stationIndex  index of the station
     * @return list of cargo names
     */
    public List<String> getAvailableCargoNames(int scenarioIndex, int stationIndex) {
        CargoListMapper cargoListMapper = new CargoListMapper();
        List<Industry> industries = getScenarioByIndex(scenarioIndex).getMap().getIndustries();
        List<City> cities = getScenarioByIndex(scenarioIndex).getMap().getCities();
        List<Cargo> cargoList = getStationByIndex(scenarioIndex, stationIndex).getSuppliesOnly(industries, cities);
        List<String> names = new ArrayList<>();
        List<CargoDTO> cargoDTOS = cargoListMapper.toDTOList(cargoList);
        for (CargoDTO cargoDTO : cargoDTOS) {
            names.add(cargoDTO.toString());
        }

        return names;
    }

    /**
     * Selects a list of cargo from available cargo using selected indices.
     *
     * @param selectedCargoIndices indices of selected cargo items
     * @param availableCargoList   list of available cargo
     * @return list of selected Cargo objects
     */
    public List<Cargo> getSelectedCargoList(List<Integer> selectedCargoIndices, List<Cargo> availableCargoList) {
        List<Cargo> selectedCargoList = new ArrayList<>();
        for (int index : selectedCargoIndices) {
            if (index >= 1 && index <= availableCargoList.size()) {
                selectedCargoList.add(availableCargoList.get(index - 1));
            }
        }

        return selectedCargoList;
    }

    /**
     * Retrieves a CargoMode by index.
     *
     * @param index index of the cargo mode
     * @return corresponding CargoMode
     */
    public CargoMode getCargoModeByIndex(int index) {
        List<CargoMode> modes = Repositories.getInstance().getCargoModeRepository().getCargoModes();
        if (index < 1 || index > modes.size()) {
            throw new IllegalArgumentException("Invalid cargo mode index.");
        }
        return modes.get(index - 1);
    }

    /**
     * Retrieves names of all available cargo modes.
     *
     * @return list of cargo mode names
     */
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

    /**
     * Creates and stores a new PointOfRoute.
     *
     * @param pointOfRouteDTO DTO containing PointOfRoute information
     */
    public void createPointOfRoute(PointOfRouteDTO pointOfRouteDTO) {
        PointsOfRouteMapper pointsOfRouteMapper = new PointsOfRouteMapper();
        PointOfRoute pointOfRoute = pointsOfRouteMapper.toPointOfRoute(pointOfRouteDTO);
        Repositories.getInstance().getPointOfRouteRepository().addPointOfRoute(pointOfRoute);
        pointsOfRoute.add(pointOfRoute);
    }

    /**
     * Returns a copy of the list of all created points of route.
     *
     * @return list of PointOfRoute objects
     */
    public List<PointOfRoute> getPointsOfRoute() {
        return new ArrayList<>(pointsOfRoute);
    }

    /**
     * Retrieves the names of all available locomotives in a scenario.
     *
     * @param scenarioIndex index of the scenario
     * @return list of locomotive model names
     */
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

    /**
     * Retrieves a Locomotive object by index within a scenario.
     *
     * @param scenarioIndex index of the scenario
     * @param index         index of the locomotive
     * @return the corresponding Locomotive object
     */
    public Locomotive getLocomotiveByIndex(int scenarioIndex, int index) {
        List<Locomotive> locos = getScenarioByIndex(scenarioIndex).getLocomotives();
        if (index < 1 || index > locos.size()) {
            throw new IllegalArgumentException("Invalid locomotive index.");
        }
        return locos.get(index - 1);
    }

    /**
     * Creates and stores a new Route object in the selected scenario and repository.
     *
     * @param routeDTO      DTO containing route data
     * @param scenarioIndex index of the scenario where the route is to be added
     */
    public void createRoute(RouteDTO routeDTO, int scenarioIndex) {
        RouteMapper routeMapper = new RouteMapper();
        Route route = routeMapper.toRoute(routeDTO);
        Scenario scenario = getScenarioByIndex(scenarioIndex);
        scenario.addRoute(route);
        Repositories.getInstance().getRouteRepository().addRoute(route);
    }
}
