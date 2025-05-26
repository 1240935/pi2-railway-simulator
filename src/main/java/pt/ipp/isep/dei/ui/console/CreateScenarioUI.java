package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateScenarioController;

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
        List<String> names = controller.getAvailableMapNames();
        System.out.println("Available maps:");
        for (int i = 0; i < names.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, names.get(i));
        }

        // User selects a map by number, with input validation
        int choice;
        do {
            System.out.print("Select a map by number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // discard non-integer input
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > names.size());
        controller.setMap(controller.chooseMap(controller.getAvailableMaps(), choice));

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
        controller.setYear(year);

        // Display locomotive types and select multiple by number
        List<String> types = controller.getAvailableLocomotiveTypeNames();
        System.out.println("Available locomotive types:");
        for (int i = 0; i < types.size(); i++) {
            System.out.println((i + 1) + ": " + types.get(i));
        }
        List<Integer> selections = controller.readIntegerSelections("Enter the locomotive's numbers (separated by space): ");
        controller.setLocomotiveTypes(controller.chooseLocomotiveTypes(controller.getAvailableLocomotivesTypes(), selections));

        // Display resource types for importation and select multiple by number
        List<String> impor_exportypes = controller.getAvailableImpor_ExportationNames();
        System.out.println("Available resource types (importation):");
        for (int i = 0; i < impor_exportypes.size(); i++) {
            System.out.println((i + 1) + ": " + impor_exportypes.get(i));
        }
        List<Integer> selections3 = controller.readIntegerSelections("Enter resource types numbers (separated by space): ");
        controller.setImportation(controller.chooseImportation(controller.getAvailableResourceType(), selections3));

        // Display resource types for exportation and select multiple by number
        System.out.println("Available resource types (exportation):");
        for (int i = 0; i < impor_exportypes.size(); i++) {
            System.out.println((i + 1) + ": " + impor_exportypes.get(i));
        }
        List<Integer> selections4 = controller.readIntegerSelections("Enter resource types numbers (separated by space): ");
        controller.setExportation(controller.chooseExportation(controller.getAvailableResourceType(), selections4));

        // Display industry changer types and select multiple by number
        List<String> changerTypes = controller.getAvailableIndustryChangerNames();
        System.out.println("Available changer types:");
        for (int i = 0; i < changerTypes.size(); i++) {
            System.out.println((i + 1) + ": " + changerTypes.get(i));
        }
        List<Integer> selections5 = controller.readIntegerSelections("Enter changer types numbers (separated by space): ");
        controller.setChangers(controller.chooseIndustryChanger(controller.getAvailableIndustryChangers(), selections5));

        // Create the scenario based on the user's selections
        controller.createScenario();
        System.out.println("Scenario created successfully.");
    }
}
