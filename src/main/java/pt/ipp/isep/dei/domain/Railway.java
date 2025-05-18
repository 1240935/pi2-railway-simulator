package pt.ipp.isep.dei.domain;

/**
 * Represents a railway connection between two stations, with a specific railway type.
 */
public class Railway {

    /**
     * The starting station of the railway.
     */
    private final Station start;

    /**
     * The ending station of the railway.
     */
    private final Station end;

    /**
     * The type of the railway (e.g., electrified, non-electrified).
     */
    private final RailwayType type;

    /**
     * Constructs a Railway object connecting two stations with a specified railway type.
     *
     * @param start the station where the railway starts
     * @param end the station where the railway ends
     * @param type the type of railway
     */
    Railway(Station start , Station end, RailwayType type){
        this.start = start;
        this.end = end;
        this.type = type;
    }

    /**
     * Returns the starting station of the railway.
     *
     * @return the start station
     */
    public Station getStart() {
        return start;
    }

    /**
     * Returns the ending station of the railway.
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
}
