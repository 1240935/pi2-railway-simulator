package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.RailwayMaintenanceController;
import pt.ipp.isep.dei.domain.MaintenanceType;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based User Interface for the Railway Maintenance feature.
 * <p>
 * This UI allows the player to:
 * <ul>
 *   <li>Load station and line data from CSV files.</li>
 *   <li>Select maintenance type (full or electrified lines only).</li>
 *   <li>View possible starting stations for a maintenance route that
 *       passes through each railway line exactly once.</li>
 *   <li>Select a starting station from the available options.</li>
 *   <li>Visualize the railway network graph.</li>
 * </ul>
 */
public class RailwayMaintenanceUI {

    private RailwayMaintenanceController controller;
    private Scanner scanner;

    /**
     * Constructs a new RailwayMaintenanceUI instance
     * initializing the controller and scanner for console input.
     */
    public RailwayMaintenanceUI() {
        this.controller = new RailwayMaintenanceController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the UI flow, guiding the player through:
     * 1. Loading the stations and lines CSV files.
     * 2. Choosing the maintenance type.
     * 3. Displaying possible starting stations for the maintenance route.
     * 4. Selecting the starting station.
     * 5. Visualizing the railway graph.
     */
    public void start() {
        // Step 1: Load files
        System.out.print("Enter the path to the stations CSV file: ");
        String stationFile = scanner.nextLine().trim();

        System.out.print("Enter the path to the lines CSV file: ");
        String lineFile = scanner.nextLine().trim();

        controller.setFiles(stationFile, lineFile);
        controller.loadData();

        // Step 2: Choose maintenance type
        MaintenanceType type = promptMaintenanceType();
        controller.setMaintenanceType(type);

        // Step 3: Get possible starting stations
        List<String> possibleStarts = controller.getPossibleStartingStationNames();

        if (possibleStarts.isEmpty()) {
            System.out.println("No possible maintenance route available.");
            controller.visualizeGraph();
            return;
        }

        System.out.println("Possible starting stations:");
        for (String name : possibleStarts) {
            System.out.println("- " + name);
        }

        // Step 4: Player chooses a starting station
        System.out.print("Enter the name of your chosen starting station: ");
        String chosenName = scanner.nextLine();

        boolean valid = controller.setSelectedStartStation(chosenName);
        if (!valid) {
            System.out.println("Invalid station selected. Please try again.");
            return;
        }

        System.out.println("Starting station '" + chosenName + "' selected.");

        // Step 5: Visualize graph
        controller.visualizeGraph();
    }

    /**
     * Prompts the user to choose the maintenance type.
     * Repeats until a valid option is entered.
     *
     * @return the selected MaintenanceType enum value
     */
    private MaintenanceType promptMaintenanceType() {
        while (true) {
            System.out.println("\nChoose maintenance type:");
            System.out.println("1 - Full (all lines)");
            System.out.println("2 - Electrified lines only");
            System.out.print("Enter your choice (1 or 2): ");

            String input = scanner.nextLine().trim();

            if (input.equals("1")) {
                return MaintenanceType.FULL;
            } else if (input.equals("2")) {
                return MaintenanceType.ELECTRIFIED;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
