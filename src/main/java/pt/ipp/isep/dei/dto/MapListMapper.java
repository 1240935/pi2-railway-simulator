package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link Map} domain objects
 * into a list of {@link MapDTO} data transfer objects.
 */
public class MapListMapper {

    /**
     * Converts a list of {@link Map} objects to a list of {@link MapDTO} objects.
     *
     * @param maps the list of {@code Map} domain objects to convert
     * @return a list of {@code MapDTO} objects representing the input maps
     */
    public List<MapDTO> toDTOList(List<Map> maps) {
        List<MapDTO> result = new ArrayList<>();
        for (Map map : maps) {
            MapDTO dto = new MapDTO();
            dto.setName(map.getName());
            dto.setSize(map.getSize());
            dto.setScale(map.getScale());
            result.add(dto);
        }
        return result;
    }
}
