package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SimulatorController;
import pt.ipp.isep.dei.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimulatorUI {
    private final SimulatorController controller = new SimulatorController();

    public void start(Player player) {
        Scanner scanner = new Scanner(System.in);

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

        if (scenarioIndex < 0 || scenarioIndex >= scenarioDescriptions.size()) {
            System.out.println("Invalid scenario selection.");
            return;
        }

        // Set the selected scenario in the controller
        controller.setScenario(scenarioIndex);

        controller.setPlayer(player);

        // Start the simulation to load available routes for the scenario
        controller.startSimulation();

        // List available routes
        List<String> routeDescriptions = controller.getAvailableRouteDescriptions();
        if (routeDescriptions.isEmpty()) {
            System.out.println("No routes available.");
            return;
        }

        System.out.println("Routes:");
        for (int i = 0; i < routeDescriptions.size(); i++) {
            System.out.println((i + 1) + ". " + routeDescriptions.get(i));
        }

        System.out.print("Select one or more routes by number (separated by space): ");
        String[] input = scanner.nextLine().split(" ");
        List<Integer> selectedIndexes = new ArrayList<>();

        for (String s : input) {
            try {
                int index = Integer.parseInt(s.trim()) - 1;
                if (index >= 0 && index < routeDescriptions.size()) {
                    selectedIndexes.add(index);
                } else {
                    System.out.println("Invalid route number: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + s);
            }
        }

        if (selectedIndexes.isEmpty()) {
            System.out.println("No valid routes selected.");
            return;
        }

        controller.selectRoutesByIndexes(selectedIndexes);

        System.out.print("Enter number of days to simulate: ");
        int maxDays;
        try {
            maxDays = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of days.");
            return;
        }

        boolean status = false;

        for (int day = 1; day <= maxDays && !status; day++) {
            controller.simulateDay(day);

            System.out.print("Continue simulation? (y/n): ");
            String decision = scanner.nextLine().trim().toLowerCase();

            if (!decision.equals("y")) {
                status = true;
                System.out.println("Simulation stopped by user.");
            }
        }

        System.out.println("Simulation ended.");
        System.out.println("\n" + controller.getSimulationSummary());
    }
}
