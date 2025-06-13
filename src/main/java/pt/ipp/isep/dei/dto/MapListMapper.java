package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.*;
import java.util.ArrayList;
import java.util.List;


public class MapListMapper {

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
