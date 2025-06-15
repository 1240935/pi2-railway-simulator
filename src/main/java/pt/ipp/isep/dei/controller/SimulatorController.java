package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

public class SimulatorController {
    private Simulator simulator;
    private Player player;
    private Scenario selectedScenario;
    private List<Route> availableRoutes;
    private List<Route> selectedRoutes;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    public List<String> getAvailableScenarioDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Scenario s : getAvailableScenarios()) {
            descriptions.add("Map: " + s.getMap().getName() + ", Year: " + s.getYear());
        }
        return descriptions;
    }

    public boolean setScenario(int index) {
        List<Scenario> scenarios = getAvailableScenarios();
        if (index >= 0 && index < scenarios.size()) {
            this.selectedScenario = scenarios.get(index);
            this.availableRoutes = selectedScenario.getRoutes();

            // Reset quantities on supplies and demands for all stations on all routes
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

    public List<String> getAvailableRouteDescriptions() {
        List<String> descriptions = new ArrayList<>();
        if (availableRoutes == null) return descriptions;
        int index = 1; // If you want 1-based for UI, otherwise use 0-based
        for (Route route : availableRoutes) {
            descriptions.add(index + ": " + route.toString());
            index++;
        }
        return descriptions;
    }

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

    public void startSimulation() {
        if (selectedScenario == null) throw new IllegalStateException("Scenario not selected");
        if (player == null) throw new IllegalStateException("Player not set");
        if (selectedRoutes == null || selectedRoutes.isEmpty()) {
            selectedRoutes = selectedScenario.getRoutes();
        }
        this.simulator = new Simulator(selectedRoutes, player, selectedScenario);

        // Set active simulator in repository
        Repositories.getInstance().getSimulatorRepository().setActiveSimulator(this.simulator);
    }

    public void simulateMonths(int months) {
        if (simulator == null) {
            throw new IllegalStateException("Simulation not started. Call startSimulation() first.");
        }
        for (int i = 0; i < months; i++) {
            simulator.simulateMonth();
        }
    }

    // Since Simulator class does not implement getSimulationSummary(), return lastMonthSummary or stub message
    public String getSimulationSummary() {
        if (simulator == null) {
            return "Simulation not started.";
        }
        // You could extend Simulator with a getSimulationSummary() method if needed
        return simulator.getLastMonthSummary();
    }

    // The Simulator class doesn't have getCurrentSimulationDate() implemented yet - stub here
    public String getCurrentSimulationDate() {
        if (simulator == null) return "Simulation not started.";
        // Could return something like: "Month X, Year Y"
        return "Month " + simulator.getCurrentMonth(); // You would need to add getCurrentMonth() getter to Simulator
    }

    public String getCurrentMonthSummary() {
        if (simulator == null) {
            return "Simulation not started.";
        }
        return simulator.getLastMonthSummary();
    }

    public Simulator getSimulator() {
        return simulator;
    }

    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
        this.player = simulator.getPlayer();
        this.selectedScenario = simulator.getScenario();
        this.selectedRoutes = simulator.getRoutes();
    }
}
