package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.ShortestPathController;

import java.util.List;
import java.util.Scanner;

public class ShortestPathUI {

    private final ShortestPathController controller;
    private final Scanner scanner;

    public ShortestPathUI() {
        this.controller = new ShortestPathController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the interface for calculating the shortest path.
     * Asks the user for the stations, lines, and route files,
     * loads the data, calculates the shortest path passing through all waypoints,
     * displays the result, and visualizes the graph.
     */
    public void start() {
        // Request files to build the graph of stations and lines
        System.out.print("Enter stations file path: ");
        String stationsFile = scanner.nextLine();
        System.out.print("Enter lines file path: ");
        String linesFile = scanner.nextLine();
        controller.setFiles(stationsFile, linesFile);

        // Request the file with the route (start;waypoints;end)
        System.out.print("Enter route file path: ");
        String routeFile = scanner.nextLine();

        try {
            controller.loadRouteFromFile(routeFile);
        } catch (RuntimeException e) {
            System.out.println("Error loading route: " + e.getMessage());
            return;
        }

        // Calculate the shortest path considering waypoints
        List<String> path = controller.getShortestPathAsNames(
                controller.getStartStation(),
                controller.getEndStation(),
                controller.getWaypoints()
        );
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
            System.out.println("Total Cost (Distance): " + cost + " km");
        }

        // Visualize the graph and the found path
        controller.visualizeGraph();
    }
}
