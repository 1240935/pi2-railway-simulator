package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AssignRouteController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssignRouteUI {

    private final AssignRouteController controller;
    private final Scanner scanner;

    public AssignRouteUI() {
        this.controller = new AssignRouteController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        // 1. Select Scenario
        List<String> scenarioNames = controller.getAvailableScenarioNames();
        System.out.println("Available scenarios:");
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println((i + 1) + ": " + scenarioNames.get(i));
        }
        System.out.print("Select scenario number: ");
        int scenarioIndex = scanner.nextInt();
        scanner.nextLine();

        // 2. Add Points of Route
        System.out.print("Enter number of points in the route: ");
        int numPoints = scanner.nextInt();
        scanner.nextLine();

        List<Integer> usedStationIndices = new ArrayList<>();

        for (int i = 0; i < numPoints; i++) {
            System.out.println("\n--- Point of Route #" + (i + 1) + " ---");

            // Get all stations names
            List<String> allStationNames = controller.getAvailableStationNames(scenarioIndex);
            List<Integer> availableIndices = new ArrayList<>();
            int displayIndex = 1;

            // Show only unused stations
            for (int j = 0; j < allStationNames.size(); j++) {
                if (!usedStationIndices.contains(j)) {  // j is zero-based
                    System.out.println(displayIndex + ": " + allStationNames.get(j));
                    availableIndices.add(j);  // zero-based index
                    displayIndex++;
                }
            }

            if (availableIndices.isEmpty()) {
                System.out.println("No more unique stations available.");
                break;
            }

            System.out.print("Select station number: ");
            int stationChoice = scanner.nextInt();
            scanner.nextLine();

            if (stationChoice < 1 || stationChoice > availableIndices.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            int zeroBasedStationIndex = availableIndices.get(stationChoice - 1); // zero-based index
            usedStationIndices.add(zeroBasedStationIndex); // store zero-based

            int oneBasedStationIndex = zeroBasedStationIndex + 1;

            List<String> cargoNames = controller.getAvailableCargoNames(scenarioIndex, oneBasedStationIndex);
            List<Integer> selectedCargoIndices = new ArrayList<>();

            if (!cargoNames.isEmpty()) {
                System.out.println("Available cargo at this station:");
                for (int j = 0; j < cargoNames.size(); j++) {
                    System.out.println((j + 1) + ": " + cargoNames.get(j));
                }

                while (true) {
                    System.out.print("Select cargo number to pick up (or 0 to stop): ");
                    int cargoIndex = scanner.nextInt();
                    scanner.nextLine();

                    if (cargoIndex == 0) break;
                    if (cargoIndex < 1 || cargoIndex > cargoNames.size()) {
                        System.out.println("Invalid choice.");
                    } else if (!selectedCargoIndices.contains(cargoIndex)) {
                        selectedCargoIndices.add(cargoIndex);
                    } else {
                        System.out.println("Cargo already selected.");
                    }
                }
            } else {
                System.out.println("No cargo available at this station.");
            }

            // 2.3. Select Cargo Mode
            List<String> cargoModes = controller.getCargoModeNames();
            System.out.println("Select cargo mode:");
            for (int j = 0; j < cargoModes.size(); j++) {
                System.out.println((j + 1) + ": " + cargoModes.get(j));
            }
            System.out.print("Enter cargo mode number: ");
            int modeIndex = scanner.nextInt();
            scanner.nextLine();

            // 2.4. Add Point of Route
            controller.createPointOfRoute(
                    controller.getStationByIndex(scenarioIndex, zeroBasedStationIndex + 1),
                    controller.getSelectedCargoList(
                            selectedCargoIndices,
                            controller.getStationByIndex(scenarioIndex, zeroBasedStationIndex + 1).getSupplies()
                    ),
                    controller.getCargoModeByIndex(modeIndex)
            );
        }

        // 3. Select Locomotive
        List<String> locomotiveNames = controller.getAvailableLocomotiveNames(scenarioIndex);
        System.out.println("\nAvailable Locomotives:");
        for (String locomotiveName : locomotiveNames) {
            System.out.println("ID:" + locomotiveName);
        }

        System.out.print("Select locomotive number: ");
        int locoIndex = scanner.nextInt();
        scanner.nextLine();

        // 4. Create Route
        controller.createRoute(controller.getScenarioByIndex(scenarioIndex), controller.getLocomotiveByIndex(scenarioIndex, locoIndex), controller.getPointsOfRoute());
    }
}
