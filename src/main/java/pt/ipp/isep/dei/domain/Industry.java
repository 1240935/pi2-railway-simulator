package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents an industry placed on a map at a specific position.
 * <p>
 * Each {@code Industry} has a defined {@link IndustryType} and a {@link Position} indicating
 * where it is located on the map grid.
 * </p>
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

    /**
     * Checks if the given position is valid within the boundaries of the map.
     *
     * @param position the position to check
     * @param size the size (width/height) of the square map grid
     * @return {@code true} if the position is within the map bounds; {@code false} otherwise
     */
    public boolean checkPosition(Position position, int size) {
        return position.getxCoordinate() >= 0 && position.getxCoordinate() < size &&
                position.getyCoordinate() >= 0 && position.getyCoordinate() < size;
    }

    /**
     * Creates a new {@link Position} object with the specified x and y coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return a new {@code Position} instance
     */
    public static Position createPosition(int x, int y){
        return Position.createPosition(x, y);
    }

    /**
     * Checks if the given industry type is valid (not {@code null}).
     *
     * @param industryType the industry type to check
     * @return {@code true} if the industry type is not null; {@code false} otherwise
     */
    public boolean checkIndustryType(IndustryType industryType) {
        return industryType != null;
    }
}
