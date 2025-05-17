package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuildStationController;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based User Interface for building a new station.
 * <p>
 * This class interacts exclusively with the {@link BuildStationController}
 * using only primitive data types and strings to keep UI and domain decoupled.
 * <p>
 * The workflow includes:
 * <ol>
 *   <li>Selecting a scenario from available options</li>
 *   <li>Entering X and Y coordinates for the station location</li>
 *   <li>Selecting the type of station to build (Depot, Terminal, or Station)</li>
 *   <li>Specifying the center position if the type is Station (compass direction)</li>
 *   <li>Displaying a recommended station name based on location and type</li>
 *   <li>Allowing the user to accept the proposed name or enter a custom name</li>
 *   <li>Creating the station and confirming creation</li>
 * </ol>
 */
public class BuildStationUI {
    private final BuildStationController controller = new BuildStationController();

    /**
     * Starts the interactive process for building a station.
     * <p>
     * This method guides the user step-by-step through scenario selection,
     * coordinate input, station type selection, center positioning,
     * naming, and station creation.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Select Scenario
        System.out.println("Available scenarios:");
        List<String> scenarioNames = controller.getAvailableScenarioDescriptions();
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println(i + ": " + scenarioNames.get(i));
        }
        System.out.print("Select scenario by number: ");
        int scenarioIndex = scanner.nextInt();
        if (!controller.setScenario(scenarioIndex)) {
            System.out.println("Invalid scenario index. Exiting.");
            return;
        }

        // Step 2: Input Coordinates
        System.out.print("Enter X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter Y coordinate: ");
        int y = scanner.nextInt();

        if (!controller.checkCoordinates(x, y)) {
            System.out.println("Invalid position for current map. Exiting.");
            return;
        }

        // Step 3: Choose Station Type
        List<String> stationTypeNames = controller.getAvailableStationTypes();
        System.out.println("Available station types:");
        for (int i = 0; i < stationTypeNames.size(); i++) {
            System.out.println(i + ": " + stationTypeNames.get(i));
        }
        System.out.print("Select station type by number: ");
        int typeIndex = scanner.nextInt();
        if (!controller.setType(typeIndex)) {
            System.out.println("Invalid type. Exiting.");
            return;
        }

        // Step 4: Set Center based on station type
        if (controller.isSelectedTypeStation()) {
            System.out.print("Enter compass direction (NE, SE, NW, SW): ");
            scanner.nextLine();
            String direction = scanner.nextLine().trim();
            controller.setCompassCenter(direction, x, y);
        } else {
            controller.setGeometricCenter();
        }

        // Step 5: Propose Name and ask user to accept or input custom name
        String proposedName = controller.getRecommendedName(x, y);
        System.out.println("Proposed name: " + proposedName);
        System.out.print("Use proposed name? (y/n): ");
        String response = scanner.next().trim().toLowerCase();
        if (!response.equals("y")) {
            System.out.print("Enter custom name: ");
            scanner.nextLine();
            proposedName = scanner.nextLine().trim();
        }
        controller.setName(proposedName);

        // Step 6: Create Station
        controller.createStation(x, y);
        System.out.println("Station '" + proposedName + "' created successfully.");
    }
}
