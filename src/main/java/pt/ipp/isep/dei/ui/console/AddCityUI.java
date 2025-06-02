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

    public AddCityUI() {
        this.controller = new AddCityController();
    }

    public void start() {
        List<String> names = controller.getAvailableMapNames();
        System.out.println("Available maps:");
        for (int i = 0; i < names.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, names.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select a map by number: ");
        int mapIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        int mapSize = controller.getMapByIndex(mapIndex).getSize();

        System.out.println("Write a name for your city:");
        String name = scanner.nextLine();

        System.out.println("Type the x coordinate of the city:");
        int x = scanner.nextInt();
        System.out.println("Type the y coordinate of the city:");
        int y = scanner.nextInt();

        System.out.println("Type the number of city blocks:");
        int blocks = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Choose block placement mode:");
        System.out.println("1. Automatic (random blocks around city center)");
        System.out.println("2. Manual (enter coordinates for each block)");
        int mode = scanner.nextInt();
        scanner.nextLine();

        if (mode == 1) {
            controller.createBlocksAutomatically(controller.createPosition(x, y), blocks, true);
        } else {
            System.out.println("Please enter the coordinates for each block:");

            for (int i = 0; i < blocks; i++) {
                int blockX, blockY;

                System.out.printf("Block %d - Type the x coordinate: ", i + 1);
                blockX = scanner.nextInt();

                System.out.printf("Block %d - Type the y coordinate: ", i + 1);
                blockY = scanner.nextInt();
                scanner.nextLine();

                controller.savePositions(controller.createPosition(blockX, blockY));

            }
            controller.createBlocksManually((controller.getPositions()), blocks);
        }
        controller.createCity(controller.createPosition(x, y), name, controller.getCityBlocks(), mapSize, mapIndex);
    }
}
