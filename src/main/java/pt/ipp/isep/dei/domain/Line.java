package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents a railway line between two stations.
 * Contains information about the start and end stations,
 * whether the line is electrified, and the distance between stations.
 */
public class Line implements Serializable {
    private Station start;
    private Station end;
    private boolean isElectrified;
    private int distance;  // Distance of the line segment

    /**
     * Constructs a Line object.
     *
     * @param start The starting station of the line.
     * @param end The ending station of the line.
     * @param isElectrified Indicates if the line is electrified.
     * @param distance The distance between the start and end stations.
     */
    public Line(Station start, Station end, boolean isElectrified, int distance) {
        this.start = start;
        this.end = end;
        this.isElectrified = isElectrified;
        this.distance = distance;
    }

    /**
     * Returns the starting station.
     * @return the start station
     */
    public Station getStart() { return start; }

    /**
     * Returns the ending station.
     * @return the end station
     */
    public Station getEnd() { return end; }

    /**
     * Checks if the line is electrified.
     * @return true if electrified, false otherwise
     */
    public boolean isElectrified() { return isElectrified; }

    /**
     * Returns the distance between the stations.
     * @return the distance as a double
     */
    public int getDistance() { return distance; }
}
