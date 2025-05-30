package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SaveMapController;
import pt.ipp.isep.dei.domain.Map;

import java.util.ArrayList;
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
        var maps = controller.getMaps();
        ArrayList<String> mapNames = new ArrayList<>();

        for (Map map : maps) {
            mapNames.add(map.getName());
        }
        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ". " + mapNames.get(i));
        }

        System.out.print("Choose the map number to save: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consumir newline

        System.out.print("Serialized File name (ex: mapa.ser): ");
        String filename = scanner.nextLine();

        // Aqui chama o método de serialização
        boolean success = controller.saveMapToFile(controller.getMaps().get(index), filename);
        if (success) {
            System.out.println("Map saved successfully.");
        } else {
            System.out.println("Failed to save map.");
        }
    }
}
