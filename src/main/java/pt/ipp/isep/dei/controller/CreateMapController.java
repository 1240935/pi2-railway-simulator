package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for creating and validating maps in the system.
 * <p>
 * Handles validation of map name and size, and interacts with the repository
 * to manage map persistence.
 */
public class CreateMapController {

    /**
     * The name selected by the user for the map.
     */
    private String selectedName;

    /**
     * The size selected by the user for the map.
     */
    private int selectedSize;

    /**
     * Constructs a new {@code CreateMapController}.
     */
    public CreateMapController() {
    }

    /**
     * Retrieves an existing map by its name from the repository.
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
     * Validates the map name.
     * <p>
     * A valid name must contain only alphabetic characters and spaces. If the name is invalid,
     * prints an error message and terminates the program.
     *
     * @param name the map name to validate
     */
    public void checkName(String name) {
        if (!name.matches("^[a-zA-Z\\s]+$")) {
            System.out.println("Nome inválido");
            System.exit(1);
        }
    }

    /**
     * Validates the map size input.
     * <p>
     * Ensures the input is a numeric string representing a positive whole number.
     * If the input is invalid (e.g., not a number, negative, or decimal), an error is printed and the program exits.
     *
     * @param sizeInput the input string representing the size
     * @return the validated size as an integer
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

        return -1; // This line is technically unreachable, added to satisfy compiler
    }

    /**
     * Sets the selected map name to be used for creation.
     *
     * @param selectedName the name of the map
     */
    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    /**
     * Sets the selected map size to be used for creation.
     *
     * @param selectedSize the size of the map
     */
    public void setSelectedSize(int selectedSize) {
        this.selectedSize = selectedSize;
    }

    /**
     * Creates a new map using the selected name and size.
     * <p>
     * The newly created map is added to the repository.
     */
    public void createMap() {
        Map map = new Map(selectedName, selectedSize);
        Repositories.getInstance().getMapRepository().addMap(map);
    }
}
