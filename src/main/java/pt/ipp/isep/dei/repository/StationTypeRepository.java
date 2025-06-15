package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.StationType;

import java.util.Arrays;
import java.util.List;

/**
 * Repository responsible for providing the available station types.
 */
public class StationTypeRepository {

    /**
     * Gets the list of all defined station types.
     *
     * @return Immutable list containing all values of the StationType enum.
     */
    public List<StationType> getStationTypes() {
        return Arrays.asList(StationType.values());
    }
}
