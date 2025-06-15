package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.MapRepository;

/**
 * Mapper class responsible for converting a {@link MapDTO}
 * into a {@link Map} domain object.
 */
public class MapMapper {

    /**
     * Converts a {@link MapDTO} to a {@link Map} domain object.
     *
     * This method delegates the creation of the {@code Map} to the {@link MapRepository}.
     *
     * @param dto the {@code MapDTO} to convert
     * @return the created {@code Map} domain object
     */
    public Map toMap(MapDTO dto) {
        return MapRepository.createMap(dto.getSize(), dto.getName(), dto.getScale());
    }
}
