package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SimulatorController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Simulator;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.SimulatorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de utilizador via consola para interagir com a simulação.
 */
public class SimulatorMenuUI {

    private final SimulatorController controller = new SimulatorController();
    private final SimulatorRepository repository = Repositories.getInstance().getSimulatorRepository();
    private final Player player;

    /**
     * Construtor que associa o menu a um jogador.
     */
    public SimulatorMenuUI(Player player) {
        this.player = player;
    }

    /**
     * Inicia o menu da simulação e trata as opções do utilizador.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Simulator Menu ===");
            System.out.println("1. Create New Simulation");
            System.out.println("2. Check Simulation Status");
            System.out.println("3. Continue Existing Simulation");
            System.out.println("4. View Financial Report");
            System.out.println("0. Back to Main Menu");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (choice) {
                case 1 -> createNewSimulation(scanner);
                case 2 -> checkSimulationStatus();
                case 3 -> continueSimulation(scanner);
                case 4 -> showFinancialReport();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Apresenta o relatório financeiro da simulação ativa, se existir.
     */
    private void showFinancialReport() {
        Simulator activeSimulator = repository.getActiveSimulator();
        if (activeSimulator == null) {
            System.out.println("No active simulation.");
            return;
        }

        System.out.println(activeSimulator.generateFinancialReport());
    }

    /**
     * Cria uma nova simulação: escolhe cenário, rotas, e inicia.
     */
    private void createNewSimulation(Scanner scanner) {
        List<String> scenarioDescriptions = controller.getAvailableScenarioDescriptions();
        if (scenarioDescriptions.isEmpty()) {
            System.out.println("No scenarios available at the moment.");
            return;
        }

        System.out.println("\nAvailable Scenarios:");
        for (int i = 0; i < scenarioDescriptions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, scenarioDescriptions.get(i));
        }

        // Seleção do cenário
        int scenarioIndex = -1;
        while (true) {
            System.out.print("Select scenario by number: ");
            String input = scanner.nextLine().trim();
            try {
                scenarioIndex = Integer.parseInt(input) - 1;
                if (scenarioIndex < 0 || scenarioIndex >= scenarioDescriptions.size()) {
                    System.out.println("Invalid scenario number, please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid scenario number.");
            }
        }

        if (!controller.setScenario(scenarioIndex)) {
            System.out.println("Failed to set the selected scenario.");
            return;
        }

        controller.setPlayer(player);

        // Seleção das rotas
        List<String> routeDescriptions = controller.getAvailableRouteDescriptions();
        if (routeDescriptions.isEmpty()) {
            System.out.println("No routes available for the selected scenario.");
            return;
        }

        System.out.println("\nAvailable Routes:");
        for (int i = 0; i < routeDescriptions.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, routeDescriptions.get(i));
        }

        System.out.println("Select one or more routes by entering their numbers separated by spaces.");
        System.out.println("Or just press Enter to select all routes.");

        List<Integer> selectedIndexes = new ArrayList<>();
        while (true) {
            System.out.print("Enter route numbers or press Enter: ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                // Seleciona todas as rotas
                for (int i = 0; i < routeDescriptions.size(); i++) {
                    selectedIndexes.add(i);
                }
                break;
            } else {
                // Valida seleção manual
                String[] tokens = line.split("\\s+");
                boolean allValid = true;
                selectedIndexes.clear();
                for (String token : tokens) {
                    try {
                        int idx = Integer.parseInt(token) - 1;
                        if (idx < 0 || idx >= routeDescriptions.size()) {
                            System.out.printf("Route number %s is out of range.%n", token);
                            allValid = false;
                            break;
                        }
                        if (!selectedIndexes.contains(idx)) {
                            selectedIndexes.add(idx);
                        }
                    } catch (NumberFormatException e) {
                        System.out.printf("Invalid route number ignored: %s%n", token);
                        allValid = false;
                        break;
                    }
                }
                if (allValid) break;
                else System.out.println("Please enter valid route numbers.");
            }
        }

        // Inicia simulação
        controller.selectRoutesByIndexes(selectedIndexes);
        controller.startSimulation();
        repository.setActiveSimulator(controller.getSimulator());

        System.out.println("\nNew simulation started successfully!");

        // Continua logo após iniciar
        continueSimulation(scanner);
    }

    /**
     * Mostra o estado atual da simulação (mês, orçamento, alertas).
     */
    private void checkSimulationStatus() {
        Simulator activeSimulator = repository.getActiveSimulator();
        if (activeSimulator == null) {
            System.out.println("No active simulation currently.");
            return;
        }

        System.out.println("\n=== Simulation Status ===");
        System.out.println("Current Month: " + activeSimulator.getCurrentMonth());
        System.out.println("Player Budget: " + activeSimulator.getPlayer().getBudget());

        if (!activeSimulator.getAlerts().isEmpty()) {
            System.out.println("\nAlerts:");
            for (String alert : activeSimulator.getAlerts()) {
                System.out.println("- " + alert);
            }
        } else {
            System.out.println("No alerts at this time.");
        }
    }

    /**
     * Permite simular vários meses da simulação ativa.
     */
    private void continueSimulation(Scanner scanner) {
        Simulator activeSimulator = repository.getActiveSimulator();
        if (activeSimulator == null) {
            System.out.println("No active simulation to continue.");
            return;
        }
        controller.setSimulator(activeSimulator);

        int years;
        while (true) {
            System.out.print("Enter number of years to simulate (positive integer): ");
            String input = scanner.nextLine().trim();
            try {
                years = Integer.parseInt(input);
                if (years <= 0) {
                    System.out.println("Please enter a positive integer.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number of years.");
            }
        }

        int totalMonths = years * 12;
        System.out.printf("Starting simulation for %d years (%d months).%n", years, totalMonths);

        for (int month = 1; month <= totalMonths; month++) {
            controller.simulateMonths(1);
            System.out.printf("Simulated month %d/%d.%n", month, totalMonths);

            // Pergunta ao utilizador se quer continuar mês a mês
            String answer;
            while (true) {
                System.out.print("Continue simulation? (y/n): ");
                answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("y") || answer.equals("n")) {
                    break;
                }
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
            if (answer.equals("n")) {
                break;
            }
        }

        System.out.println("\nSimulation finished.");
        System.out.println(controller.getSimulationSummary());
    }
}
