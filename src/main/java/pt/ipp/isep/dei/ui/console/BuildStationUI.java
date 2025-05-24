package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based User Interface for building a station in the game.
 * <p>
 * Guides the player through the entire station creation process including:
 * - Scenario selection
 * - Coordinate input and validation
 * - Station type selection
 * - Determining demanded and supplied cargoes for the station
 * - Setting the station's center (compass or geometric)
 * - Naming the station with user confirmation or customization
 * - Confirming the purchase based on player's budget
 * - Final creation of the station
 * </p>
 */
public class BuildStationUI {

    private final BuildStationController controller = new BuildStationController();

    /**
     * Starts the build station process for the specified player.
     * <p>
     * This method interacts with the user via console input/output to:
     * 1. Display available scenarios and let the player select one.
     * 2. Request and validate X and Y coordinates for the station location.
     * 3. Display available station types and let the player select one.
     * 4. Determine the cargo demands and supplies based on position and station type.
     * 5. Ask for compass center input if applicable, otherwise use geometric center.
     * 6. Propose a default station name and allow the player to accept or enter a custom name.
     * 7. Show the player's budget and station cost for purchase confirmation.
     * 8. Attempt to create the station, displaying success or failure messages.
     * </p>
     *
     * @param player the Player object who is building the station; used to validate budget and ownership
     */
    public void start(Player player) {
        Scanner scanner = new Scanner(System.in);
        controller.setPlayer(player);

        List<String> scenarios = controller.getAvailableScenarioDescriptions();
        if (scenarios.isEmpty()) {
            System.out.println("No scenarios available.");
            return;
        }

        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarios.size(); i++) {
            System.out.println((i + 1) + ". " + scenarios.get(i));
        }

        int scenarioIndex;
        while (true) {
            System.out.print("Select scenario (1-" + scenarios.size() + "): ");
            String input = scanner.nextLine();
            try {
                scenarioIndex = Integer.parseInt(input);
                if (scenarioIndex >= 1 && scenarioIndex <= scenarios.size()) {
                    break;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid input. Try again.");
        }
        controller.setScenario(scenarioIndex - 1);

        // Step 2: Input Coordinates
        System.out.print("Enter X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter Y coordinate: ");
        int y = scanner.nextInt();

        if (!controller.checkOverbuilding(x, y)) {
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
        int typeIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume leftover newline

        if (!controller.setType(typeIndex)) {
            System.out.println("Invalid type. Exiting.");
            return;
        }

        // Step 4: Determine demanded and supplied cargoes based on position and type
        controller.determineStationCargoes(x, y);

        // Step 5: Set station center depending on station type
        if (controller.isSelectedTypeStation()) {
            System.out.print("Enter compass direction (NE, SE, NW, SW): ");
            String direction = scanner.nextLine().trim();
            controller.setCompassCenter(direction, x, y);
        } else {
            controller.setGeometricCenter();
        }

        // Step 6: Propose a station name and ask player to accept or customize
        String proposedName = controller.getRecommendedName(x, y);
        System.out.println("Proposed name: " + proposedName);
        System.out.print("Use proposed name? (y/n): ");
        String response = scanner.next().trim().toLowerCase();

        if (!response.equals("y")) {
            System.out.print("Enter custom name: ");
            scanner.nextLine(); // consume leftover newline
            proposedName = scanner.nextLine().trim();
        }

        controller.setName(proposedName);
        scanner.nextLine();

        // Step 7: Confirm purchase by displaying player budget and station cost
        System.out.println("Player budget: " + player.getBudget());
        System.out.println("Station cost: " + controller.getSelectedType().getCost());
        System.out.print("Do you want to proceed with the purchase? (y/n): ");

        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("y")) {
            System.out.println("Purchase cancelled.");
            return;
        }

        // Step 8: Attempt to create the station and show success/failure message
        boolean success = controller.createStation(x, y);
        if (success) {
            System.out.println("Station '" + proposedName + "' created successfully.");
        } else {
            System.out.println("Not enough budget to buy this station.");
        }
    }
}
