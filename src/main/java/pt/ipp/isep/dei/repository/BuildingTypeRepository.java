package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.BuildingType;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Repository class to manage BuildingType values.
 */
public class BuildingTypeRepository {

    /**
     * Returns a list of all available BuildingType enum values.
     *
     * @return an ArrayList containing all BuildingType values
     */
    public ArrayList<BuildingType> getAvailableBuildingTypes() {
        return new ArrayList<>(Arrays.asList(BuildingType.values()));
    }
}
