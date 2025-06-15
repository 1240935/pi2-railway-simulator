package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.CargoMode;

import java.util.Arrays;
import java.util.List;

public class CargoModeRepository {

    /**
     * Returns the list of all available cargo modes.
     * @return list with all the values of the CargoMode enum.
     */
    public List<CargoMode> getCargoModes() {
        return Arrays.asList(CargoMode.values());
    }
}
