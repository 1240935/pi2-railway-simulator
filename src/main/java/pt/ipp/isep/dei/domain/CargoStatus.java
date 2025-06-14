package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the cargo status around a station, including cargoes supplied and demanded.
 * <p>
 * This class stores lists of cargo objects representing what is supplied and what is demanded
 * at or near a station.
 */
public class CargoStatus implements Serializable {

    /**
     * List of cargoes supplied by the station or nearby industries/cities.
     */
    private final List<Cargo> supplied;

    /**
     * List of cargoes demanded by the station or nearby industries/cities.
     */
    private final List<Cargo> demanded;

    /**
     * Constructs a CargoStatus object with supplied and demanded cargo lists.
     *
     * @param supplied the list of cargoes supplied
     * @param demanded the list of cargoes demanded
     */
    public CargoStatus(List<Cargo> supplied, List<Cargo> demanded) {
        this.supplied = supplied;
        this.demanded = demanded;
    }

    /**
     * Returns the list of cargoes supplied.
     *
     * @return list of supplied cargo objects
     */
    public List<Cargo> getSupplied() {
        return supplied;
    }

    /**
     * Returns the list of cargoes demanded.
     *
     * @return list of demanded cargo objects
     */
    public List<Cargo> getDemanded() {
        return demanded;
    }

    @Override
    public String toString() {
        boolean noSupplied = supplied == null || supplied.isEmpty();
        boolean noDemanded = demanded == null || demanded.isEmpty();

        if (noSupplied && noDemanded) {
            return "No available cargo";
        }

        StringBuilder sb = new StringBuilder();

        sb.append(" - Supplied Cargoes:\n");
        if (noSupplied) {
            sb.append("  No cargo supplied.\n");
        } else {
            for (Cargo supplied : supplied) {
                sb.append("  ").append(supplied).append("\n");
            }
        }

        sb.append(" - Demanded Cargoes:\n");
        if (noDemanded) {
            sb.append("  No cargo demanded.\n");
        } else {
            for (Cargo demanded : demanded) {
                sb.append("  ").append(demanded).append("\n");
            }
        }

        return sb.toString().trim();
    }

}
