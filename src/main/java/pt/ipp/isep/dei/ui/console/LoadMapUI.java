package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.LoadMapController;

import java.util.Scanner;

public class LoadMapUI {

    private final LoadMapController controller = new LoadMapController();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the CSV file to load the map: ");
        String filePath = scanner.nextLine();

        boolean success = controller.loadMapFromCSV(filePath);
        if (!success) {
            System.out.println("Error loading map.");
        }
    }
}
