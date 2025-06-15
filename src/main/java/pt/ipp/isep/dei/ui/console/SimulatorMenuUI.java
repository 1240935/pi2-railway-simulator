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
 * Console user interface to interact with the simulator.
 * <p>
 * Allows the player to create new simulations, check the current status,
 * continue existing simulations, and view financial reports.
 * Provides an interactive console menu to manage the simulation lifecycle.
 */
public class SimulatorMenuUI {

    private final SimulatorController controller = new SimulatorController();
    private final SimulatorRepository repository = Repositories.getInstance().getSimulatorRepository();
    private final Player player;

    /**
     * Constructor that associates the menu with a specific player.
     *
     * @param player the player who interacts with the simulator through this menu
     */
    public SimulatorMenuUI(Player player) {
        this.player = player;
    }

    /**
     * Starts the main simulator menu.
     * <p>
     * Presents available options to the user, including:
     * creating a new simulation, checking simulation status,
     * continuing existing simulations, viewing financial reports,
     * or returning to the main application menu.
     * <p>
     * This method keeps the menu active until the user chooses to exit (option 0).
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
            scanner.nextLine(); // Clear buffer

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
     * Displays the financial report of the active simulation.
     * <p>
     * If no active simulation exists, informs the user.
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
     * Allows creating a new simulation.
     * <p>
     * Lists available scenarios, allows the user to select one,
     * then lists routes available for the chosen scenario.
     * The user can select one or more routes.
     * Starts the simulation with the selected options and sets it as active in the repository.
     * <p>
     * After creation, automatically starts continuing the simulation.
     *
     * @param scanner Scanner to read user inputs
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

        // Scenario selection
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

        // Route selection
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
                // Select all routes
                for (int i = 0; i < routeDescriptions.size(); i++) {
                    selectedIndexes.add(i);
                }
                break;
            } else {
                // Validate manual selection
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

        // Start simulation
        controller.selectRoutesByIndexes(selectedIndexes);
        controller.startSimulation();
        repository.setActiveSimulator(controller.getSimulator());

        System.out.println("\nNew simulation started successfully!");

        // Continue simulation immediately after starting
        continueSimulation(scanner);
    }

    /**
     * Shows the current status of the active simulation,
     * including the current month, player budget, and any alerts.
     * <p>
     * If no active simulation exists, informs the user.
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
     * Allows the user to advance the active simulation by a specified number
     * of years, simulating month by month.
     * <p>
     * After each simulated month, asks the user if they wish to continue
     * the simulation.
     * <p>
     * Displays a summary of the simulation at the end.
     *
     * @param scanner Scanner to read user inputs
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

            // Ask user if they want to continue month-by-month
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
