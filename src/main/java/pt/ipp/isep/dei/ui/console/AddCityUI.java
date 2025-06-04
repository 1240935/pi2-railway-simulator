package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.controller.AddCityController;
import pt.ipp.isep.dei.dto.CityBlockDTO;
import pt.ipp.isep.dei.dto.CityDTO;

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

        CityBlockDTO cityBlockDTO = new CityBlockDTO();
        cityBlockDTO.setxCoordinate(x);
        cityBlockDTO.setyCoordinate(y);

        if (mode == 1) {
            cityBlockDTO.setRandomize(true);
        } else {
            cityBlockDTO.setRandomize(false);
            System.out.println("Please enter the coordinates for each block:");
                int[] blockX = new int[blocks];
                int[] blockY = new int[blocks];
            for (int i = 0; i < blocks; i++) {

                System.out.printf("Block %d - Type the x coordinate: ", i + 1);
                blockX[i] = scanner.nextInt();

                System.out.printf("Block %d - Type the y coordinate: ", i + 1);
                blockY[i] = scanner.nextInt();
                scanner.nextLine();


            }
            cityBlockDTO.setBlocks(blocks);
            cityBlockDTO.setPositions(controller.createPositions(blockX,blockY));
        }
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName(name);
        cityDTO.setMapSize(mapSize);
        cityDTO.setxCoordinate(x);
        cityDTO.setyCoordinate(y);
        cityDTO.setCityBlocks(controller.createBlocks(cityBlockDTO));
        controller.createCity(cityDTO);

    }
}
