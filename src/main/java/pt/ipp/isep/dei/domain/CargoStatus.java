package pt.ipp.isep.dei.domain;

import java.util.List;

/**
 * Stores cargo being supplied and demanded around a station.
 */
public class CargoStatus {
    private final List<Cargo> supplied;
    private final List<Cargo> demanded;

    public CargoStatus(List<Cargo> supplied, List<Cargo> demanded) {
        this.supplied = supplied;
        this.demanded = demanded;
    }

    public List<Cargo> getSupplied() {
        return supplied;
    }

    public List<Cargo> getDemanded() {
        return demanded;
    }

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
