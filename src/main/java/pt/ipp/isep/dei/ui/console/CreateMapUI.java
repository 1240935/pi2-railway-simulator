package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.CreateMapController;
import java.util.Scanner;

/**
 * User interface class for creating a new map.
 * <p>
 * This class interacts with the user through the console to collect map name and size,
 * and delegates the creation to {@link CreateMapController}.
 */
public class CreateMapUI {
    /**
     * Controller responsible for handling the business logic of map creation.
     */
    private CreateMapController controller;

    /**
     * Constructs a new {@code CreateMapUI} and initializes the associated controller.
     */
    public CreateMapUI() {
        controller = new CreateMapController();
    }

    /**
     * Starts the map creation process by asking the user for the map name and size,
     * then delegates to the controller to create the map.
     * Displays a success message if the creation completes.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter map name: ");
        String name = scanner.nextLine();

        System.out.print("Enter map size: ");
        String sizeInput = scanner.nextLine();

        int size = controller.checkSize(sizeInput);

        controller.createMap(size, name);
        System.out.println("Map created successfully.");
    }
}
