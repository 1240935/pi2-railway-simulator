package pt.ipp.isep.dei.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a city in the game map.
 * <p>
 * A {@code City} has a name, a central position, and a list of associated {@link CityBlock}s.
 * It defines specific cargo types that it supplies and demands.
 */
public class City {

    /** The central position of the city on the map. */
    private Position position;

    /** The name of the city. */
    private String name;

    /** The list of city blocks that make up the city. */
    private List<CityBlock> blocks;

    /**
     * Constructs a new {@code City} with the specified name, position, and city blocks.
     *
     * @param name     the name of the city
     * @param position the central position of the city
     * @param blocks   the list of blocks that compose the city
     */
    public City(String name, Position position, List<CityBlock> blocks) {
        this.name = name;
        this.position = position;
        this.blocks = blocks;
    }

    /**
     * Returns the name of the city.
     *
     * @return the city's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the central position of the city.
     *
     * @return the position of the city
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns the list of city blocks that make up the city.
     *
     * @return the list of {@link CityBlock}s
     */
    public List<CityBlock> getBlocks() {
        return blocks;
    }

    /**
     * Returns a list of cargo types that the city supplies.
     * Currently includes:
     * <ul>
     *     <li>{@link ResourceType#PASSENGERS}</li>
     *     <li>{@link ResourceType#MAIL}</li>
     * </ul>
     *
     * @return a list of supplied cargo types
     */
    public List<ResourceType> getSuppliedCargoes() {
        return Arrays.asList(ResourceType.PASSENGERS, ResourceType.MAIL);
    }

    /**
     * Returns a list of cargo types that the city demands.
     * Currently includes:
     * <ul>
     *     <li>{@link ResourceType#PASSENGERS}</li>
     *     <li>{@link ResourceType#MAIL}</li>
     *     <li>{@link ResourceType#GOODS}</li>
     *     <li>{@link ResourceType#FOOD}</li>
     * </ul>
     *
     * @return a list of demanded cargo types
     */
    public List<ResourceType> getDemandedCargoes() {
        return Arrays.asList(ResourceType.PASSENGERS, ResourceType.MAIL, ResourceType.GOODS, ResourceType.FOOD);
    }
}
