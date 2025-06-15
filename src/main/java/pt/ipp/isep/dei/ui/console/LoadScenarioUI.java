package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.LoadScenarioController;
import java.util.Scanner;

/**
 * Console interface for loading a scenario from a serialized file.
 */
public class LoadScenarioUI {

    private final LoadScenarioController controller = new LoadScenarioController();

    /**
     * Starts the process of loading the scenario.
     * Prompts the user for the path of the serialized file and attempts to load the scenario.
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
