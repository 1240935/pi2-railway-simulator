package pt.ipp.isep.dei.domain;

/**
 * Represents a position in a 2D coordinate system with x and y coordinates.
 */
public class Position {
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

    public boolean checkPosition(Position position, int size){
        int x = position.getxCoordinate();
        int y = position.getyCoordinate();
        if (x >= size || y >= size || x < 0 || y < 0) {
            System.out.println("Invalid position: coordinates exceed map bounds.");
            return false;
        }
        else{
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
     * @param other the other position to which distance is calculated
     * @return the Euclidean distance as a double
     */
    public double euclideanDistance(Position other) {
        int dx = this.xCoordinate - other.xCoordinate;
        int dy = this.yCoordinate - other.yCoordinate;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static Position createPosition(int x, int y){
        return new Position(x, y);
    }
}
