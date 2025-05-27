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
     * Starts the interactive process for industry creation via console input.
     * <ol>
     *     <li>Displays a list of available maps and lets the user choose one</li>
     *     <li>Prompts the user to input X and Y coordinates for the industry</li>
     *     <li>Validates the coordinates with the selected map</li>
     *     <li>Displays a list of industry types and lets the user choose one</li>
     *     <li>Creates the industry and confirms its creation</li>
     * </ol>
     * <p>
     * If invalid coordinates are entered, the process is terminated with a warning.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available maps:");
        List<String> mapNames = controller.getAvailableMapNames();
        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ": " + mapNames.get(i));
        }

        System.out.print("Select map by number: ");
        int mapIndex = scanner.nextInt() - 1;

        System.out.print("Enter X coordinate: ");
        int x = scanner.nextInt();
        System.out.print("Enter Y coordinate: ");
        int y = scanner.nextInt();


        System.out.println("Available industry types:");
        List<String> industryTypeNames = controller.getAvailableIndustryTypeNames();
        for (int i = 0; i < industryTypeNames.size(); i++) {
            System.out.println(i + ": " + industryTypeNames.get(i));
        }

        System.out.print("Select industry type: ");
        int industryTypeIndex = scanner.nextInt();

        controller.createIndustry(controller.getIndustryTypeByIndex(industryTypeIndex),controller.createPosition(x,y),
                controller.getMapByIndex(mapIndex).getSize(), mapIndex);
        System.out.println("Industry created successfully!");


    }
}
