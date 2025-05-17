package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateIndustryController;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based UI for creating a new industry on a selected map.
 * <p>
 * This interface guides the user through the following steps:
 * <ul>
 *     <li>Display available maps and prompt the user to select one</li>
 *     <li>Request valid coordinates for the industry location</li>
 *     <li>Display available industry types and prompt selection</li>
 *     <li>Create the industry and show a confirmation message</li>
 * </ul>
 * <p>
 * Relies on {@link CreateIndustryController} for handling domain logic and data access.
 */
public class CreateIndustryUI {

    /** Controller responsible for managing industry creation logic. */
    private final CreateIndustryController controller = new CreateIndustryController();

    /**
     * Starts the interactive process for industry creation.
     * <ol>
     *     <li>Shows the list of maps and gets user selection</li>
     *     <li>Prompts the user for X and Y coordinates</li>
     *     <li>Validates coordinates with the controller</li>
     *     <li>Shows available industry types and gets selection</li>
     *     <li>Delegates industry creation to the controller</li>
     * </ol>
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available maps:");
        List<String> mapNames = controller.getAvailableMapNames();
        for (int i = 1; i < mapNames.size(); i++) {
            System.out.println(i + ": " + mapNames.get(i));
        }

        System.out.print("Select map by number: ");
        int mapIndex = scanner.nextInt();
        controller.setMapByIndex(mapIndex);

        System.out.print("Enter X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter Y coordinate: ");
        int y = scanner.nextInt();

        if (!controller.checkCoordinates(x, y)) {
            System.out.println("Invalid coordinates. Exiting.");
            return;
        }

        System.out.println("Available industry types:");
        List<String> industryTypeNames = controller.getAvailableIndustryTypeNames();
        for (int i = 0; i < industryTypeNames.size(); i++) {
            System.out.println(i + ": " + industryTypeNames.get(i));
        }

        System.out.print("Select industry type: ");
        int industryTypeIndex = scanner.nextInt();
        controller.setIndustryTypeByIndex(industryTypeIndex);

        controller.createIndustry(x, y);
        System.out.println("Industry created successfully!");
    }
}
