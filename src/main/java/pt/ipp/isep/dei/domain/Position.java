package pt.ipp.isep.dei.domain;

public class Position {
    private int xCoordinate;
    private int yCoordinate;

    public Position(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Calculates the Euclidean distance between this position and another position.
     * @param other The other position
     * @return The Euclidean distance as a double
     */
    public double euclideanDistance(Position other) {
        int dx = this.xCoordinate - other.xCoordinate;
        int dy = this.yCoordinate - other.yCoordinate;
        return Math.sqrt(dx * dx + dy * dy);
    }
}