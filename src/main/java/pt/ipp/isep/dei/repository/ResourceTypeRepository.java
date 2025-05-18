package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.ResourceType;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Repository class providing access to all available {@link ResourceType} values.
 */
public class ResourceTypeRepository {

    /**
     * Returns a new list containing all available resource types.
     * The list is a copy of the enum values and can be safely modified without affecting the enum.
     *
     * @return an {@link ArrayList} of all available {@link ResourceType} values
     */
    public ArrayList<ResourceType> getAvailableResourceTypes() {
        return new ArrayList<>(Arrays.asList(ResourceType.values()));
    }
}
