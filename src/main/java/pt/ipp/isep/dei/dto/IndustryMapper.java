package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.IndustryRepository;

/**
 * Mapper class responsible for converting an {@link IndustryDTO}
 * into an {@link Industry} domain object.
 */
public class IndustryMapper {

    /**
     * Converts an {@link IndustryDTO} to an {@link Industry} domain object.
     * <p>
     * This method delegates the creation of the {@code Industry} to the {@link IndustryRepository}.
     * </p>
     *
     * @param industryDTO the {@code IndustryDTO} to convert
     * @return the created {@code Industry} domain object
     */
    public Industry toIndustry(IndustryDTO industryDTO){
        return IndustryRepository.createIndustry(
                industryDTO.getIndustryType(),
                industryDTO.getxCoordinate(),
                industryDTO.getyCoordinate(),
                industryDTO.getSize()
        );
    }
}
