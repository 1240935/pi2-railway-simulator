package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.ResourceType;

/**
 * Data Transfer Object (DTO) representing a cargo item.
 * Contains the resource type and the quantity being transported or stored.
 */
public class CargoDTO {

    /**
     * The type of resource in the cargo.
     */
    private ResourceType type;

    /**
     * The quantity of the resource in the cargo.
     */
    private int quantity;

    /**
     * Default constructor for {@code CargoDTO}.
     */
    public CargoDTO() {}

    /**
     * Constructs a {@code CargoDTO} with the specified type and quantity.
     *
     * @param type the resource type
     * @param quantity the quantity of the resource
     */
    public CargoDTO(ResourceType type, int quantity) {
        this.quantity = quantity;
        this.type = type;
    }

    /**
     * Gets the quantity of the resource.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the resource.
     *
     * @param quantity the new quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the resource type.
     *
     * @return the resource type
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * Sets the resource type.
     *
     * @param type the new resource type to set
     */
    public void setType(ResourceType type) {
        this.type = type;
    }

    /**
     * Returns a string representation of the cargo,
     * including its type and quantity.
     *
     * @return a string describing the cargo
     */
    @Override
    public String toString() {
        return ("Type: " + type + "\nQuantity: " + quantity);
    }
}
