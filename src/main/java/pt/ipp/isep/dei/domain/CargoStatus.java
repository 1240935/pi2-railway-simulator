package pt.ipp.isep.dei.domain;

import java.util.List;

/**
 * Represents the cargo status around a station, including cargoes supplied and demanded.
 * <p>
 * This class stores lists of cargo objects representing what is supplied and what is demanded
 * at or near a station.
 */
public class CargoStatus {

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

    /**
     * Returns a string representation of the cargo status,
     * listing supplied and demanded cargo types and quantities.
     *
     * @return formatted string with supplied and demanded cargo details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Supplied Cargoes:\n");
        for (Cargo c : this.supplied) {
            sb.append(" - ").append(c.getType().name()).append(": ").append(c.getQuantity()).append("\n");
        }
        sb.append("Demanded Cargoes:\n");
        for (Cargo c : this.demanded) {
            sb.append(" - ").append(c.getType().name()).append(": ").append(c.getQuantity()).append("\n");
        }
        return sb.toString();
    }
}
