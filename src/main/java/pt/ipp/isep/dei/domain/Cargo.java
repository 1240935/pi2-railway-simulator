package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents a quantity of a specific resource type carried as cargo.
 */
public class Cargo implements Serializable {

    private ResourceType type;
    private int quantity;

    /**
     * Constructs a new Cargo with the given resource type and quantity.
     *
     * @param type the type of resource carried
     * @param quantity the amount of resource
     */
    public Cargo(ResourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    /**
     * Returns the type of resource in this cargo.
     *
     * @return the resource type
     */
    public ResourceType getType() {
        return type;
    }

    /**
     * Returns the quantity of the resource in this cargo.
     *
     * @return the quantity of the resource
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the resource in this cargo.
     *
     * @param quantity the new quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int addQuantity(int other) {
        this.quantity += other;
        return this.quantity;
    }


    /**
     * Returns a string representation of the cargo.
     *
     * @return a string describing the cargo type
     */
    @Override
    public String toString() {
        return type.name();
    }
}
