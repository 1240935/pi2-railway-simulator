package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based User Interface for building a station in the game.
 * Guides the player through scenario selection, coordinate input, station type selection,
 * naming, confirmation, and final creation of the station.
 */
public class BuildStationUI {

    private final BuildStationController controller = new BuildStationController();

    /**
     * Starts the build station process for the given player.
     * It interacts via the console, collecting inputs step by step,
     * showing options and validating user choices.
     *
     * @param player The player who wants to build a station; player's budget is used for purchase validation.
     */
    public void start(Player player) {
        Scanner scanner = new Scanner(System.in);
        controller.setPlayer(player);

        // Step 1: Select Scenario
        List<String> scenarios = controller.getAvailableScenarioDescriptions();
        if (scenarios.isEmpty()) {
            System.out.println("No scenarios available.");
            return;
        }

        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarios.size(); i++) {
            System.out.println((i + 1) + ". " + scenarios.get(i));
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

        controller.setPosition(x, y);

        // Step 3: Choose Station Type
        List<String> stationTypeNames = controller.getAvailableStationTypes();
        System.out.println("Available station types:");
        for (int i = 0; i < stationTypeNames.size(); i++) {
            System.out.println((i + 1) + ": " + stationTypeNames.get(i));
        }
        System.out.print("Select station type by number: ");
        int typeIndex = scanner.nextInt() - 1; // Adjust to zero-based index
        scanner.nextLine(); // consume leftover newline
        if (!controller.setType(typeIndex)) {
            System.out.println("Invalid type. Exiting.");
            return;
        }

        // Step 4: Set Center based on station type
        if (controller.isSelectedTypeStation()) {
            System.out.print("Enter compass direction (NE, SE, NW, SW): ");
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
            scanner.nextLine(); // consume newline
            proposedName = scanner.nextLine().trim();
        }
        controller.setName(proposedName);
        scanner.nextLine();

        // Step 6: Confirm purchase with player budget and station cost
        System.out.println("Player budget: " + player.getBudget());
        System.out.println("Station cost: " + controller.getSelectedType().getCost());
        System.out.print("Do you want to proceed with the purchase? (y/n): ");

        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("y")) {
            System.out.println("Purchase cancelled.");
            return;
        }

        // Step 7: Create Station
        boolean success = controller.createStation(x, y);
        if (success) {
            System.out.println("Station '" + proposedName + "' created successfully.");
        } else {
            System.out.println("Not enough budget to buy this station.");
        }
    }
}
