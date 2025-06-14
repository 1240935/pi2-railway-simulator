package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Enumeration representing the different types of locomotives available.
 */
public enum LocomotiveType implements Serializable {
    /** Steam-powered locomotive */
    STEAM(),

    /** Diesel-powered locomotive */
    DIESEL(),

    /** Electric-powered locomotive */
    ELECTRIC();

    /**
     * Default constructor for the locomotive types.
     */
    LocomotiveType() {
    }
}
