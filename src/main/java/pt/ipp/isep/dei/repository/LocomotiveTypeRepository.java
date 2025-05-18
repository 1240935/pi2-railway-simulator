package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.LocomotiveType;

import java.util.Arrays;
import java.util.List;

/**
 * Repository class providing access to the available locomotive types.
 * It returns all possible {@link LocomotiveType} values defined in the system.
 */
public class LocomotiveTypeRepository {

    /**
     * Returns a list of all available locomotive types.
     * This list is backed by the enum values and is immutable.
     *
     * @return a {@link List} of all {@link LocomotiveType} constants
     */
    public List<LocomotiveType> getLocomotiveTypes() {
        return Arrays.asList(LocomotiveType.values());
    }
}
