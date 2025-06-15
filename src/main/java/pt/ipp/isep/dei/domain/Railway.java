package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a railway connection between two stations,
 * with a specific railway type and distance.
 */
public class Railway implements Serializable {

    private final Station start;
    private final Station end;
    private final RailwayType type;
    private final double distance;  // Distance attribute in units (e.g., kilometers)

    /**
     * Constructs a Railway with given start and end stations, railway type, and distance.
     *
     * @param start    the starting station of the railway
     * @param end      the ending station of the railway
     * @param type     the type of railway (e.g., single, double, electric)
     * @param distance the distance between start and end stations
     */
    public Railway(Station start, Station end, RailwayType type, double distance) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.distance = distance;
    }

    /**
     * Returns the starting station.
     *
     * @return the start station
     */
    public Station getStart() {
        return start;
    }

    /**
     * Returns the ending station.
     *
     * @return the end station
     */
    public Station getEnd() {
        return end;
    }

    /**
     * Returns the type of the railway.
     *
     * @return the railway type
     */
    public RailwayType getType() {
        return type;
    }

    /**
     * Returns the distance between the start and end stations.
     *
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Factory method to create a Railway between two stations
     * calculating the Euclidean distance and setting a default type.
     *
     * @param start the starting station
     * @param end   the ending station
     * @return a new Railway instance of type ELECDOUBLE with calculated distance
     */
    public static Railway getRailwayBetween(Station start, Station end) {
        Position position1 = start.getPosition();
        Position position2 = end.getPosition();
        double distance = position1.euclideanDistance(position2);
        return new Railway(start, end, RailwayType.ELECDOUBLE, distance);
    }

    /**
     * Checks if the railway is double track.
     *
     * @return true if the railway type is ELECDOUBLE or DOUBLE, false otherwise
     */
    public boolean isDoubleTrack() {
        return type == RailwayType.ELECDOUBLE || type == RailwayType.DOUBLE;
    }

    /**
     * Two Railways are considered equal if they connect the same stations,
     * regardless of direction (start to end or end to start).
     *
     * @param o the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Railway railway = (Railway) o;
        return (start.equals(railway.start) && end.equals(railway.end)) ||
                (start.equals(railway.end) && end.equals(railway.start));
    }

    /**
     * Hash code based on start and end stations, symmetric in order.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(start, end) + Objects.hash(end, start);
    }
}
