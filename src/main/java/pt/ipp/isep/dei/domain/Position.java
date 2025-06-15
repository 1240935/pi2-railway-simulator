package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents a position in a 2D coordinate system with x and y coordinates.
 */
public class Position implements Serializable {

    private int xCoordinate;
    private int yCoordinate;

    /**
     * Constructs a Position with specified x and y coordinates.
     *
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     */
    public Position(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Checks if the given position is valid within the bounds of a square grid of given size.
     *
     * @param position the position to validate
     * @param size     the size of the grid (width and height)
     * @return {@code true} if the position is within bounds, {@code false} otherwise
     */
    public static boolean isValid(Position position, int size) {
        int x = position.getxCoordinate();
        int y = position.getyCoordinate();
        return x >= 0 && y >= 0 && x < size && y < size;
    }

    /**
     * Checks if the given position is valid within the bounds of the map.
     * Prints a message if the position is invalid.
     *
     * @param position the position to check
     * @param size     the size of the map
     * @return {@code true} if the position is valid, {@code false} otherwise
     */
    public boolean checkPosition(Position position, int size){
        int x = position.getxCoordinate();
        int y = position.getyCoordinate();
        if (x >= size || y >= size || x < 0 || y < 0) {
            System.out.println("Invalid position: coordinates exceed map bounds.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns the x coordinate of this position.
     *
     * @return the x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Returns the y coordinate of this position.
     *
     * @return the y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Sets the x coordinate of this position.
     *
     * @param xCoordinate the new x coordinate
     */
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Sets the y coordinate of this position.
     *
     * @param yCoordinate the new y coordinate
     */
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Calculates the Euclidean distance between this position and another position.
     *
     * @param other the other position to which the distance is calculated
     * @return the Euclidean distance as a double
     */
    public double euclideanDistance(Position other) {
        int dx = this.xCoordinate - other.xCoordinate;
        int dy = this.yCoordinate - other.yCoordinate;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Factory method to create a new Position instance.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return a new Position object with given coordinates
     */
    public static Position createPosition(int x, int y){
        return new Position(x, y);
    }
}
