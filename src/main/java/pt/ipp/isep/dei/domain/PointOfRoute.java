package pt.ipp.isep.dei.domain;

import java.util.List;

public class PointOfRoute {

    private Station station;
    private List<Cargo> cargoList;
    private CargoMode cargoMode;
    private double estimatedTimeToNextArrival;


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

    public boolean checkStation(Station station) {
        return station != null && station.getName() != null && station.getName().matches("^[a-zA-Z\\s]+$");
    }

    public boolean checkCargo(List<Cargo> cargoList) {
        for (Cargo cargo : cargoList) {
            if (cargo.getType() == null || cargo.getQuantity() < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCargoMode(CargoMode mode) {
        return mode != null;
    }

    public void setEstimatedTimeToNextArrival(double timeHours) {
        this.estimatedTimeToNextArrival = timeHours;
    }


    @Override
    public String toString() {
        return "PointOfRoute{" +
                "station=" + station +
                ", cargoList=" + cargoList +
                ", cargoMode=" + cargoMode +
                '}';
    }
}