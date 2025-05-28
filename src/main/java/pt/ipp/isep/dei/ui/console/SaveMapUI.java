package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SaveMapController;
import java.util.Scanner;

public class SaveMapUI {

    private final SaveMapController controller = new SaveMapController();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        int mapCount = controller.getMapCount();
        if (mapCount == 0) {
            System.out.println("Não há mapas disponíveis para guardar.");
            return;
        }

        System.out.println("Mapas disponíveis:");
        var mapNames = controller.getMapNames();
        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ". " + mapNames.get(i));
        }

        System.out.print("Escolha o número do mapa a guardar: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consumir newline

        System.out.print("Nome do ficheiro CSV (ex: mapa.csv): ");
        String filename = scanner.nextLine();

        boolean success = controller.saveMapByIndex(index, filename);
        if (success) {
            System.out.println("Mapa guardado com sucesso.");
        } else {
            System.out.println("Índice inválido. Mapa não guardado.");
        }
    }
}
