package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.LoadScenarioController;
import java.util.Scanner;

/**
 * Interface de consola para carregar um cenário a partir de um ficheiro serializado.
 */
public class LoadScenarioUI {

    private final LoadScenarioController controller = new LoadScenarioController();

    /**
     * Inicia o processo de carregamento do cenário.
     * Solicita ao utilizador o caminho do ficheiro serializado e tenta carregar o cenário.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter serialized file path: ");
        String filePath = scanner.nextLine();

        boolean success = controller.loadSerializedScenario(filePath);
        if (success) {
            System.out.println("Scenario loaded successfully.");
        } else {
            System.out.println("Failed to load scenario.");
        }
    }
}
