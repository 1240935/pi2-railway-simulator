package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
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

    public List<String> getMapNames() {
        List<String> names = new ArrayList<>();
        for (Map map : repository.getMaps()) {
            names.add(map.getName());
        }
        return names;
    }

    public boolean saveMapByIndex(int index, String filePath) {
        List<Map> maps = repository.getMaps();
        if (index < 0 || index >= maps.size()) {
            return false;
        }
        Map selectedMap = maps.get(index);
        return repository.saveMapToSerializedFile(selectedMap, filePath);
    }
}
