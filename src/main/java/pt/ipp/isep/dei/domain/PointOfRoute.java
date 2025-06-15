package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Representa um ponto de uma rota, contendo uma estação,
 * uma lista de cargas, o modo de carga e o tempo estimado
 * até à próxima chegada.
 */
public class PointOfRoute implements Serializable {

    private Station station;
    private List<Cargo> cargoList;
    private CargoMode cargoMode;
    private double estimatedTimeToNextArrival;

    /**
     * Construtor principal da classe.
     *
     * @param station   estação associada ao ponto
     * @param cargoList lista de cargas no ponto
     * @param cargoMode modo de transporte da carga
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
     * Verifica se a estação é válida (nome não nulo e apenas letras/espaços).
     *
     * @param station estação a validar
     * @return true se válida, false caso contrário
     */
    public boolean checkStation(Station station) {
        return station != null && station.getName() != null && station.getName().matches("^[a-zA-Z\\s]+$");
    }

    /**
     * Verifica se a lista de cargas é válida (tipo definido e quantidade ≥ 0).
     *
     * @param cargoList lista de cargas a validar
     * @return true se válida, false caso contrário
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
     * Verifica se o modo de carga é válido (não nulo).
     *
     * @param mode modo de carga
     * @return true se válido, false caso contrário
     */
    public boolean checkCargoMode(CargoMode mode) {
        return mode != null;
    }

    /**
     * Define o tempo estimado até à próxima chegada (em horas).
     *
     * @param timeHours tempo em horas
     */
    public void setEstimatedTimeToNextArrival(double timeHours) {
        this.estimatedTimeToNextArrival = timeHours;
    }

    /**
     * Representação textual do ponto de rota.
     *
     * @return string com nome da estação, modo de carga e tipos de carga
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
