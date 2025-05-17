package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.LocomotiveModel;

import java.util.Arrays;
import java.util.List;

public class LocomotiveModelRepository {

    /**
     * Returns all predefined locomotive models.
     *
     * @return list of locomotive models
     */
    public List<LocomotiveModel> getLocomotiveModels() {
        return Arrays.asList(LocomotiveModel.values());
    }
}
