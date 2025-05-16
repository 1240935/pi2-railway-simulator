package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateIndustryController;

import java.util.List;
import java.util.Scanner;

/**
 * Console-based UI for creating a new industry on a selected map.
 * <p>
 * This interface guides the user through:
 * <ul>
 *     <li>Choosing a map</li>
 *     <li>Entering coordinates for the new industry</li>
 *     <li>Selecting an industry type</li>
 *     <li>Creating and displaying the resulting industry</li>
 * </ul>
 *
 * It uses {@link CreateIndustryController} to handle business logic.
 */
public class CreateIndustryUI {

    /** Controller that manages the industry creation logic. */
    private final CreateIndustryController controller = new CreateIndustryController();

    /**
     * Starts the industry creation workflow:
     * <ol>
     *     <li>Displays available maps and prompts user to select one</li>
     *     <li>Prompts for valid coordinates within the selected map</li>
     *     <li>Displays available industry types and prompts user to select one</li>
     *     <li>Creates the industry and displays confirmation</li>
     * </ol>
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available maps:");
        List<String> mapNames = controller.getAvailableMapNames();
        for (int i = 0; i < mapNames.size(); i++) {
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

        String industryDescription = controller.createIndustry(x, y);
        System.out.println("Industry created successfully!");
        System.out.println(industryDescription);
    }
}
