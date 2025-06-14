package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.ViewStationDetailsController;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for displaying station details within scenarios.
 *
 * <p>This class interacts with the user via the console to:
 * <ul>
 *   <li>List available scenarios for selection.</li>
 *   <li>List stations within the selected scenario.</li>
 *   <li>Display detailed information about the selected station, including building and cargo data.</li>
 * </ul>
 *
 * <p>It relies on the {@link ViewStationDetailsController} to retrieve and manage data.
 */
public class ViewStationDetailsUI {

    private final ViewStationDetailsController controller = new ViewStationDetailsController();

    /**
     * Starts the UI interaction process.
     *
     * <p>The method follows these steps:
     * <ol>
     *   <li>Displays all available scenarios and prompts the user to select one.</li>
     *   <li>Displays stations for the selected scenario and prompts the user to select one.</li>
     *   <li>Retrieves and prints the building details and cargo information for the selected station.</li>
     * </ol>
     *
     * <p>If no scenarios or stations are available, or if invalid selections are made,
     * appropriate error messages are shown and the method exits.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Select Scenario
        List<String> scenarioDescriptions = controller.getAvailableScenarioNames();
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



        // Step 2: List Stations
        List<String> stationNames = controller.getAvailableStationNames(scenarioIndex);
        if (stationNames.isEmpty()) {
            System.out.println("No stations available.");
            return;
        }

        System.out.println("Stations:");
        for (int i = 0; i < stationNames.size(); i++) {
            System.out.println((i + 1) + ". " + stationNames.get(i));
        }

        System.out.print("Select station by number: ");
        int stationIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (stationIndex < 0 || stationIndex >= stationNames.size()) {
            System.out.println("Invalid station selection.");
            return;
        }

        String buildingDetails = controller.getBuildingDetailsString(stationIndex, scenarioIndex);
        String cargoInfo = controller.getCargoInfo(stationIndex, scenarioIndex);

        System.out.println("\nBuilding Details:");
        System.out.println(buildingDetails);

        System.out.println("\nCargo Information:");

        if (cargoInfo == null || cargoInfo.trim().isEmpty()) {
            System.out.println("No available cargo");
        } else {
            System.out.println(cargoInfo);
        }

    }
}
