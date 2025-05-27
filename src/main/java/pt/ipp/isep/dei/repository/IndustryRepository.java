package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.IndustryType;
import pt.ipp.isep.dei.domain.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Industry objects.
 */
public class IndustryRepository {
    private final ArrayList<Industry> industries = new ArrayList<>();

    /**
     * Adds a new Industry to the repository.
     *
     * @param industry the Industry object to add
     */
    public void addIndustry(Industry industry) {
        industries.add(industry);
    }

    /**
     * Retrieves all Industry objects stored in the repository.
     *
     * @return a list of Industry objects
     */
    public List<Industry> getIndustries() {
        return industries;
    }

    public static Industry createIndustry(IndustryType industryType, Position position, int size) {
        if (!position.checkPosition(position, size)) {
            System.out.println("Invalid position!");
            System.exit(1);
        }
        Industry industry = new Industry(industryType, position);
        return industry;
    }
}


