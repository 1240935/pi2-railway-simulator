package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for creating and validating maps in the system.
 * Handles map name and size validation, and interacts with the repository to retrieve maps.
 */
public class CreateMapController {

    /**
     * Constructs a new {@code CreateMapController}.
     */
    public CreateMapController() {
    }

    /**
     * Searches for an existing map by its name in the repository.
     *
     * @param name the name of the map to search for
     * @return the {@link Map} object if found, or {@code null} if no map with the given name exists
     */
    public Map getMap(String name) {
        for (Map m : Repositories.getInstance().getMapRepository().getMaps()) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Checks whether a map name is valid.
     * A valid name contains only alphabetic characters and spaces.
     *
     * @param name the map name to validate
     * @return {@code true} if the name is valid, {@code false} otherwise
     */
    public boolean checkName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
    }

    /**
     * Validates the map size input.
     * Ensures the input is a numeric string representing a positive integer (no decimals).
     * If invalid, prints an error and terminates the program.
     *
     * @param sizeInput the size input string
     * @return the validated size as an integer if valid; otherwise, the program exits
     */
    public int checkSize(String sizeInput) {
        try {
            double temp = Double.parseDouble(sizeInput);

            if (temp % 1 != 0) {
                System.out.println("Invalid size! Must be a whole number (no decimals).");
                System.exit(1);
            }

            int size = (int) temp;

            if (size <= 0) {
                System.out.println("Invalid size! Must be a positive integer.");
                System.exit(1);
            }

            return size;

        } catch (NumberFormatException e) {
            System.out.println("Invalid size! Must be a number.");
            System.exit(1);
        }

        return -1;
    }

    /**
     * Creates a new map with the given size and name.
     * Validates the name before creation. If invalid, prints an error and terminates.
     *
     * @param size the size of the map
     * @param name the name of the map
     */
    public void createMap(int size, String name) {
        if (!checkName(name)) {
            System.out.println("Invalid Map name!");
            return;
        }

        Map map = new Map(name, size);
        Repositories.getInstance().getMapRepository().addMap(map);
    }

}
