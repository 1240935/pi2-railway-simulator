package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador responsável por gerir a configuração e execução de simulações.
 */
public class SimulatorController {
    private Simulator simulator;
    private Player player;
    private Scenario selectedScenario;
    private List<Route> availableRoutes;
    private List<Route> selectedRoutes;

    /**
     * Define o jogador para a simulação.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Obtém todos os cenários disponíveis no repositório.
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Retorna uma lista de descrições dos cenários disponíveis.
     */
    public List<String> getAvailableScenarioDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Scenario s : getAvailableScenarios()) {
            descriptions.add("Map: " + s.getMap().getName() + ", Year: " + s.getYear());
        }
        return descriptions;
    }

    /**
     * Define o cenário a ser usado na simulação com base no índice.
     * Também reinicia as quantidades de carga em todas as estações.
     */
    public boolean setScenario(int index) {
        List<Scenario> scenarios = getAvailableScenarios();
        if (index >= 0 && index < scenarios.size()) {
            this.selectedScenario = scenarios.get(index);
            this.availableRoutes = selectedScenario.getRoutes();

            // Reinicia quantidades de carga para todas as estações nas rotas
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
     * Retorna uma lista de descrições das rotas disponíveis no cenário selecionado.
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
     * Seleciona rotas com base nos índices fornecidos.
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
     * Inicia a simulação com o jogador, cenário e rotas definidos.
     */
    public void startSimulation() {
        if (selectedScenario == null) throw new IllegalStateException("Scenario not selected");
        if (player == null) throw new IllegalStateException("Player not set");
        if (selectedRoutes == null || selectedRoutes.isEmpty()) {
            selectedRoutes = selectedScenario.getRoutes();
        }
        this.simulator = new Simulator(selectedRoutes, player, selectedScenario);

        // Guarda o simulador como o ativo no repositório
        Repositories.getInstance().getSimulatorRepository().setActiveSimulator(this.simulator);
    }

    /**
     * Simula a quantidade especificada de meses.
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
     * Retorna o resumo do último mês simulado.
     */
    public String getSimulationSummary() {
        if (simulator == null) {
            return "Simulation not started.";
        }
        return simulator.getLastMonthSummary();
    }

    /**
     * Retorna a data atual da simulação (formato textual).
     */
    public String getCurrentSimulationDate() {
        if (simulator == null) return "Simulation not started.";
        return "Month " + simulator.getCurrentMonth();
    }

    /**
     * Retorna o resumo do mês atual da simulação.
     */
    public String getCurrentMonthSummary() {
        if (simulator == null) {
            return "Simulation not started.";
        }
        return simulator.getLastMonthSummary();
    }

    /**
     * Retorna o simulador atual.
     */
    public Simulator getSimulator() {
        return simulator;
    }

    /**
     * Define o simulador atual e atualiza jogador, cenário e rotas a partir dele.
     */
    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
        this.player = simulator.getPlayer();
        this.selectedScenario = simulator.getScenario();
        this.selectedRoutes = simulator.getRoutes();
    }
}
