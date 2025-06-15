package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SaveMapController;

import java.util.List;
import java.util.Scanner;

/**
 * Console interface to save serialized maps.
 * Allows the user to choose an available map and save it to a file.
 */
public class SaveMapUI {

    private final SaveMapController controller = new SaveMapController();

    /**
     * Starts the process of saving a map.
     * Lists available maps, asks the user to select one and enter the destination filename.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        int mapCount = controller.getMapCount();
        if (mapCount == 0) {
            System.out.println("No available maps to save.");
            return;
        }

        System.out.println("Available Maps:");
        List<String> mapNames = controller.getMapNames();
        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ". " + mapNames.get(i));
        }

        System.out.print("Choose the map number to save: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        System.out.print("Serialized File name (e.g., map.ser): ");
        String filename = scanner.nextLine();

        boolean success = controller.saveMapByIndex(index, filename);
        if (success) {
            System.out.println("Map saved successfully.");
        } else {
            System.out.println("Failed to save map.");
        }
    }
}
