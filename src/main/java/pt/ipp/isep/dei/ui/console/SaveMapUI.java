package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SaveMapController;
import java.util.Scanner;

public class SaveMapUI {

    private final SaveMapController controller = new SaveMapController();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        int mapCount = controller.getMapCount();
        if (mapCount == 0) {
            System.out.println("No available maps to save.");
            return;
        }

        System.out.println("Available Maps:");
        var mapNames = controller.getMapNames();
        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ". " + mapNames.get(i));
        }

        System.out.print("Choose the map number to save: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consumir newline

        System.out.print("CSV file name (ex: mapa.csv): ");
        String filename = scanner.nextLine();

        boolean success = controller.saveMapByIndex(index, filename);
        if (success) {
            System.out.println("Map saved with success.");
        } else {
            System.out.println("Invalid Index, map not saved.");
        }
    }
}
