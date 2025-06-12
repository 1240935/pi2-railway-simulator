//package pt.ipp.isep.dei.ui.console;
//
//import pt.ipp.isep.dei.controller.SimulatorController;
//import pt.ipp.isep.dei.domain.Player;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * Console-based User Interface for the Simulator feature.
// *
// * Allows a player to:
// *  - Select a scenario from the available scenarios
// *  - Choose one or more routes within the scenario to simulate
// *  - Specify the number of simulation days
// *  - Step through the simulation day by day, with option to stop early
// *  - View a summary of the simulation results
// */
//public class SimulatorUI {
//    private final SimulatorController controller = new SimulatorController();
//
//    /**
//     * Starts the simulation interaction with the specified player.
//     * Guides the player through scenario selection, route selection,
//     * simulation execution, and displaying results.
//     *
//     * @param player the player who is running the simulation
//     */
//    public void start(Player player) {
//        Scanner scanner = new Scanner(System.in);
//
//        // Get and display available scenarios
//        List<String> scenarioDescriptions = controller.getAvailableScenarioDescriptions();
//        if (scenarioDescriptions.isEmpty()) {
//            System.out.println("No scenarios available.");
//            return;
//        }
//
//        System.out.println("Available Scenarios:");
//        for (int i = 0; i < scenarioDescriptions.size(); i++) {
//            System.out.println((i + 1) + ". " + scenarioDescriptions.get(i));
//        }
//
//        // User selects a scenario by number
//        System.out.print("Select scenario by number: ");
//        int scenarioIndex = scanner.nextInt() - 1;
//        scanner.nextLine();
//
//        if (scenarioIndex < 0 || scenarioIndex >= scenarioDescriptions.size()) {
//            System.out.println("Invalid scenario selection.");
//            return;
//        }
//
//        // Set scenario and player in the controller
//        controller.setScenario(scenarioIndex);
//        controller.setPlayer(player);
//
//        // Initialize simulation and load available routes for the scenario
//        controller.startSimulation();
//
//        // Display available routes
//        List<String> routeDescriptions = controller.getAvailableRouteDescriptions();
//        if (routeDescriptions.isEmpty()) {
//            System.out.println("No routes available.");
//            return;
//        }
//
//        System.out.println("Routes:");
//        for (int i = 0; i < routeDescriptions.size(); i++) {
//            System.out.println((i + 1) + ". " + routeDescriptions.get(i));
//        }
//
//        // User selects one or more routes by numbers separated by spaces
//        System.out.print("Select one or more routes by number (separated by space): ");
//        String[] input = scanner.nextLine().split(" ");
//        List<Integer> selectedIndexes = new ArrayList<>();
//
//        for (String s : input) {
//            try {
//                int index = Integer.parseInt(s.trim()) - 1;
//                if (index >= 0 && index < routeDescriptions.size()) {
//                    selectedIndexes.add(index);
//                } else {
//                    System.out.println("Invalid route number: " + (index + 1));
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input: " + s);
//            }
//        }
//
//        if (selectedIndexes.isEmpty()) {
//            System.out.println("No valid routes selected.");
//            return;
//        }
//
//        controller.selectRoutesByIndexes(selectedIndexes);
//
//        // Ask user for number of days to simulate
//        System.out.print("Enter number of days to simulate: ");
//        int maxDays;
//        try {
//            maxDays = Integer.parseInt(scanner.nextLine().trim());
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid number of days.");
//            return;
//        }
//
//        boolean stopSimulation = false;
//
//        // Simulate day by day, with option to stop after each day
//        for (int day = 1; day <= maxDays && !stopSimulation; day++) {
//            controller.simulateDay(day);
//
//            System.out.print("Continue simulation? (y/n): ");
//            String decision = scanner.nextLine().trim().toLowerCase();
//
//            if (!decision.equals("y")) {
//                stopSimulation = true;
//                System.out.println("Simulation stopped by user.");
//            }
//        }
//
//        // End simulation and display summary
//        System.out.println("Simulation ended.");
//        System.out.println("\n" + controller.getSimulationSummary());
//    }
//}
