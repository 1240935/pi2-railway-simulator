package pt.ipp.isep.dei.ui.console;

import  pt.ipp.isep.dei.controller.UpgradeStationController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.dto.BuildingDTO;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based UI for the station upgrade feature.
 *
 * Allows a player to:
 * 1. Select a scenario
 * 2. Select a station within that scenario
 * 3. Choose a building type to upgrade the station
 * 4. Confirm the purchase if budget permits
 * 5. Perform the upgrade and notify the player of the result
 */
public class UpgradeStationUI {

    private final UpgradeStationController controller = new UpgradeStationController();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Starts the UI interaction for upgrading a station, with the given player.
     * @param player the player performing the upgrade
     */
    public void start(Player player) {
        controller.setPlayer(player);
        BuildingDTO buildingDTO = new BuildingDTO();

        // 1. Show scenarios and get player selection
        List<String> scenarioNames = controller.getAvailableScenarioNames();
        if (scenarioNames.isEmpty()) {
            System.out.println("No scenarios available.");
            return;
        }
        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println((i + 1) + ". " + scenarioNames.get(i));
        }

        int scenarioIndex = -1;
        while (scenarioIndex < 1 || scenarioIndex > scenarioNames.size()) {
            System.out.print("Select scenario by number: ");
            try {
                scenarioIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        scenarioIndex -= 1;

        // 2. Show stations and get player selection
        List<String> stationNames = controller.getAvailableStationNames(scenarioIndex);
        if (stationNames.isEmpty()) {
            System.out.println("No stations available.");
            return;
        }
        System.out.println("\nAvailable Stations:");
        for (int i = 0; i < stationNames.size(); i++) {
            System.out.println((i + 1) + ". " + stationNames.get(i));
        }

        int stationIndex = -1;
        while (stationIndex < 1 || stationIndex > stationNames.size()) {
            System.out.print("Select station by number: ");
            try {
                stationIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        stationIndex -= 1;

        // 3. Show available building types and get player selection
        List<String> buildingNames = controller.getAvailableBuildingTypeNames(scenarioIndex, stationIndex);
        if (buildingNames.isEmpty()) {
            System.out.println("No valid buildings available for this station.");
            return;
        }
        System.out.println("\nAvailable Buildings:");
        for (int i = 0; i < buildingNames.size(); i++) {
            System.out.println((i + 1) + ". " + buildingNames.get(i));
        }

        int buildingTypeIndex = -1;
        while (buildingTypeIndex < 1 || buildingTypeIndex > buildingNames.size()) {
            System.out.print("Select building type by number: ");
            try {
                buildingTypeIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        buildingTypeIndex -= 1;

        System.out.println("Player budget: " + player.getBudget());
        System.out.println("Building cost: " + controller.getBuildingTypeByIndex(scenarioIndex, stationIndex, buildingTypeIndex).getCost());
        System.out.print("Do you want to proceed with the purchase? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("y")) {
            System.out.println("Purchase cancelled.");
            return;
        }

        buildingDTO.setBuildingType(controller.getBuildingTypeByIndex(scenarioIndex, stationIndex, buildingTypeIndex));
        controller.createBuilding(buildingDTO, stationIndex);

    }
}
