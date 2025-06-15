package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.LoadSimulatorController;

import java.util.Scanner;

/**
 * Console interface to load a previously serialized simulator.
 */
public class LoadSimulatorUI {
    private final LoadSimulatorController controller = new LoadSimulatorController();

    /**
     * Starts the process of loading a simulator from a .ser file.
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
