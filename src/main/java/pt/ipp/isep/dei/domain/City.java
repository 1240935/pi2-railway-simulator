package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Represents a city in the game map.
 * <p>
 * A {@code City} has a name, a central position, and a list of associated {@link CityBlock}s.
 * It defines specific cargo types that it supplies and demands.
 * </p>
 */
public class City implements Serializable {


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

    /**
     * Creates a new Position object with given x and y coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return A new Position object.
     */
    public static Position createPosition(int x, int y) {
        return Position.createPosition(x, y);
    }

    /**
     * Creates a list of city blocks at specified positions.
     *
     * @param positions List of Position objects where blocks should be created.
     * @param cityBlocks Number of city blocks to create.
     * @return List of CityBlock objects created.
     */
    public static List<CityBlock> createBlocks(List<Position> positions, int cityBlocks){
        List<CityBlock> blocks = new ArrayList<>();
        for(int i = 0; i < cityBlocks; i++){
            CityBlock block = new CityBlock(positions.get(i));
            blocks.add(block);
        }
        return blocks;
    }

    /**
     * Checks if the provided city name is valid.
     * <p>
     * A valid name contains only letters (uppercase or lowercase) and spaces.
     * </p>
     *
     * @param name the city name to validate
     * @return {@code true} if the name is valid, {@code false} otherwise
     */
    public boolean checkName(String name) {
        return name != null && name.matches("^[A-Za-z ]+$");
    }

    /**
     * Checks if the provided list of city blocks is valid.
     * <p>
     * Validity criteria:
     * <ul>
     *     <li>No two city blocks share the same position.</li>
     *     <li>No city block occupies the same position as the city's central position.</li>
     * </ul>
     * </p>
     *
     * @param blocks the list of city blocks to validate
     * @return {@code true} if all blocks have unique positions and none match the city's position, {@code false} otherwise
     */
    public boolean checkCityBlocks(List<CityBlock> blocks) {
        List<Position> seen = new ArrayList<>();
        for (CityBlock block : blocks) {
            Position pos = block.getPosition();
            if (seen.contains(pos)) {
                return false;
            }
            if (pos.equals(this.position)) {
                return false;
            }
            seen.add(pos);
        }
        return true;
    }

}
