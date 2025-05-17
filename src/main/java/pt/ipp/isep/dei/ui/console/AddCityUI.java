package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AddCityController;

import java.util.List;
import java.util.Scanner;

/**
 * User Interface class responsible for managing the console interaction
 * for adding a new city to a selected map.
 *
 * This UI guides the user through selecting a map, entering city details,
 * choosing block placement mode (automatic or manual), and confirming creation.
 */
public class AddCityUI {
    private AddCityController controller;
    private final Scanner scanner;

    /**
     * Constructs an AddCityUI instance initializing the controller
     * and the input scanner.
     */
    public AddCityUI() {
        controller = new AddCityController();
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the process of adding a new city through console interaction.
     *
     * The method performs the following steps:
     * 1. Displays a list of available maps to the user.
     * 2. Allows the user to select a map by entering a number.
     * 3. Prompts the user to input the city's name and validates it.
     * 4. Prompts for the city's center coordinates and validates them.
     * 5. Asks for the number of city blocks to be created.
     * 6. Provides an option to place city blocks automatically (random positions near city center)
     *    or manually (user inputs each block's coordinates).
     * 7. Validates each block's coordinates if manual mode is selected.
     * 8. Creates the city with the specified information.
     * 9. Displays a confirmation message upon successful creation.
     */
    public void start() {
        List<String> names = controller.getAvailableMapNames();
        System.out.println("Available maps:");
        for (int i = 0; i < names.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, names.get(i));
        }

        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Select a map by number: ");
            if (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > names.size());
        controller.setMap(controller.chooseMap(controller.getAvailableMaps(), choice));
        scanner.nextLine();

        System.out.println("Write a name for your city:");
        String name = scanner.nextLine();
        while (!controller.checkName(name)) {
            System.out.println("Invalid name, try again:");
            name = scanner.nextLine();
        }
        controller.setSelectedName(name);

        System.out.println("Type the x coordinate of the city:");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int x = scanner.nextInt();
        System.out.println("Type the y coordinate of the city:");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int y = scanner.nextInt();
        if (!controller.checkCoordinates(controller.createPosition(x, y))) {
            System.out.println("Invalid coordinates.");
            System.exit(1);
        }
        controller.setCenter(controller.createPosition(x, y));
        System.out.println("Type the number of city blocks:");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }

        int blocks = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Choose block placement mode:");
        System.out.println("1. Automatic (random blocks around city center)");
        System.out.println("2. Manual (enter coordinates for each block)");
        int mode = 0;
        do {
            System.out.print("Enter 1 or 2: ");
            if (scanner.hasNextInt()) {
                mode = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.nextLine();
            }
        } while (mode != 1 && mode != 2);

        if (mode == 1) {
            controller.setSelectedBlocks(controller.generateBlocks(controller.createPosition(x, y), blocks, true));
        } else {
            System.out.println("Please enter the coordinates for each block:");

            for (int i = 0; i < blocks; i++) {
                int blockX, blockY;

                System.out.printf("Block %d - Type the x coordinate: ", i + 1);
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.next();
                }
                blockX = scanner.nextInt();

                System.out.printf("Block %d - Type the y coordinate: ", i + 1);
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.next();
                }
                blockY = scanner.nextInt();
                scanner.nextLine();

                if (!controller.checkCoordinates(controller.createPosition(blockX, blockY))) {
                    System.out.println("Invalid coordinates. Please enter coordinates close to the city center.");
                    i--;
                } else {
                    controller.addSelectedPositions(blockX, blockY);
                }
            }
            controller.setSelectedBlocks(controller.createBlocks(controller.getSelectedPositions(), blocks));
        }
        controller.createCity();
        System.out.println("City created successfully!");
    }
}
