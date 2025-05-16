package pt.ipp.isep.dei.ui.console;
import pt.ipp.isep.dei.controller.AddCityController;

import java.util.List;
import java.util.Scanner;

public class AddCityUI {
    private AddCityController controller;
    private final Scanner scanner;

    public AddCityUI() {
        controller = new AddCityController();
        scanner = new Scanner(System.in);
    }
    public void start() {
        System.out.println("==== Add City ====");
        List<String> names = controller.getAvailableMapNames();
        System.out.println("Available maps:");
        for (int i = 0; i < names.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, names.get(i));
        }

        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Select a map by number: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // discard non-integer input
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > names.size());
        controller.setMap(controller.chooseMap(controller.getAvailableMaps(), choice));

        System.out.println("Write a name for your city:");
        String name = scanner.nextLine();
        while(!controller.checkName(name)){
            System.out.println("Invalid name, try again:");
            name = scanner.nextLine();}
        controller.setSelectedName(name);

        System.out.println("Type the x coordinate of the city:");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); // discard non-integer input
            }
            int x = scanner.nextInt();
        System.out.println("Type the y coordinate of the city:");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); // discard non-integer input
            }
            int y = scanner.nextInt();
            if(!controller.checkCoordinates(controller.createPosition(x,y))){
                System.out.println("Invalid coordinates.");
                System.exit(1);
            }
            controller.setCenter(controller.createPosition(x,y));
        System.out.println("Type the number of city blocks:");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); // discard non-integer input
        }
        int blocks = scanner.nextInt();
        System.out.println("Please start entering the coordinates of the blocks: X:");

        String firstInput = scanner.nextLine();
        if (firstInput.isBlank()) {
            controller.setSelectedBlocks(controller.generateBlocks(controller.createPosition(x,y),blocks,true));
        }
        else {
            int firstx = Integer.parseInt(firstInput);
            System.out.println("Type the y coordinate of the city:");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next(); // discard non-integer input
            }
            int y1 = scanner.nextInt();
            if (!controller.checkCoordinates(controller.createPosition(firstx, y1))) {
                System.out.println("Invalid coordinates.");
                System.exit(1);
            }
            controller.addSelectedPositions(firstx, y1);

            for (int d = 1; d < blocks; d++) {
                System.out.println("Type the x coordinate of the city:");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.next(); // discard non-integer input
                }
                firstx = scanner.nextInt();
                System.out.println("Type the y coordinate of the city:");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.next(); // discard non-integer input
                }
                y1 = scanner.nextInt();
                if (!controller.checkCoordinates(controller.createPosition(x, y1))) {
                    System.out.println("Invalid coordinates.");
                    System.exit(1);
                }
                controller.addSelectedPositions(firstx, y1);
            }
            controller.setSelectedBlocks(controller.createBlocks(controller.getSelectedPositions(),blocks));
        }
                controller.showInfo();
                controller.createCity();
            }
        }
