package pt.ipp.isep.dei.repository;


import pt.ipp.isep.dei.domain.CargoMode;

import java.util.Arrays;
import java.util.List;

public class CargoModeRepository {
        public List<CargoMode> getCargoModes() {
            return Arrays.asList(CargoMode.values());
        }
}
