package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.VerifyTrainTravelController;
import pt.ipp.isep.dei.domain.LocomotiveType;
import pt.ipp.isep.dei.domain.StationType;


import java.util.Scanner;

public class VerifyTrainTravelUI {

    private final VerifyTrainTravelController controller = new VerifyTrainTravelController();
    private final Scanner scanner = new Scanner(System.in);

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
