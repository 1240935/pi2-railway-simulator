package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single block within a city on the map.
 * <p>
 * Each {@code CityBlock} has a {@link Position} which defines its location on the grid.
 */
public class CityBlock implements Serializable {


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

    public CityBlock(){
    }

    /**
     * Returns the position of this city block.
     *
     * @return the position of the block
     */
    public Position getPosition() {
        return position;
    }

    public List<Position> createPositions(int[] x, int[] y){

        List<Position> positions = new ArrayList<>();


        for (int i = 0; i < x.length; i++) {

            Position position = new Position(x[i], y[i]);


            positions.add(position);
        }

        return positions;
    }
}
