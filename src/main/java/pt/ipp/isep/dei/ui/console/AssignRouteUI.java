package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AssignRouteController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based User Interface for assigning routes to locomotives
 * in different scenarios. This class interacts with the user through
 * the console to gather input on scenario selection, locomotive choice,
 * route stations, optional intermediate stops, and cargo selection,
 * then delegates the operations to the AssignRouteController.
 */
public class AssignRouteUI {
    private final AssignRouteController controller;
    private final Scanner scanner;

    /**
     * Constructs a new AssignRouteUI instance,
     * initializing the controller and input scanner.
     */
    public AssignRouteUI() {
        controller = new AssignRouteController();
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the user interaction for assigning a route.
     * Guides the user through:
     *  - selecting a scenario,
     *  - choosing a locomotive,
     *  - selecting start and end stations,
     *  - optionally adding intermediate stops,
     *  - selecting cargo to supply,
     * and finally creates the route through the controller.
     */
    public void start() {
        // Display available scenarios and get user selection
        List<String> scenarioNames = controller.getAvailableScenarioNames();
        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println((i + 1) + ": " + scenarioNames.get(i));
        }
        System.out.print("Enter the scenario's number: ");
        int choice = scanner.nextInt();
        controller.setSelectedScenario(controller.chooseScenario(controller.getAvailableScenarios(), choice));

        // Display available locomotives and get user selection
        List<String> locomotiveNames = controller.getAvailableLocomotivesNames();
        System.out.println("Available Locomotives:");
        for (String locomotiveName : locomotiveNames) {
            System.out.println(locomotiveName);
        }
        System.out.print("Enter the locomotive's number: ");
        int choice2 = scanner.nextInt();
        controller.setSelectedLocomotive(controller.chooseLocomotive(controller.getAvailableLocomotives(), choice2));

        // Display available stations
        List<String> stationNames = controller.getAvailableStationNames();
        System.out.println("Available Stations:");
        for (int i = 0; i < stationNames.size(); i++) {
            System.out.println((i + 1) + ": " + stationNames.get(i));
        }

        // List to track chosen stations and prevent duplicates in stops
        List<Integer> chosenStops = new ArrayList<>();

        // Select starting station
        System.out.print("Select the starting station: ");
        int choice3 = scanner.nextInt();
        controller.setFirstStation(controller.chooseStation(controller.getAvailableStations(), choice3));
        chosenStops.add(choice3);

        // Select ending station
        System.out.print("Select the ending station: ");
        int choice4 = scanner.nextInt();
        controller.setLastStation(controller.chooseStation(controller.getAvailableStations(), choice4));
        chosenStops.add(choice4);

        scanner.nextLine(); // Consume leftover newline

        // Ask user if they want to add intermediate stops
        System.out.print("Would you like to add stops to the route? (yes/no): ");
        String stopResponse = scanner.nextLine().trim().toLowerCase();
        boolean hasStops = false;

        if (stopResponse.equals("yes") || stopResponse.equals("y")) {
            hasStops = true;

            // Loop to add stops until user finishes
            while (true) {
                System.out.println("Available stations to add as stops:");
                for (int i = 0; i < stationNames.size(); i++) {
                    if (!chosenStops.contains(i + 1)) {
                        System.out.printf("%d. %s%n", i + 1, stationNames.get(i));
                    }
                }

                System.out.print("Enter the number of a station to add as a stop (or 0 to finish): ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.next();
                }
                int selection = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (selection == 0) break; // Finish adding stops

                if (selection < 1 || selection > stationNames.size()) {
                    System.out.println("Invalid selection. Please choose a valid station number.");
                } else if (chosenStops.contains(selection)) {
                    System.out.println("This station has already been selected. Choose a different one.");
                } else {
                    chosenStops.add(selection);
                    controller.addStation(controller.chooseStation(controller.getAvailableStations(), selection));
                    System.out.println("Station added.");
                }
            }
        }

        // Display available cargo at selected stations
        List<String> supplyCargoDisplay = controller.getSupplyDisplayList();

        System.out.println();
        System.out.println("Supplied Cargo Available:");
        if (supplyCargoDisplay.isEmpty()) {
            System.out.println("No supplied cargo available at selected stations.");
        } else {
            for (String cargoStr : supplyCargoDisplay) {
                System.out.println(cargoStr);
            }

            // Let user select cargo by entering indices separated by space
            System.out.print("Enter the numbers of the cargo you want to select (separated by space): ");
            String input = scanner.nextLine();
            String[] parts = input.trim().split("\\s+");

            for (String part : parts) {
                try {
                    int idx = Integer.parseInt(part);
                    boolean success = controller.selectCargoByIndex(idx);
                    if (!success) {
                        System.out.println("Invalid cargo index: " + idx);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + part);
                }
            }
        }

        // Create route using controller depending on presence of stops
        if (hasStops) {
            controller.createRoute2();
        } else {
            controller.createRoute();
        }

        System.out.println("Route Created!");
    }
}
