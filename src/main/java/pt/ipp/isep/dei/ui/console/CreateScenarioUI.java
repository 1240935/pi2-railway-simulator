package pt.ipp.isep.dei.ui.console;
import pt.ipp.isep.dei.controller.CreateScenarioController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateScenarioUI {
    private final CreateScenarioController controller;
    private final Scanner scanner;

    public CreateScenarioUI() {
        controller = new CreateScenarioController();
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("==== Create Scenario ====");
        List<String> names = controller.getAvailableMapNames();
        System.out.println("Available maps:");
        for (int i = 0; i < names.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, names.get(i));
        }

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

        List<String> types = controller.getAvailableLocomotiveTypeNames();
        System.out.println("Available locomotive types:");
        for (int i = 0; i < types.size(); i++) {
            System.out.println((i + 1) + ": " + types.get(i));
        }
        List<Integer> selections = controller.readIntegerSelections("Enter the locomotive's numbers (separated by space): ");
        controller.setLocomotiveTypes(controller.chooseLocomotiveTypes(controller.getAvailableLocomotivesTypes(), selections));

        List<String> itypes = controller.getAvailableIndustryTypeNames();
        System.out.println("Available industry types:");
        for (int i = 0; i < itypes.size(); i++) {
            System.out.println((i + 1) + ": " + itypes.get(i));
        }
        List<Integer> selections2 = controller.readIntegerSelections("Enter industry types numbers (separated by space): ");
        controller.setIndustryTypes(controller.chooseIndustryTypes(controller.getAvailableIndustryTypes(), selections2));

        List<String> impor_exportypes = controller.getAvailableImpor_ExportationNames();
        System.out.println("Available resource types (importation):");
        for (int i = 0; i < impor_exportypes.size(); i++) {
            System.out.println((i + 1) + ": " + impor_exportypes.get(i));
        }

        List<Integer> selections3 = controller.readIntegerSelections("Enter resource types numbers (separated by space): ");
        controller.setImportation(controller.chooseImportation(controller.getAvailableResourceType(), selections3));

        System.out.println("Available resource types (exportation):");
        for (int i = 0; i < impor_exportypes.size(); i++) {
            System.out.println((i + 1) + ": " + impor_exportypes.get(i));
        }

        List<Integer> selections4 = controller.readIntegerSelections("Enter resource types numbers (separated by space): ");

        controller.setExportation(controller.chooseExportation(controller.getAvailableResourceType(), selections4));

        List<String> changerTypes = controller.getAvailableIndustryChangerNames();
        System.out.println("Available changer types:");
        for (int i = 0; i < changerTypes.size(); i++) {
            System.out.println((i + 1) + ": " + changerTypes.get(i));
        }
        List<Integer> selections5 = controller.readIntegerSelections("Enter changer types numbers (separated by space): ");
        controller.setChangers(controller.chooseIndustryChanger(controller.getAvailableIndustryChangers(), selections5));

        // FALTA O SHOW INFORMATION SÓ ISSO !!! acho
        controller.createScenario();
    }
}
