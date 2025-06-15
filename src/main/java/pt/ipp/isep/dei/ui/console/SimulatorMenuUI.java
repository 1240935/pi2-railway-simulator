package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SimulatorController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Simulator;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.SimulatorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimulatorMenuUI {

    private final SimulatorController controller = new SimulatorController();
    private final SimulatorRepository repository = Repositories.getInstance().getSimulatorRepository();
    private final Player player;

    public SimulatorMenuUI(Player player) {
        this.player = player;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Simulator Menu ===");
            System.out.println("1. Create New Simulation");
            System.out.println("2. Check Simulation Status");
            System.out.println("3. Continue Existing Simulation");
            System.out.println("0. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createNewSimulation(scanner);
                    break;
                case 2:
                    checkSimulationStatus();
                    break;
                case 3:
                    continueSimulation(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createNewSimulation(Scanner scanner) {
        // Configurar cenário
        List<String> scenarioDescriptions = controller.getAvailableScenarioDescriptions();
        if (scenarioDescriptions.isEmpty()) {
            System.out.println("No scenarios available.");
            return;
        }

        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarioDescriptions.size(); i++) {
            System.out.println((i + 1) + ". " + scenarioDescriptions.get(i));
        }

        System.out.print("Select scenario by number: ");
        int scenarioIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (!controller.setScenario(scenarioIndex)) {
            System.out.println("Invalid scenario selection.");
            return;
        }

        controller.setPlayer(player);

        // Configurar rotas
        List<String> routeDescriptions = controller.getAvailableRouteDescriptions();
        if (routeDescriptions.isEmpty()) {
            System.out.println("No routes available.");
            return;
        }

        System.out.println("Available Routes:");
        for (int i = 0; i < routeDescriptions.size(); i++) {
            System.out.println((i + 1) + ". " + routeDescriptions.get(i));
        }

        System.out.print("Select one or more routes by number (separated by space), or press Enter to select all: ");
        String line = scanner.nextLine().trim();

        List<Integer> selectedIndexes = new ArrayList<>();
        if (!line.isEmpty()) {
            String[] tokens = line.split("\\s+");
            for (String token : tokens) {
                try {
                    int idx = Integer.parseInt(token) - 1;
                    selectedIndexes.add(idx);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input ignored: " + token);
                }
            }
        } else {
            // Selecionar todas as rotas
            for (int i = 0; i < routeDescriptions.size(); i++) {
                selectedIndexes.add(i);
            }
        }
        controller.selectRoutesByIndexes(selectedIndexes);

        // Iniciar nova simulação
        controller.startSimulation();
        repository.setActiveSimulator(controller.getSimulator());

        System.out.println("\nNew simulation started successfully!");

        // Simular meses imediatamente
        continueSimulation(scanner);
    }

    private void checkSimulationStatus() {
        Simulator activeSimulator = repository.getActiveSimulator();
        if (activeSimulator == null) {
            System.out.println("No active simulation.");
            return;
        }

        System.out.println("\n=== Simulation Status ===");
        System.out.println("Current Month: " + activeSimulator.getCurrentMonth());
        System.out.println("Player Budget: " + activeSimulator.getPlayer().getBudget());

        // Mostrar alertas se houver
        if (!activeSimulator.getAlerts().isEmpty()) {
            System.out.println("\nAlerts:");
            for (String alert : activeSimulator.getAlerts()) {
                System.out.println("- " + alert);
            }
        }
    }

    private void continueSimulation(Scanner scanner) {
        Simulator activeSimulator = repository.getActiveSimulator();
        if (activeSimulator == null) {
            System.out.println("No active simulation to continue.");
            return;
        }
        controller.startSimulation();

        System.out.print("Enter number of years to simulate: ");
        int years;
        try {
            years = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of years.");
            return;
        }

        int totalMonths = years * 12;

        System.out.println("Simulating " + years + " years (" + totalMonths + " months)...");

        for (int month = 1; month <= totalMonths; month++) {
            controller.simulateMonths(1);

            System.out.print("Continue simulation? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (!answer.equals("y")) {
                break;
            }
        }

        System.out.println("Simulation finished.");
        System.out.println(controller.getSimulationSummary());
    }
}