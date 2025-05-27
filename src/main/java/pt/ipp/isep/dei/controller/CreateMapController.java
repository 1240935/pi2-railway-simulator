package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

public class CreateMapController {

    public CreateMapController() {
    }

    public void createMap(int size, String name, int scale) {
        Map map = MapRepository.createMap(size, name, scale);
        Repositories.getInstance().getMapRepository().addMap(map);
    }
}
