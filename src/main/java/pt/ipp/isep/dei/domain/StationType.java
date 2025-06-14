package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Enumeration representing the types of railway stations in the system.
 * Each type has an associated construction cost and influence radius.
 */
public enum StationType implements Serializable {
    /**
     * A depot station with lower cost and smaller influence radius.
     */
    DEPOT(50, 3),

    /**
     * A standard station with medium cost and radius.
     */
    STATION(100, 4),

    /**
     * A terminal station with higher cost and larger radius.
     */
    TERMINAL(200, 5);

    private final int cost;
    private final int radius;

    /**
     * Constructs a StationType with a specified cost and radius.
     *
     * @param cost   the construction cost of this station type
     * @param radius the radius of influence of this station type
     */
    StationType(int cost, int radius) {
        this.cost = cost;
        this.radius = radius;
    }

    /**
     * Returns the radius of influence of the station type.
     *
     * @return the radius value
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Returns the construction cost of the station type.
     *
     * @return the cost value
     */
    public int getCost() {
        return cost;
    }
}
