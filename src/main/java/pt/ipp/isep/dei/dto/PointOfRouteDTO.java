package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Cargo;
import pt.ipp.isep.dei.domain.CargoMode;
import pt.ipp.isep.dei.domain.Station;

import java.util.List;

/**
 * Data Transfer Object representing a point in a route.
 * It contains the station, list of cargo, and the cargo mode associated with this point.
 */
public class PointOfRouteDTO {

    /**
     * The station associated with this point of the route.
     */
    private Station station;

    /**
     * The list of cargo at this point of the route.
     */
    private List<Cargo> cargo;

    /**
     * The mode of cargo transportation at this point.
     */
    private CargoMode cargoMode;

    /**
     * Default constructor.
     */
    public PointOfRouteDTO(){}

    /**
     * Constructs a PointOfRouteDTO with given station, cargo list, and cargo mode.
     *
     * @param station the station of the route point
     * @param cargo the list of cargo at this point
     * @param cargoMode the mode of cargo transportation
     */
    public PointOfRouteDTO(Station station, List<Cargo> cargo, CargoMode cargoMode){
        this.station = station;
        this.cargo = cargo;
        this.cargoMode = cargoMode;
    }

    /**
     * Returns the cargo mode at this point of the route.
     *
     * @return the cargo mode
     */
    public CargoMode getCargoMode() {
        return cargoMode;
    }

    /**
     * Returns the list of cargo at this point of the route.
     *
     * @return the cargo list
     */
    public List<Cargo> getCargo() {
        return cargo;
    }

    /**
     * Returns the station at this point of the route.
     *
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the cargo list at this point of the route.
     *
     * @param cargo the cargo list to set
     */
    public void setCargo(List<Cargo> cargo) {
        this.cargo = cargo;
    }

    /**
     * Sets the cargo mode at this point of the route.
     *
     * @param cargoMode the cargo mode to set
     */
    public void setCargoMode(CargoMode cargoMode) {
        this.cargoMode = cargoMode;
    }

    /**
     * Sets the station at this point of the route.
     *
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }
}
