package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.CargoMode;

import java.util.Arrays;
import java.util.List;

public class CargoModeRepository {

    /**
     * Retorna a lista de todos os modos de carga disponíveis.
     * @return lista com todos os valores do enum CargoMode.
     */
    public List<CargoMode> getCargoModes() {
        return Arrays.asList(CargoMode.values());
    }
}
