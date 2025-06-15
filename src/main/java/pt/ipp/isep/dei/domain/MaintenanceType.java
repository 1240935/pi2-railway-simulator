package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents the types of maintenance available.
 * <p>
 * Possible values are:
 * <ul>
 *   <li>{@link #FULL} — Full maintenance.</li>
 *   <li>{@link #ELECTRIFIED} — Maintenance related to electrified systems.</li>
 * </ul>
 */
public enum MaintenanceType implements Serializable {
    FULL,
    ELECTRIFIED;
}
