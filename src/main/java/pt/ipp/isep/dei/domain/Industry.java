package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents an industry placed on a map at a specific position.
 * <p>
 * Each industry has a defined {@link IndustryType} and a {@link Position} indicating
 * where it is located on the map grid.
 */
public class Industry implements Serializable {


    /** The type of the industry (e.g., Steel Mill, Oil Well). */
    private IndustryType industryType;

    /** The position of the industry on the map. */
    private Position position;

    /**
     * Constructs a new {@code Industry} with the given type and position.
     *
     * @param industryType the type of the industry
     * @param position the position on the map where the industry is placed
     */
    public Industry(IndustryType industryType, Position position) {
        this.industryType = industryType;
        this.position = position;
    }

    /**
     * Returns the type of this industry.
     *
     * @return the industry type
     */
    public IndustryType getType() {
        return industryType;
    }

    /**
     * Sets the type of this industry.
     *
     * @param industryType the new industry type to set
     */
    public void setType(IndustryType industryType) {
        this.industryType = industryType;
    }

    /**
     * Returns the position of this industry on the map.
     *
     * @return the industry's position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of this industry on the map.
     *
     * @param position the new position to assign to the industry
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    public static Position createPosition(int x, int y){
        return Position.createPosition(x, y);
    }

    public boolean checkIndustryType(IndustryType industryType) {
        return industryType != null;
    }
}
