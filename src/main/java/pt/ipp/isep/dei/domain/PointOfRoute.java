package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a point in a route, containing a station,
 * a list of cargos, the cargo transport mode,
 * and the estimated time until the next arrival.
 */
public class PointOfRoute implements Serializable {

    private Station station;
    private List<Cargo> cargoList;
    private CargoMode cargoMode;
    private double estimatedTimeToNextArrival;

    /**
     * Main constructor of the class.
     *
     * @param station   the station associated with the point
     * @param cargoList list of cargos at the point
     * @param cargoMode cargo transport mode
     */
    public PointOfRoute(Station station, List<Cargo> cargoList, CargoMode cargoMode) {
        this.station = station;
        this.cargoList = cargoList;
        this.cargoMode = cargoMode;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }

    public void setCargoList(List<Cargo> cargoList) {
        this.cargoList = cargoList;
    }

    public CargoMode getCargoMode() {
        return cargoMode;
    }

    public void setCargoMode(CargoMode cargoMode) {
        this.cargoMode = cargoMode;
    }

    /**
     * Checks if the station is valid (non-null name with only letters and spaces).
     *
     * @param station the station to validate
     * @return true if valid, false otherwise
     */
    public boolean checkStation(Station station) {
        return station != null && station.getName() != null && station.getName().matches("^[a-zA-Z\\s]+$");
    }

    /**
     * Checks if the cargo list is valid (type defined and quantity ≥ 0).
     *
     * @param cargoList the list of cargos to validate
     * @return true if valid, false otherwise
     */
    public boolean checkCargo(List<Cargo> cargoList) {
        for (Cargo cargo : cargoList) {
            if (cargo.getType() == null || cargo.getQuantity() < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the cargo mode is valid (non-null).
     *
     * @param mode the cargo mode to validate
     * @return true if valid, false otherwise
     */
    public boolean checkCargoMode(CargoMode mode) {
        return mode != null;
    }

    /**
     * Sets the estimated time until the next arrival (in hours).
     *
     * @param timeHours time in hours
     */
    public void setEstimatedTimeToNextArrival(double timeHours) {
        this.estimatedTimeToNextArrival = timeHours;
    }

    /**
     * Textual representation of the route point.
     *
     * @return string with station name, cargo mode, and cargo types
     */
    @Override
    public String toString() {
        String stationName = (station != null) ? station.getName() : "N/A";
        String modeName = (cargoMode != null) ? cargoMode.name() : "N/A";

        StringBuilder cargosBuilder = new StringBuilder();
        if (cargoList != null && !cargoList.isEmpty()) {
            for (int i = 0; i < cargoList.size(); i++) {
                cargosBuilder.append(cargoList.get(i).getType().name());
                if (i < cargoList.size() - 1) {
                    cargosBuilder.append(", ");
                }
            }
        } else {
            cargosBuilder.append("No cargo");
        }

        return "Station: " + stationName + " | Mode: " + modeName + " | Cargo: [" + cargosBuilder + "]";
    }
}
