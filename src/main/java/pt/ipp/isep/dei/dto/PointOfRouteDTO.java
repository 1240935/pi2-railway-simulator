package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Cargo;
import pt.ipp.isep.dei.domain.CargoMode;
import pt.ipp.isep.dei.domain.Station;

import java.util.List;

public class PointOfRouteDTO {
    private Station station;
    private List<Cargo> cargo;
    private CargoMode cargoMode;

    public PointOfRouteDTO(){}

    public PointOfRouteDTO(Station station, List<Cargo> cargo, CargoMode cargoMode){
        this.station = station;
        this.cargo = cargo;
        this.cargoMode = cargoMode;
    }

    public CargoMode getCargoMode() {
        return cargoMode;
    }

    public List<Cargo> getCargo() {
        return cargo;
    }

    public Station getStation() {
        return station;
    }

    public void setCargo(List<Cargo> cargo) {
        this.cargo = cargo;
    }

    public void setCargoMode(CargoMode cargoMode) {
        this.cargoMode = cargoMode;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
