package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.ShortestPathController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * UI class for finding the shortest path through ordered waypoints.
 * Complies with layered architecture — no domain class imports.
 */
public class ShortestPathUI {

    private final ShortestPathController controller;
    private final Scanner scanner;

    public ShortestPathUI() {
        this.controller = new ShortestPathController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        // Load files
        System.out.print("Enter stations file path: ");
        String stationsFile = scanner.nextLine();
        System.out.print("Enter lines file path: ");
        String linesFile = scanner.nextLine();
        controller.setFiles(stationsFile, linesFile);

        // Show available stations
        List<String> stationNames = controller.getAllStationNames();
        if (stationNames.isEmpty()) {
            System.out.println("No stations available. Please check the data files.");
            return;
        }

        System.out.println("\nAvailable Stations:");
        for (String name : stationNames) {
            System.out.println(" - " + name);
        }
        System.out.println();

        // Read start and end stations
        System.out.print("Enter START station name: ");
        String start = scanner.nextLine().trim();
        System.out.print("Enter END station name: ");
        String end = scanner.nextLine().trim();

        // Read waypoints
        List<String> waypoints = new ArrayList<>();
        System.out.println("Enter WAYPOINT stations (in order). Type 'done' to finish:");
        while (true) {
            System.out.print("Waypoint: ");
            String wp = scanner.nextLine().trim();
            if (wp.equalsIgnoreCase("done")) break;
            if (!wp.isEmpty()) waypoints.add(wp);
        }

        // Get path
        List<String> path = controller.getShortestPathAsNames(start, end, waypoints);
        int cost = controller.getLastPathCost();

        System.out.println();
        if (path.isEmpty()) {
            System.out.println("No valid path found through all waypoints.");
        } else {
            System.out.println("Shortest path:");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) System.out.print(" -> ");
            }
            System.out.println();
            System.out.println("Total Cost (Distance): " + cost);
        }

        controller.visualizeGraph();
    }
}
