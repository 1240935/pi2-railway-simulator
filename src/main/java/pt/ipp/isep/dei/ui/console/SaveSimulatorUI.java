package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SaveSimulatorController;
import java.util.Scanner;

public class SaveSimulatorUI {
    private final SaveSimulatorController controller = new SaveSimulatorController();

    public void start() {
        if (!controller.hasActiveSimulator()) {
            System.out.println("No active simulator to save.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Serialized File name (e.g., simulator.ser): ");
        String filename = scanner.nextLine();

        boolean success = controller.saveSimulatorToFile(filename);
        if (success) {
            System.out.println("Simulator saved successfully.");
        } else {
            System.out.println("Failed to save simulator.");
        }
    }
}