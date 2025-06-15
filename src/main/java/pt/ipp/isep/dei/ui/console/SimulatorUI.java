package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SimulatorController;
import pt.ipp.isep.dei.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console interface to start and control a simulation.
 */
public class SimulatorUI {
    private final SimulatorController controller = new SimulatorController();

    /**
     * Starts the simulation based on user interaction.
     *
     * @param player player who will participate in the simulation
     */
    public void start(Player player) {
        Scanner scanner = new Scanner(System.in);

        // Get and list available scenarios
        List<String> scenarioDescriptions = controller.getAvailableScenarioDescriptions();
        if (scenarioDescriptions.isEmpty()) {
            System.out.println("No scenarios available.");
            return;
        }

        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarioDescriptions.size(); i++) {
            System.out.println((i + 1) + ". " + scenarioDescriptions.get(i));
        }

        // Select scenario
        System.out.print("Select scenario by number: ");
        int scenarioIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (!controller.setScenario(scenarioIndex)) {
            System.out.println("Invalid scenario selection.");
            return;
        }

        controller.setPlayer(player);

        // Get and list available routes
        List<String> routeDescriptions = controller.getAvailableRouteDescriptions();
        if (routeDescriptions.isEmpty()) {
            System.out.println("No routes available.");
            return;
        }

        System.out.println("Available Routes:");
        for (String routeDescription : routeDescriptions) {
            System.out.println(routeDescription);
        }

        // Select routes manually or leave blank to select all
        System.out.print("Select one or more routes by number (separated by space), or press Enter to select all: ");
        String line = scanner.nextLine().trim();

        String[] tokens = line.split("\\s+");
        List<Integer> selectedIndexes = new ArrayList<>();
        for (String token : tokens) {
            try {
                int idx = Integer.parseInt(token) - 1;
                selectedIndexes.add(idx);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input ignored: " + token);
            }
        }
        controller.selectRoutesByIndexes(selectedIndexes);

        // Start simulation
        controller.startSimulation();

        // Set number of years to simulate
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

        // Simulate month by month with option to stop
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
