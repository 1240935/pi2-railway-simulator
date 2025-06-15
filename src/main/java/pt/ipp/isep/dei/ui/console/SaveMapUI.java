package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SaveMapController;

import java.util.List;
import java.util.Scanner;

/**
 * Interface de consola para salvar mapas serializados.
 * Permite ao utilizador escolher um mapa disponível e guardar num ficheiro.
 */
public class SaveMapUI {

    private final SaveMapController controller = new SaveMapController();

    /**
     * Inicia o processo de salvar um mapa.
     * Lista os mapas disponíveis, pede ao utilizador para escolher um e o nome do ficheiro de destino.
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

        System.out.print("Serialized File name (e.g., mapa.ser): ");
        String filename = scanner.nextLine();

        boolean success = controller.saveMapByIndex(index, filename);
        if (success) {
            System.out.println("Map saved successfully.");
        } else {
            System.out.println("Failed to save map.");
        }
    }
}
