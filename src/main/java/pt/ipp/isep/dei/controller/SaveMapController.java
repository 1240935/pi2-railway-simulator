package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.List;

public class SaveMapController {

    private final MapRepository repository;

    public SaveMapController() {
        this.repository = Repositories.getInstance().getMapRepository();
    }

    public boolean saveMapToFile(Map map, String filePath) {
        return Repositories.getInstance().getMapRepository().saveMapToSerializedFile(map, filePath);
    }
    public List<Map> getMaps() {
        return repository.getMaps();
    }


    public int getMapCount() {
        return repository.getMaps().size();
    }
}
