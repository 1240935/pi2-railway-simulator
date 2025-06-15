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

    /**
     * Creates an Industry with the specified type and position, validating parameters.
     *
     * @param industryType the type of the industry
     * @param x            X coordinate of the industry's position
     * @param y            Y coordinate of the industry's position
     * @param size         Size parameter used for position validation
     * @return the created Industry if parameters are valid; null otherwise
     */
    public static Industry createIndustry(IndustryType industryType, int x, int y, int size) {
        Position position = Industry.createPosition(x, y);
        Industry industry = new Industry(industryType, position);
        if (industry.getPosition().checkPosition(position, size) && industry.checkIndustryType(industryType)) {
            System.out.println("Industry created successfully!");
            return industry;
        } else {
            System.out.println("Invalid industry parameters.");
            return null;
        }
    }
}
