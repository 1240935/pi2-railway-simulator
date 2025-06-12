//package pt.ipp.isep.dei.controller;
//
//import pt.ipp.isep.dei.domain.*;
//import pt.ipp.isep.dei.repository.Repositories;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Controller class responsible for managing the simulation process within the application.
// *
// * <p>This controller manages selection of scenarios, routes, and the player participating in
// * the simulation. It initializes the Simulator domain class and provides methods to start
// * the simulation, select routes, simulate individual days, and retrieve simulation results.</p>
// */
//public class SimulatorController {
//
//    /**
//     * The Simulator instance handling the actual simulation logic.
//     */
//    private Simulator simulator;
//
//    /**
//     * The Player participating in the simulation.
//     */
//    private Player player;
//
//    /**
//     * The Scenario selected for the simulation.
//     */
//    private Scenario selectedScenario;
//
//    /**
//     * List of routes available in the selected scenario.
//     */
//    private List<Route> availableRoutes;
//
//    /**
//     * List of routes selected by the player for simulation.
//     */
//    private List<Route> selectedRoutes;
//
//    /**
//     * Default constructor.
//     */
//    public SimulatorController() {
//    }
//
//    /**
//     * Sets the player who will participate in the simulation.
//     *
//     * @param player the Player instance
//     */
//    public void setPlayer(Player player) {
//        this.player = player;
//    }
//
//    /**
//     * Starts the simulation with the selected scenario, player, and selected routes.
//     *
//     * <p>If no routes have been selected, all available routes from the scenario are simulated.</p>
//     *
//     * @throws IllegalStateException if scenario or player has not been set before starting simulation
//     */
//    public void startSimulation() {
//        if (selectedScenario == null) {
//            throw new IllegalStateException("Scenario not selected");
//        }
//        if (player == null) {
//            throw new IllegalStateException("Player not set");
//        }
//        if (selectedRoutes == null || selectedRoutes.isEmpty()) {
//            selectedRoutes = selectedScenario.getRoutes();
//        }
//        this.simulator = new Simulator(selectedRoutes, player, selectedScenario);
//    }
//
//    /**
//     * Returns a list of descriptive strings for all available routes.
//     * Each description is prefixed by an index starting at 1.
//     *
//     * @return list of route descriptions for UI display
//     */
//    public List<String> getAvailableRouteDescriptions() {
//        List<String> descriptions = new ArrayList<>();
//        int index = 1;
//        for (Route route : availableRoutes) {
//            descriptions.add(index + ": " + route.toString());
//            index++;
//        }
//        return descriptions;
//    }
//
//    /**
//     * Retrieves all scenarios available in the system.
//     *
//     * @return list of all scenarios from the repository
//     */
//    public List<Scenario> getAvailableScenarios() {
//        return Repositories.getInstance().getScenarioRepository().getScenarios();
//    }
//
//    /**
//     * Returns a list of descriptive strings for all available scenarios.
//     * Each string contains the map name and the year of the scenario.
//     *
//     * @return list of scenario descriptions
//     */
//    public List<String> getAvailableScenarioDescriptions() {
//        List<String> descriptions = new ArrayList<>();
//        for (Scenario s : getAvailableScenarios()) {
//            descriptions.add("Map: " + s.getMap().getName() + ", Year: " + s.getYear());
//        }
//        return descriptions;
//    }
//
//    /**
//     * Selects a scenario by its index in the available scenarios list.
//     * Also initializes the list of available routes in the selected scenario,
//     * setting default quantities of supplies and demands in each station to 10.
//     *
//     * @param index the index of the scenario to select
//     * @return true if scenario was selected successfully; false if index invalid
//     */
//    public boolean setScenario(int index) {
//        List<Scenario> scenarios = getAvailableScenarios();
//        if (index >= 0 && index < scenarios.size()) {
//            this.selectedScenario = scenarios.get(index);
//            this.availableRoutes = selectedScenario.getRoutes();
//
//            // Set default quantities for supplies and demands in each station on the routes
//            for (Route route : availableRoutes) {
//                for (PointOfRoute point : route.getPointsOfRoute()) {
//                    if (point.getStation().getSupplies() != null) {
//                        for (Cargo supply : point.getStation().getSupplies()) {
//                            supply.setQuantity(10);
//                        }
//                    }
//                    if (point.getStation().getDemands() != null) {
//                        for (Cargo demand : point.getStation().getDemands()) {
//                            demand.setQuantity(10);
//                        }
//                    }
//                }
//            }
//
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Selects multiple routes for simulation by their indexes.
//     *
//     * <p>Indexes outside the valid range are ignored and a warning is printed to the console.</p>
//     *
//     * @param indexes list of route indexes to select
//     */
//    public void selectRoutesByIndexes(List<Integer> indexes) {
//        if (availableRoutes == null || availableRoutes.isEmpty()) {
//            System.out.println("No available routes to select from.");
//            return;
//        }
//
//        List<Route> selected = new ArrayList<>();
//        for (int i : indexes) {
//            if (i >= 0 && i < availableRoutes.size()) {
//                selected.add(availableRoutes.get(i));
//            } else {
//                System.out.println("Invalid index ignored: " + (i + 1));
//            }
//        }
//
//        this.selectedRoutes = selected;
//    }
//
//    /**
//     * Simulates a single day in the simulation.
//     *
//     * @param day the day number to simulate
//     * @throws IllegalStateException if simulation has not been started yet
//     */
//    public void simulateDay(int day) {
//        if (simulator == null) {
//            throw new IllegalStateException("Simulation not started. Call startSimulation() first.");
//        }
//        simulator.simulateDay(day);
//    }
//
//    /**
//     * Returns a summary of the simulation results for the current player.
//     *
//     * @return simulation summary string or a message indicating simulation not completed
//     */
//    public String getSimulationSummary() {
//        if (simulator == null || player == null) {
//            return "Simulation not completed.";
//        }
//        return simulator.getSimulationSummary(player);
//    }
//}
