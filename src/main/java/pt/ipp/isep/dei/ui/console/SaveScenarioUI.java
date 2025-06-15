package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.SaveScenarioController;

import java.util.List;
import java.util.Scanner;

/**
 * Interface de consola para salvar um cenário selecionado em ficheiro serializado.
 */
public class SaveScenarioUI {

    private final SaveScenarioController controller = new SaveScenarioController();

    /**
     * Inicia o processo de salvar um cenário.
     * Mostra os cenários disponíveis, permite a seleção por número,
     * e solicita o nome do ficheiro para salvar o cenário.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        int scenarioCount = controller.getScenarioCount();
        if (scenarioCount == 0) {
            System.out.println("No available scenarios to save.");
            return;
        }

        System.out.println("Available Scenarios:");
        List<String> scenarioNames = controller.getScenarioNames();
        for (int i = 0; i < scenarioNames.size(); i++) {
            System.out.println((i + 1) + ". " + scenarioNames.get(i));
        }

        System.out.print("Choose the scenario number to save: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline

        System.out.print("Serialized File name (e.g., scenario.ser): ");
        String filename = scanner.nextLine();

        boolean success = controller.saveScenarioByIndex(index, filename);
        if (success) {
            System.out.println("Scenario saved successfully.");
        } else {
            System.out.println("Failed to save scenario.");
        }
    }
}
