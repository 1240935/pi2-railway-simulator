package pt.ipp.isep.dei.domain;

/**
 * Enumeration representing the different types of locomotives available.
 */
public enum LocomotiveType {
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
