package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Enumeration representing the types of railway lines.
 * Each type has an associated construction cost and electrification status.
 */
public enum RailwayType implements Serializable {
    /**
     * A single non-electrified railway line.
     */
    SINGLE(6, false),

    /**
     * A double non-electrified railway line.
     */
    DOUBLE(9, false),

    /**
     * A single electrified railway line.
     */
    ELECSINGLE(11, true),

    /**
     * A double electrified railway line.
     */
    ELECDOUBLE(14, true);

    private final int cost;
    private final boolean electrification;

    /**
     * Constructs a RailwayType with a specified cost and electrification status.
     *
     * @param cost            the construction cost of this railway type
     * @param electrification true if the railway line is electrified, false otherwise
     */
    RailwayType(int cost, boolean electrification) {
        this.cost = cost;
        this.electrification = electrification;
    }

    /**
     * Returns the construction cost of the railway type.
     *
     * @return the cost value
     */
    public int getCost() {
        return cost;
    }

    /**
     * Returns whether the railway type is electrified.
     *
     * @return true if electrified, false otherwise
     */
    public boolean isElectrification() {
        return electrification;
    }
}
