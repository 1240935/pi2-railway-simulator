package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Enum representing different modes of cargo availability.
 * <p>
 * The possible modes are:
 * <ul>
 *   <li>{@link #FULL} - Cargo is fully booked or occupied.</li>
 *   <li>{@link #HALF} - Cargo is half booked or partially occupied.</li>
 *   <li>{@link #AVAILABLE} - Cargo is available and not booked.</li>
 * </ul>
 * </p>
 * This enum implements {@link Serializable} to allow its instances
 * to be serialized if needed.
 */
public enum CargoMode implements Serializable {
    FULL, HALF, AVAILABLE
}
