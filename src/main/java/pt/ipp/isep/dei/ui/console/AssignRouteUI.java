package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AssignRouteController;
import pt.ipp.isep.dei.dto.PointOfRouteDTO;
import pt.ipp.isep.dei.dto.RouteDTO;

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

    /**
     * Starts the route assignment process.
     * Allows the user to select scenario, points of route, cargo, cargo modes,
     * locomotive, and creates the associated route.
     */
    public void start() {
        RouteDTO routeDTO = new RouteDTO();
        PointOfRouteDTO pointOfRouteDTO = new PointOfRouteDTO();

        // Select available scenario
        List<String> scenarioNames = controller.getAvailableScenarioNames();
        System.out.println("Available scenarios:");
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println((i + 1) + ": " + scenarioNames.get(i));
        }
        System.out.print("Select scenario number: ");
        int scenarioIndex = scanner.nextInt();
        scanner.nextLine();

        // Define points in the route, avoiding station repetition
        System.out.print("Enter number of points in the route: ");
        int numPoints = scanner.nextInt();
        scanner.nextLine();

        List<Integer> usedStationIndices = new ArrayList<>();

        for (int i = 0; i < numPoints; i++) {
            System.out.println("\n--- Point of Route #" + (i + 1) + " ---");

            List<String> allStationNames = controller.getAvailableStationNames(scenarioIndex);
            List<Integer> availableIndices = new ArrayList<>();
            int displayIndex = 1;

            for (int j = 0; j < allStationNames.size(); j++) {
                if (!usedStationIndices.contains(j)) {
                    System.out.println(displayIndex + ": " + allStationNames.get(j));
                    availableIndices.add(j);
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

            int zeroBasedStationIndex = availableIndices.get(stationChoice - 1);
            usedStationIndices.add(zeroBasedStationIndex);

            int oneBasedStationIndex = zeroBasedStationIndex + 1;

            // Select cargo available at the station
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

            // Select cargo mode for the point of route
            List<String> cargoModes = controller.getCargoModeNames();
            System.out.println("Select cargo mode:");
            for (int j = 0; j < cargoModes.size(); j++) {
                System.out.println((j + 1) + ": " + cargoModes.get(j));
            }
            System.out.print("Enter cargo mode number: ");
            int modeIndex = scanner.nextInt();
            scanner.nextLine();

            pointOfRouteDTO.setCargo(controller.getSelectedCargoList(selectedCargoIndices,
                    controller.getStationByIndex(scenarioIndex, zeroBasedStationIndex + 1).getSupplies()));

            pointOfRouteDTO.setStation(controller.getStationByIndex(scenarioIndex, zeroBasedStationIndex + 1));

            pointOfRouteDTO.setCargoMode(controller.getCargoModeByIndex(modeIndex));

            controller.createPointOfRoute(pointOfRouteDTO);
        }

        // Select locomotive for the route
        List<String> locomotiveNames = controller.getAvailableLocomotiveNames(scenarioIndex);
        System.out.println("\nAvailable Locomotives:");
        int locoId = 1;
        for (String locomotiveName : locomotiveNames) {
            System.out.println(locoId+ ": " + locomotiveName);
            locoId++;
        }

        System.out.print("Select locomotive number: ");
        int locoIndex = scanner.nextInt();
        scanner.nextLine();

        // Final creation of the route with selected points and locomotive
        routeDTO.setPointsOfRoute(controller.getPointsOfRoute());
        routeDTO.setLocomotive(controller.getLocomotiveByIndex(scenarioIndex, locoIndex));
        controller.createRoute(routeDTO, scenarioIndex);
    }
}
