package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents the center position of a station.
 */
public class StationCenter implements Serializable {
    private Position position;

    /**
     * Creates a StationCenter with the given position.
     *
     * @param position the center position of the station, must not be null
     * @throws IllegalArgumentException if position is null
     */
    public StationCenter(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = position;
    }

    /**
     * Returns the position of the station center.
     *
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the station center.
     *
     * @param position the new position, must not be null
     * @throws IllegalArgumentException if position is null
     */
    public void setPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = position;
    }
}
