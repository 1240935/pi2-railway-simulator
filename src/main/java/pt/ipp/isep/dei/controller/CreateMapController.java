package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.dto.MapDTO;
import pt.ipp.isep.dei.dto.MapMapper;
import pt.ipp.isep.dei.repository.Repositories;

public class CreateMapController {

    public CreateMapController() {
    }

    public MapDTO createMapDTO(int size, String name, int scale){
        return new MapDTO(size,name,scale);
    }

    public void createMap(MapDTO mapDTO) {
        MapMapper mapMapper = new MapMapper();
        Map map = mapMapper.toMap(mapDTO);
        Repositories.getInstance().getMapRepository().addMap(map);
    }
}
