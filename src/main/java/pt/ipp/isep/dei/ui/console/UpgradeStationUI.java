package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.UpgradeStationController;
import pt.ipp.isep.dei.domain.Player;

import java.util.List;
import java.util.Scanner;

public class UpgradeStationUI {

    private final UpgradeStationController controller = new UpgradeStationController();
    private final Scanner scanner = new Scanner(System.in);

    public void start(Player player) {
        controller.setPlayer(player);

        // 1. Show scenarios
        List<String> scenarioNames = controller.getAvailableScenarioNames();
        if (scenarioNames.isEmpty()) {
            System.out.println("No scenarios available.");
            return;
        }

        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println((i + 1) + ". " + scenarioNames.get(i));
        }

        int scenarioChoice = -1;
        while (scenarioChoice < 1 || scenarioChoice > scenarioNames.size()) {
            System.out.print("Select scenario by number: ");
            try {
                scenarioChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        controller.setScenarioByIndex(scenarioChoice - 1);

        // 2. Show stations
        List<String> stationNames = controller.getAvailableStationNames();
        if (stationNames.isEmpty()) {
            System.out.println("No stations available.");
            return;
        }

        System.out.println("\nAvailable Stations:");
        for (int i = 0; i < stationNames.size(); i++) {
            System.out.println((i + 1) + ". " + stationNames.get(i));
        }

        int stationChoice = -1;
        while (stationChoice < 1 || stationChoice > stationNames.size()) {
            System.out.print("Select station by number: ");
            try {
                stationChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        controller.setStationByIndex(stationChoice - 1);

        // 3. Show available buildings
        List<String> buildingNames = controller.getAvailableBuildingNames();
        if (buildingNames.isEmpty()) {
            System.out.println("No valid buildings available for this station.");
            return;
        }

        System.out.println("\nAvailable Buildings:");
        for (int i = 0; i < buildingNames.size(); i++) {
            System.out.println((i + 1) + ". " + buildingNames.get(i));
        }

        int buildingChoice = -1;
        while (buildingChoice < 1 || buildingChoice > buildingNames.size()) {
            System.out.print("Select building type by number: ");
            try {
                buildingChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        controller.setBuildingTypeByIndex(buildingChoice - 1);

        // 4. Display budget and cost
        System.out.println("Player budget: " + player.getBudget());
        System.out.println("Building cost: " + controller.getSelectedBuildingCost());
        System.out.print("Do you want to proceed with the purchase? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("y")) {
            System.out.println("Purchase cancelled.");
            return;
        }

        // 5. Attempt to create the building
        boolean success = controller.createAndSetBuilding();
        if (success) {
            System.out.println("Station upgraded successfully.");
        } else {
            System.out.println("Not enough budget to upgrade this station.");
        }
    }
}
