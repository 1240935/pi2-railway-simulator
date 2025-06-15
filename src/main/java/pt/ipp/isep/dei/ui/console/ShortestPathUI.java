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
     * Inicia a interface para cálculo do caminho mais curto.
     * Solicita ao utilizador os ficheiros de estações, linhas e rota,
     * carrega os dados, calcula o caminho mais curto passando por todos os waypoints,
     * apresenta o resultado e visualiza o grafo.
     */
    public void start() {
        // Solicita ficheiros para construir o grafo de estações e linhas
        System.out.print("Enter stations file path: ");
        String stationsFile = scanner.nextLine();
        System.out.print("Enter lines file path: ");
        String linesFile = scanner.nextLine();
        controller.setFiles(stationsFile, linesFile);

        // Solicita ficheiro com a rota (start;waypoints;end)
        System.out.print("Enter route file path: ");
        String routeFile = scanner.nextLine();

        try {
            controller.loadRouteFromFile(routeFile);
        } catch (RuntimeException e) {
            System.out.println("Error loading route: " + e.getMessage());
            return;
        }

        // Calcula o caminho mais curto considerando waypoints
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
            System.out.println("Total Cost (Distance): " + cost + "km");
        }

        // Visualiza o grafo e o caminho encontrado
        controller.visualizeGraph();
    }
}
