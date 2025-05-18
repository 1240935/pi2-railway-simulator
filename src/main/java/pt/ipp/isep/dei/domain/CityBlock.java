package pt.ipp.isep.dei.domain;

/**
 * Represents a single block within a city on the map.
 * <p>
 * Each {@code CityBlock} has a {@link Position} which defines its location on the grid.
 */
public class CityBlock {

    /** The position of this city block on the map. */
    private Position position;

    /**
     * Constructs a new {@code CityBlock} with the specified position.
     *
     * @param position the position of the city block
     */
    public CityBlock(Position position) {
        this.position = position;
    }

    /**
     * Returns the position of this city block.
     *
     * @return the position of the block
     */
    public Position getPosition() {
        return position;
    }
}
