package pt.ipp.isep.dei.dto;

/**
 * Data Transfer Object (DTO) representing a resource type with a name and associated value.
 */
public class ResourceTypeDTO {
    private int value;
    private String name;

    /**
     * Gets the value associated with this resource type.
     *
     * @return the value of the resource type
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value associated with this resource type.
     *
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the name of this resource type.
     *
     * @return the name of the resource type
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this resource type.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the resource type.
     *
     * @return the name and value formatted as "name (value)"
     */
    @Override
    public String toString() {
        return name + " (" + value + ")";
    }
}
