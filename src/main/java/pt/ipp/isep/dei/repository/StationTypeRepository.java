package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.StationType;

import java.util.Arrays;
import java.util.List;

public class StationTypeRepository {
     public List<StationType> getStationTypes() {
            return Arrays.asList(StationType.values());
     }
}
