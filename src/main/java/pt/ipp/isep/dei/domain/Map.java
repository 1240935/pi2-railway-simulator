package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a map containing cities and industries with a specified size and name.
 * <p>
 * The map provides functionality to manage its elements and validate positions within its bounds.
 */
public class Map implements Serializable {


    /**
     * The name of the map.
     */
    private String name;

    /**
     * The size of the map (assumed to define both width and height as a square grid).
     */
    private int size;

    private int scale;

    /**
     * The list of industries placed on the map.
     */
    private List<Industry> industries;

    /**
     * The list of cities placed on the map.
     */
    private List<City> cities;

    /**
     * Constructs a new {@code Map} instance with the specified name, size, and scale.
     *
     * @param size  the size of the map grid
     * @param name  the name of the map
     * @param scale the scale of the map
     */
    public Map(int size, String name, int scale) {
        this.size = size;
        this.name = name;
        this.scale = scale;
        this.cities = new ArrayList<>();
        this.industries = new ArrayList<>();
    }

    /**
     * Returns the name of the map.
     *
     * @return the map name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the size of the map.
     *
     * @return the map size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the name of the map.
     *
     * @param name the new map name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the size of the map.
     *
     * @param size the new map size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Checks if a given position is within the bounds of the map.
     *
     * @param position the position to validate
     * @return {@code true} if the position is within bounds, {@code false} otherwise
     */
    public boolean checkCoordinates(Position position) {
        return position.getxCoordinate() >= 0 && position.getxCoordinate() < size &&
                position.getyCoordinate() >= 0 && position.getyCoordinate() < size;
    }

    /**
     * Adds a city to the map.
     *
     * @param city the city to add
     */
    public void addCity(City city) {
        cities.add(city);
    }

    /**
     * Returns the list of cities on the map.
     *
     * @return a list of {@link City} objects
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     * Adds an industry to the map.
     *
     * @param industry the industry to add
     */
    public void addIndustry(Industry industry) {
        industries.add(industry);
    }

    /**
     * Returns the list of industries on the map.
     *
     * @return a list of {@link Industry} objects
     */
    public List<Industry> getIndustries() {
        return industries;
    }

    /**
     * Checks if the given name is valid (non-null and contains only letters and spaces).
     *
     * @param name the name to check
     * @return {@code true} if the name is valid, {@code false} otherwise
     */
    public static boolean checkName(String name) {
        return name != null && name.matches("^[A-Za-z ]+$");
    }

    /**
     * Checks if the given size is valid (greater than zero).
     *
     * @param size the size to check
     * @return {@code true} if the size is valid, {@code false} otherwise
     */
    public boolean checkSize(int size) {
        return size > 0;
    }

    /**
     * Checks if the given scale is valid (greater than zero).
     *
     * @param scale the scale to check
     * @return {@code true} if the scale is valid, {@code false} otherwise
     */
    public boolean checkScale(int scale) {
        return scale > 0;
    }

    /**
     * Returns the scale of the map.
     *
     * @return the map scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * Sets the scale of the map.
     *
     * @param scale the new map scale
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * Returns a string representation of the map.
     *
     * @return a string containing the map's name and size
     */
    @Override
    public String toString() {
        return "Map{name='" + name + "', size=" + size + "}";
    }
}
