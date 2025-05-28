package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.MapRepository;

public class MapMapper {
    public Map toMap(MapDTO dto) {
        return MapRepository.createMap(dto.getSize(), dto.getName(), dto.getScale());
    }
}
