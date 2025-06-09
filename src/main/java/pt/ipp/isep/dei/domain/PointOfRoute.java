package pt.ipp.isep.dei.domain;

import java.util.List;

public class PointOfRoute {
    private Station station;
    private List<Cargo> cargoList;
    private CargoMode cargoMode;

    public PointOfRoute(Station station, List<Cargo> cargoList, CargoMode cargoMode) {
        this.station = station;
        this.cargoList = cargoList;
        this.cargoMode = cargoMode;
    }

    public Station getStation() {
        return station;
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }

    public CargoMode getCargoMode() {
        return cargoMode;
    }
}
