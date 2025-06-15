package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.LoadMapController;

import java.util.Scanner;

/**
 * Interface de consola para carregar um mapa a partir de um ficheiro serializado.
 */
public class LoadMapUI {

    private final LoadMapController controller = new LoadMapController();

    /**
     * Inicia o processo de carregamento do mapa.
     * Solicita ao utilizador o caminho do ficheiro serializado e tenta carregar o mapa.
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
