package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.VerifyTrainTravelController;
import pt.ipp.isep.dei.domain.LocomotiveType;
import pt.ipp.isep.dei.domain.StationType;

import java.util.Scanner;

/**
 * User interface for verifying train travel feasibility.
 * It prompts the user for input files and train configuration,
 * builds the railway graph, and evaluates travel possibility.
 */
public class VerifyTrainTravelUI {

    /** Controller responsible for the verification logic. */
    private final VerifyTrainTravelController controller = new VerifyTrainTravelController();

    /** Scanner for reading user input. */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Starts the UI process:
     * - Prompts for file paths and train type settings.
     * - Loads data and builds the graph.
     * - Verifies if travel is possible.
     * - Optionally visualizes the graph.
     */
    public void start() {
        System.out.print("Enter path to **stations** CSV file: ");
        String stationsFile = scanner.nextLine();

        System.out.print("Enter path to **lines** CSV file: ");
        String linesFile = scanner.nextLine();

        LocomotiveType locomotiveType = chooseLocomotiveType();
        controller.setType(locomotiveType);

        StationType stationType = chooseStationType();
        controller.setStationType(stationType);

        controller.buildGraphFromFiles(stationsFile, linesFile);
        boolean result = controller.verify();

        System.out.println("Travel possible: " + (result ? "YES" : "NO"));

        controller.visualizeGraph();
    }

    /**
     * Prompts the user to select a locomotive type.
     *
     * @return the selected {@link LocomotiveType}, or {@code null} if invalid
     */
    private LocomotiveType chooseLocomotiveType() {
        System.out.println("Choose Locomotive Type:");
        System.out.println("1. ELECTRIC");
        System.out.println("2. DIESEL");
        System.out.println("3. STEAM");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        return switch (choice) {
            case 1 -> LocomotiveType.ELECTRIC;
            case 2 -> LocomotiveType.DIESEL;
            case 3 -> LocomotiveType.STEAM;
            default -> null;
        };
    }

    /**
     * Prompts the user to select a station type.
     *
     * @return the selected {@link StationType}, or {@code null} if invalid
     */
    private StationType chooseStationType() {
        System.out.println("Choose Station Type:");
        System.out.println("1. DEPOT");
        System.out.println("2. STATION");
        System.out.println("3. TERMINAL");

        int choice = scanner.nextInt();
        scanner.nextLine();

        return switch (choice) {
            case 1 -> StationType.DEPOT;
            case 2 -> StationType.STATION;
            case 3 -> StationType.TERMINAL;
            default -> null;
        };
    }
}
