package pt.ipp.isep.dei.ui.console;
import pt.ipp.isep.dei.controller.UpgradeStationController;

import java.util.List;
import java.util.Scanner;

public class UpgradeStationUI {
    private final UpgradeStationController controller;
    private final Scanner scanner;

    public UpgradeStationUI() {
        controller = new UpgradeStationController();
        scanner = new Scanner(System.in);
    }
    public void start() {
        System.out.println("==== Upgrade Station ====");
        List<String> scenarioNames = controller.getAvailableScenarioNames();
        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println((i + 1) + ": " + scenarioNames.get(i));
        }
        System.out.println("Enter the scenario's number:");
        int choice = scanner.nextInt();
        controller.setScenario(controller.chooseScenario(controller.getAvailableScenarios(),choice));

        List<String> stationNames = controller.getAvailableStationNames();
        System.out.println("Available Stations:");
        for (int i = 0; i < stationNames.size(); i++) {
            System.out.println((i + 1) + ": " + stationNames.get(i));
        }
        System.out.println("Enter the stations's number:");
        int choice2 = scanner.nextInt();
        controller.setStation(controller.chooseStation(controller.getAvailableStations(),choice2));

        List<String> upgradeNames = controller.getAvailableBuildingNames();
        System.out.println("Available Upgrades:");
        for (int i = 0; i < upgradeNames.size(); i++) {
            System.out.println((i + 1) + ": " + upgradeNames.get(i));
        }
        System.out.println("Enter the upgrades's number:");
        int choice3 = scanner.nextInt();
        controller.setType(controller.chooseBuildingType(controller.getAvailableBuildingTypes(),choice3));
        controller.setBuilding(controller.createBuilding());

        //falta mostrar a info e confirmar!!!
        controller.upgradeStation();

    }
}
