package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.LoadMapController;

import java.util.Scanner;

/**
 * Console interface for loading a map from a serialized file.
 */
public class LoadMapUI {

    private final LoadMapController controller = new LoadMapController();

    /**
     * Starts the process of loading the map.
     * Prompts the user for the path of the serialized file and attempts to load the map.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the serialized file to load the map (e.g., mapa.ser): ");
        String filePath = scanner.nextLine();

        boolean success = controller.loadSerializedMap(filePath);
        if (success) {
            System.out.println("Map loaded successfully.");
        } else {
            System.out.println("Error loading map.");
        }
    }
}
