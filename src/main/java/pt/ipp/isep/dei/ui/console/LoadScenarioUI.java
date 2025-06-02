package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.LoadScenarioController;
import java.util.Scanner;

public class LoadScenarioUI {

    private final LoadScenarioController controller = new LoadScenarioController();

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter serialized file path: ");
        String filePath = scanner.nextLine();

        boolean success = controller.loadSerializedScenario(filePath);
    }
}