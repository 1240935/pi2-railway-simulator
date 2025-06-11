package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.dto.StationDTO;

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
        StationDTO stationDTO = new StationDTO();

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

        scenarioIndex = scenarioIndex - 1;

        System.out.print("Enter X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter Y coordinate: ");
        int y = scanner.nextInt();

        // Step 3: Choose Station Type
        List<String> stationTypeNames = controller.getAvailableStationTypes();
        System.out.println("Available station types:");
        for (int i = 0; i < stationTypeNames.size(); i++) {
            System.out.println((i + 1) + ": " + stationTypeNames.get(i));
        }
        System.out.print("Select station type by number: ");
        int typeIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // consume leftover newline


        // Step 5: Set station center depending on station type

        String direction = "";
        if (controller.isSelectedTypeStation(typeIndex)) {
            System.out.print("Enter compass direction (NE, SE, NW, SW): ");
            direction = scanner.nextLine().trim();
        }

        // Step 6: Propose a station name and ask player to accept or customize
        String proposedName = controller.getRecommendedName(x, y, scenarioIndex, typeIndex);
        String name;

        if (proposedName == null) {
            System.out.println("You must manually enter a station name.");
            System.out.print("Enter custom name: ");
            name = scanner.nextLine();
        } else {
            System.out.print("Proposed name: " + proposedName + "\nUse proposed name? (y/n): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("n")) {
                System.out.print("Enter custom name: ");
                name = scanner.nextLine();
            } else {
                name = proposedName;
            }
        }
        stationDTO.setStationType(controller.getStationTypeByIndex(typeIndex));
        // Step 7: Confirm purchase by displaying player budget and station cost
        System.out.println("Player budget: " + player.getBudget());
        System.out.println("Station cost: " + stationDTO.getStationType().getCost());
        System.out.print("Do you want to proceed with the purchase? (y/n): ");

        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("y")) {
            System.out.println("Purchase cancelled.");
            return;
        }
        stationDTO.setPosition(controller.createPosition(x,y));
        stationDTO.setName(name);
        stationDTO.setCenter(controller.getCenter(direction,x,y,typeIndex));
        stationDTO.setScenario(controller.getScenarioByIndex(scenarioIndex));
        stationDTO.setMap(stationDTO.getScenario().getMap());
        stationDTO.setSupplies(controller.getSuppliedCargoes(x,y,scenarioIndex,typeIndex));
        stationDTO.setDemands(controller.getDemandedCargoes(x,y,scenarioIndex,typeIndex));
        controller.createStation(stationDTO);
    }
}
