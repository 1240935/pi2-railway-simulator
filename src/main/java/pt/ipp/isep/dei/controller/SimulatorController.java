package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for managing the configuration and execution of simulations.
 */
public class SimulatorController {
    private Simulator simulator;
    private Player player;
    private Scenario selectedScenario;
    private List<Route> availableRoutes;
    private List<Route> selectedRoutes;

    /**
     * Sets the player for the simulation.
     *
     * @param player the player object
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Retrieves all available scenarios from the repository.
     *
     * @return list of available scenarios
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Returns a list of descriptions for the available scenarios.
     *
     * @return list of scenario descriptions
     */
    public List<String> getAvailableScenarioDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Scenario s : getAvailableScenarios()) {
            descriptions.add("Map: " + s.getMap().getName() + ", Year: " + s.getYear());
        }
        return descriptions;
    }

    /**
     * Sets the scenario to be used in the simulation based on the given index.
     * Also resets the cargo quantities at all stations in the scenario's routes.
     *
     * @param index the index of the scenario to select
     * @return true if the scenario was successfully set; false otherwise
     */
    public boolean setScenario(int index) {
        List<Scenario> scenarios = getAvailableScenarios();
        if (index >= 0 && index < scenarios.size()) {
            this.selectedScenario = scenarios.get(index);
            this.availableRoutes = selectedScenario.getRoutes();

            // Reset cargo quantities for all stations in the routes
            for (Route route : availableRoutes) {
                for (PointOfRoute point : route.getPointsOfRoute()) {
                    if (point.getStation().getSupplies() != null) {
                        for (Cargo supply : point.getStation().getSupplies()) {
                            supply.setQuantity(0);
                        }
                    }
                    if (point.getStation().getDemands() != null) {
                        for (Cargo demand : point.getStation().getDemands()) {
                            demand.setQuantity(0);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a list of descriptions for the available routes in the selected scenario.
     *
     * @return list of route descriptions
     */
    public List<String> getAvailableRouteDescriptions() {
        List<String> descriptions = new ArrayList<>();
        if (availableRoutes == null) return descriptions;
        int index = 1;
        for (Route route : availableRoutes) {
            descriptions.add(index + ": " + route.toString());
            index++;
        }
        return descriptions;
    }

    /**
     * Selects routes based on the provided list of indexes.
     *
     * @param indexes list of indexes corresponding to routes to select
     */
    public void selectRoutesByIndexes(List<Integer> indexes) {
        if (availableRoutes == null || availableRoutes.isEmpty()) {
            System.out.println("No available routes to select from.");
            return;
        }
        List<Route> selected = new ArrayList<>();
        for (int i : indexes) {
            if (i >= 0 && i < availableRoutes.size()) {
                selected.add(availableRoutes.get(i));
            } else {
                System.out.println("Invalid index ignored: " + i);
            }
        }
        this.selectedRoutes = selected;
    }

    /**
     * Starts the simulation using the defined player, scenario, and selected routes.
     *
     * @throws IllegalStateException if scenario or player are not set
     */
    public void startSimulation() {
        if (selectedScenario == null) throw new IllegalStateException("Scenario not selected");
        if (player == null) throw new IllegalStateException("Player not set");
        if (selectedRoutes == null || selectedRoutes.isEmpty()) {
            selectedRoutes = selectedScenario.getRoutes();
        }
        this.simulator = new Simulator(selectedRoutes, player, selectedScenario);

        // Save the simulator as active in the repository
        Repositories.getInstance().getSimulatorRepository().setActiveSimulator(this.simulator);
    }

    /**
     * Simulates the specified number of months.
     *
     * @param months number of months to simulate
     * @throws IllegalStateException if the simulation has not been started
     */
    public void simulateMonths(int months) {
        if (simulator == null) {
            throw new IllegalStateException("Simulation not started. Call startSimulation() first.");
        }
        for (int i = 0; i < months; i++) {
            simulator.simulateMonth();
        }
    }

    /**
     * Returns the summary of the last simulated month.
     *
     * @return summary string of the last month simulated
     */
    public String getSimulationSummary() {
        if (simulator == null) {
            return "Simulation not started.";
        }
        return simulator.getLastMonthSummary();
    }

    /**
     * Returns the current date of the simulation in textual format.
     *
     * @return current simulation date string
     */
    public String getCurrentSimulationDate() {
        if (simulator == null) return "Simulation not started.";
        return "Month " + simulator.getCurrentMonth();
    }

    /**
     * Returns the summary of the current simulation month.
     *
     * @return current month summary string
     */
    public String getCurrentMonthSummary() {
        if (simulator == null) {
            return "Simulation not started.";
        }
        return simulator.getLastMonthSummary();
    }

    /**
     * Returns the current Simulator instance.
     *
     * @return the current simulator
     */
    public Simulator getSimulator() {
        return simulator;
    }

    /**
     * Sets the current Simulator and updates the player, scenario, and selected routes accordingly.
     *
     * @param simulator the simulator instance to set
     */
    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
        this.player = simulator.getPlayer();
        this.selectedScenario = simulator.getScenario();
        this.selectedRoutes = simulator.getRoutes();
    }
}
