package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateMapController;
import java.util.Scanner;

/**
 * Provides a console-based user interface for creating a new map.
 * <p>
 * This class handles user input to gather map configuration such as size and name,
 * and delegates the creation logic to the {@link CreateMapController}.
 * </p>
 */
public class CreateMapUI {

    /**
     * Controller responsible for handling the business logic of map creation.
     */
    private CreateMapController controller;

    /**
     * Constructs a new instance of {@code CreateMapUI}, initializing the controller.
     */
    public CreateMapUI() {
        controller = new CreateMapController();
    }

    /**
     * Starts the interactive process of creating a new map.
     * <p>
     * Prompts the user to enter the map size and name. The input is validated and passed
     * to the controller. If the map is successfully created, a success message is displayed.
     * </p>
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter map size: ");
        int size = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter map name: ");
        String name = scanner.nextLine();

        System.out.print("Enter map scale (kms): ");
        int scale = scanner.nextInt();

        controller.createMap(controller.createMapDTO(size,name,scale));
    }
}
