package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.LoadSimulatorController;

import java.util.Scanner;

/**
 * Interface de consola para carregar um simulador previamente serializado.
 */
public class LoadSimulatorUI {
    private final LoadSimulatorController controller = new LoadSimulatorController();

    /**
     * Inicia o processo de carregamento de um simulador a partir de um ficheiro .ser.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path of the serialized file to load the simulator (e.g., simulator.ser): ");
        String filePath = scanner.nextLine();

        boolean success = controller.loadSerializedSimulator(filePath);
        if (success) {
            System.out.println("Simulator loaded successfully and set as active.");
        } else {
            System.out.println("Error loading simulator.");
        }
    }
}
