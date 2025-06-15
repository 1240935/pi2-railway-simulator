package pt.ipp.isep.dei.dto;

/**
 * Data Transfer Object (DTO) representing a mode of cargo transportation or handling.
 * This class encapsulates the name of the cargo mode.
 */
public class CargoModeDTO {

    /**
     * The name of the cargo mode.
     */
    private String name;

    /**
     * Default constructor for {@code CargoModeDTO}.
     */
    public CargoModeDTO() {}

    /**
     * Constructs a {@code CargoModeDTO} with the specified name.
     *
     * @param name the name of the cargo mode
     */
    public CargoModeDTO(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the cargo mode.
     *
     * @return the name of the cargo mode
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the cargo mode.
     *
     * @param name the new name of the cargo mode
     */
    public void setName(String name) {
        this.name = name;
    }
}
