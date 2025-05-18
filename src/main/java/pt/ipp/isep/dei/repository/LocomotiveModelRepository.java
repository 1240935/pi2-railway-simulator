package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.LocomotiveModel;

import java.util.Arrays;
import java.util.List;

/**
 * Repository class that provides access to all predefined locomotive models.
 */
public class LocomotiveModelRepository {

    /**
     * Returns an immutable list of all available locomotive models.
     * This list is derived from the enum constants defined in {@link LocomotiveModel}.
     *
     * @return a {@link List} of all {@link LocomotiveModel} constants
     */
    public List<LocomotiveModel> getLocomotiveModels() {
        return Arrays.asList(LocomotiveModel.values());
    }
}
