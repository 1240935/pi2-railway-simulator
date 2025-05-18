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

    public SimulatorController() {
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void startSimulation() {
        if (selectedScenario == null) {
            throw new IllegalStateException("Scenario not selected");
        }
        if (player == null) {
            throw new IllegalStateException("Player not set");
        }
        if (selectedRoutes == null || selectedRoutes.isEmpty()) {
            selectedRoutes = selectedScenario.getRoutes();
        }
        this.simulator = new Simulator(selectedRoutes, player, selectedScenario);
    }

    // Returns route descriptions for UI display
    public List<String> getAvailableRouteDescriptions() {
        List<String> descriptions = new ArrayList<>();
        int index = 1;
        for (Route route : availableRoutes) {
            descriptions.add(index + ": " + route.toString());
            index++;
        }
        return descriptions;
    }

    /**
     * Retrieves all available scenarios from the repository.
     *
     * @return A list of all scenarios.
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Returns a list of descriptive strings representing each available scenario.
     * Each description contains the map name and the year of the scenario.
     *
     * @return List of scenario descriptions.
     */
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

            for (Route route : availableRoutes) {
                for (Station station : route.getRouteStations()) {
                    if (station.getSupplies() != null) {
                        for (Cargo supply : station.getSupplies()) {
                            supply.setQuantity(10);
                        }
                    }
                    if (station.getDemands() != null) {
                        for (Cargo demand : station.getDemands()) {
                            demand.setQuantity(10);
                        }
                    }
                }
            }

            return true;
        }
        return false;
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
                System.out.println("Invalid index ignored: " + (i + 1));
            }
        }

        this.selectedRoutes = selected;
    }

    public void simulateDay(int day) {
        if (simulator == null) {
            throw new IllegalStateException("Simulation not started. Call startSimulation() first.");
        }
        simulator.simulateDay(day);
    }

    public String getSimulationSummary() {
        if (simulator == null || player == null) {
            return "Simulation not completed.";
        }
        return simulator.getSimulationSummary(player);
    }
}
