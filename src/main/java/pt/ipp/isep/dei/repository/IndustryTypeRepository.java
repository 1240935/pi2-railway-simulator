package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.IndustryType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Repository class that provides access to all predefined industry types.
 */
public class IndustryTypeRepository {

    /**
     * Retrieves all available industry types.
     *
     * @return an {@link ArrayList} containing all {@link IndustryType} enum constants
     */
    public ArrayList<IndustryType> getIndustryTypes() {
        return new ArrayList<>(Arrays.asList(IndustryType.values()));
    }
}
