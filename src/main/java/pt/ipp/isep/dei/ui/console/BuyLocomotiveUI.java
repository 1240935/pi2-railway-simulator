package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.dto.LocomotiveDTO;

import java.util.List;
import java.util.Scanner;

/**
 * Console UI class responsible for handling the buying of locomotives by a player.
 * This class interacts with the user through the console, allowing them to
 * select a scenario, choose a locomotive model, and confirm purchase based on their budget.
 */
public class BuyLocomotiveUI {

    private final BuyLocomotiveController controller;
    private final Scanner scanner;

    /**
     * Constructs a new BuyLocomotiveUI initializing the controller and scanner.
     */
    public BuyLocomotiveUI() {
        this.controller = new BuyLocomotiveController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the buying process for the given player.
     * Presents available scenarios and locomotive models,
     * displays costs and player budget, and requests confirmation before purchase.
     *
     * @param player the Player who wants to buy a locomotive; must not be null.
     */
    public void start(Player player) {
        // Set player in the controller for budget management
        controller.setPlayer(player);
        LocomotiveDTO locomotiveDTO = new LocomotiveDTO();

        List<String> scenarios = controller.getAvailableScenarioNames();
        if (scenarios.isEmpty()) {
            System.out.println("No scenarios available.");
            return;
        }

        System.out.println("Available Scenarios:");
        for (int i = 0; i < scenarios.size(); i++) {
            System.out.println((i + 1) + ". " + scenarios.get(i));
        }

        int scenarioIndex;
        while (true) {
            System.out.print("Select scenario (1-" + scenarios.size() + "): ");
            String input = scanner.nextLine();
            try {
                scenarioIndex = Integer.parseInt(input);
                if (scenarioIndex >= 1 && scenarioIndex <= scenarios.size()) {
                    break;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid input. Try again.");
        }

        List<String> models = controller.getAvailableModelDescriptions(scenarioIndex - 1);
        if (models.isEmpty()) {
            System.out.println("No locomotive models available for the selected scenario.");
            return;
        }

        System.out.println("Available Locomotive Models:");
        for (int i = 0; i < models.size(); i++) {
            System.out.println((i + 1) + ". " + models.get(i));
        }

        int modelIndex;
        while (true) {
            System.out.print("Select model (1-" + models.size() + "): ");
            String input = scanner.nextLine();
            try {
                modelIndex = Integer.parseInt(input);
                if (modelIndex >= 1 && modelIndex <= models.size()) {
                    break;
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid input. Try again.");
        }

        // Show budget and cost, then ask for confirmation
        System.out.println("Player budget: " + player.getBudget());
        System.out.println("Locomotive cost: " + controller.getModelByIndex(modelIndex - 1, scenarioIndex - 1).getCost());
        System.out.print("Do you want to proceed with the purchase? (y/n): ");

        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("y")) {
            System.out.println("Purchase cancelled.");
            return;
        }
        locomotiveDTO.setLocomotiveModel(controller.getModelByIndex(modelIndex - 1, scenarioIndex - 1));
        controller.createLocomotive(locomotiveDTO, scenarioIndex - 1);

    }
}
