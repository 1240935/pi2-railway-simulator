package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.repository.CityRepository;

/**
 * Mapper class responsible for converting a {@link CityDTO}
 * into a {@link City} domain object.
 */
public class CityMapper {

    /**
     * Converts a {@link CityDTO} to a {@link City} domain object.
     *
     * This method delegates the creation of the {@code City} to the {@link CityRepository}.
     *
     * @param dto the {@code CityDTO} to convert
     * @return the created {@code City} domain object
     */
    public City toCity(CityDTO dto) {
        return CityRepository.createCity(
                dto.getxCoordinate(),
                dto.getyCoordinate(),
                dto.getName(),
                dto.getCityBlocks(),
                dto.getMapSize()
        );
    }
}
