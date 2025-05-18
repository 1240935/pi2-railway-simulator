package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AssignRouteController;
import pt.ipp.isep.dei.controller.UpgradeStationController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssignRouteUI {
    private final AssignRouteController controller;
    private final Scanner scanner;

    public AssignRouteUI() {
        controller = new AssignRouteController();
        scanner = new Scanner(System.in);
    }
    public void start() {
        System.out.println("==== Assign Route ====");
        List<String> scenarioNames = controller.getAvailableScenarioNames();
        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println((i + 1) + ": " + scenarioNames.get(i));
        }
        System.out.println("Enter the scenario's number:");
        int choice = scanner.nextInt();
        controller.setSelectedScenario(controller.chooseScenario(controller.getAvailableScenarios(), choice));

        List<String> locomotiveNames = controller.getAvailableLocomotivesNames();
        System.out.println("Available Locomotives:");
        for (int i = 0; i < locomotiveNames.size(); i++) {
            System.out.println((i + 1) + ": " + locomotiveNames.get(i));
        }
        System.out.println("Enter the locomotive's number:");
        int choice2 = scanner.nextInt();
        controller.setSelectedLocomotive(controller.chooseLocomotive(controller.getAvailableLocomotives(), choice2));

        List<String> stationNames = controller.getAvailableStationNames();
        System.out.println("Available Stations:");
        for (int i = 0; i < stationNames.size(); i++) {
            System.out.println((i + 1) + ": " + stationNames.get(i));
        }

        List<Integer> chosenStops = new ArrayList<>();
        System.out.println("Which will be the starting station:");
        int choice3 = scanner.nextInt();
        controller.setFirstStation(controller.chooseStation(controller.getAvailableStations(), choice3));
        chosenStops.add(choice3);
        System.out.println("Which will be the ending station:");
        int choice4 = scanner.nextInt();
        controller.setLastStation(controller.chooseStation(controller.getAvailableStations(), choice4));
        chosenStops.add(choice4);
        System.out.print("Would you like to add stops to the route? (yes/no): ");
        String stopResponse = scanner.nextLine().trim().toLowerCase();
        boolean stops = false;

        if (stopResponse.equals("yes") || stopResponse.equals("y")) {
            stops = true;

            while (true) {
                System.out.println("Available stations to add as stops:");
                for (int i = 0; i < stationNames.size(); i++) {
                    if (!chosenStops.contains(i + 1)) { // display only unselected
                        System.out.printf("%d. %s%n", i + 1, stationNames.get(i));
                    }
                }

                System.out.print("Enter the number of a station to add as a stop (or 0 to finish): ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.next(); // discard non-integer input
                }
                int selection = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (selection == 0) break;

                if (selection < 1 || selection > stationNames.size()) {
                    System.out.println("Invalid selection. Please choose a valid station number.");
                } else if (chosenStops.contains(selection)) {
                    System.out.println("This station has already been selected. Choose a different one.");
                } else {
                    chosenStops.add(selection);
                    System.out.println("Station added.");
                    controller.addStation(controller.chooseStation(controller.getAvailableStations(),selection));
                }
            }
        }
        List<Integer> selectedIndexes = new ArrayList<>();
        controller.printCargoes(controller.getSupply(),controller.getDemand());

        System.out.print("Enter the numbers of the cargo you want to select (separated by space): ");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+");

        for (String part : parts) {
            try {
                int index = Integer.parseInt(part);

                if (index < 1 || index > controller.addAllCargo(controller.getSupply(),controller.getDemand()).size()) {
                    System.out.println("Invalid index: " + index);
                    continue;
                }

                if (selectedIndexes.contains(index)) {
                    System.out.println("Cargo already selected: " + index);
                    continue;
                }

                controller.addCargo(controller.addAllCargo(controller.getSupply(),controller.getDemand()).get(index - 1)); // Adjust for 0-based index
                selectedIndexes.add(index);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + part);
            }
        }
        if(stops){
            controller.createRoute();
        }
        else {
            controller.createRoute2();
        }
        System.out.println("Route Created!");
    }
}
