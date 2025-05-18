package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.IndustryChanger;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Repository class to provide access to all predefined IndustryChanger types.
 */
public class IndustryChangerRepository {

    /**
     * Returns all available IndustryChanger enum constants.
     *
     * @return an {@link ArrayList} containing all {@link IndustryChanger} values
     */
    public ArrayList<IndustryChanger> getAvailableIndustryChanger() {
        return new ArrayList<>(Arrays.asList(IndustryChanger.values()));
    }
}
