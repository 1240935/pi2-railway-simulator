package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateScenarioController;
import pt.ipp.isep.dei.dto.ScenarioDTO;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based User Interface for creating a new scenario.
 *
 * This class interacts with the user to:
 *  - Select a map
 *  - Choose a year for the scenario
 *  - Select locomotive types
 *  - Select resource importation and exportation types
 *  - Select industry changers
 *
 * It delegates all business logic operations to the CreateScenarioController.
 */
public class CreateScenarioUI {
    private final CreateScenarioController controller;
    private final Scanner scanner;

    /**
     * Constructs a new CreateScenarioUI, initializing the controller and scanner.
     */
    public CreateScenarioUI() {
        controller = new CreateScenarioController();
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the scenario creation interaction with the user.
     * Guides the user through the selection process and creates
     * the scenario based on their choices.
     */
    public void start() {
        // Display available maps
        System.out.println("Available maps:");
        List<String> mapNames = controller.getAvailableMapNames();
        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ": " + mapNames.get(i));
        }

        System.out.print("Select map by number: ");
        int mapIndex = scanner.nextInt() - 1;

        // User selects a year for the scenario, validated between 1800 and 2025
        int year;
        do {
            System.out.print("Select a year for the map (1800 - 2025): ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // discard non-integer input
            }
            year = scanner.nextInt();
        } while (year < 1800 || year > 2025);

        // Display locomotive types and select multiple by number
        List<String> types = controller.getAvailableLocomotiveTypeNames();
        System.out.println("Available locomotive types:");
        for (int i = 0; i < types.size(); i++) {
            System.out.println((i + 1) + ": " + types.get(i));
        }
        List<Integer> selectionsLocomotiveTypes = controller.readIntegerSelections("Enter the locomotive's numbers (separated by space): ");

        List<String> impor_exportypes = controller.getAvailableResourceTypeNames();
        System.out.println("Available resource types (importation):");
        for (int i = 0; i < impor_exportypes.size(); i++) {
            System.out.println((i + 1) + ": " + impor_exportypes.get(i));
        }
        List<Integer> selectionsImportation = controller.readIntegerSelections("Enter resource types numbers (separated by space):\nType ! for null. ");

        // Display resource types for exportation and select multiple by number
        System.out.println("Available resource types (exportation):");
        for (int i = 0; i < impor_exportypes.size(); i++) {
            System.out.println((i + 1) + ": " + impor_exportypes.get(i));
        }
        List<Integer> selectionsExportation = controller.readIntegerSelections("Enter resource types numbers (separated by space):");

        controller.configurePorts(mapIndex, controller.chooseResourceTypes(selectionsImportation), controller.chooseResourceTypes(selectionsExportation));

        // Display industry changer types and select multiple by number
        System.out.println("Enter the industry changer value (1 - 200):");
        int changer = scanner.nextInt();
        while (changer < 1 || changer > 200) {
            System.out.println("Invalid input. Please enter a value between 1 and 200:");
            changer = scanner.nextInt();
        }

        ScenarioDTO scenarioDTO = new ScenarioDTO();
        scenarioDTO.setMap(controller.getMapByIndex(mapIndex));
        scenarioDTO.setYear(year);
        scenarioDTO.setLocomotiveTypes(controller.chooseLocomotiveTypes(selectionsLocomotiveTypes));
        scenarioDTO.setImportation(controller.chooseResourceTypes(selectionsImportation));
        scenarioDTO.setExportation(controller.chooseResourceTypes(selectionsExportation));
        scenarioDTO.setChanger(changer);

        // Create the scenario based on the user's selections
        controller.createScenario(scenarioDTO);
        System.out.println("Scenario created successfully.");
    }
}
