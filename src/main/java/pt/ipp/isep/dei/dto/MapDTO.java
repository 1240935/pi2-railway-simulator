package pt.ipp.isep.dei.dto;

/**
 * Data Transfer Object (DTO) for representing a Map with basic properties.
 */
public class MapDTO {
    private int size;
    private String name;
    private int scale;

    /**
     * Constructs a MapDTO with the specified size, name, and scale.
     *
     * @param size the size of the map
     * @param name the name of the map
     * @param scale the scale of the map
     */
    public MapDTO(int size, String name, int scale) {
        this.size = size;
        this.name = name;
        this.scale = scale;
    }

    /**
     * Constructs a MapDTO with default values:
     * size = 1, name = "Default", scale = 1.
     */
    public MapDTO(){
        this.size = 1;
        this.name = "Default";
        this.scale = 1;
    }

    /**
     * Returns the size of the map.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the name of the map.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the scale of the map.
     *
     * @return the scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * Sets the name of the map.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the scale of the map.
     *
     * @param scale the new scale
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * Sets the size of the map.
     *
     * @param size the new size
     */
    public void setSize(int size) {
        this.size = size;
    }
}
