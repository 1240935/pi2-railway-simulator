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

    public List<String> getMapNames() {
        return repository.getMaps().stream().map(Map::getName).toList();
    }

    public boolean saveMapByIndex(int index, String filePath) {
        List<Map> maps = repository.getMaps();
        if (index < 0 || index >= maps.size()) {
            return false;
        }
        Map map = maps.get(index);
        repository.saveMapToCSV(map, filePath);
        return true;
    }

    public int getMapCount() {
        return repository.getMaps().size();
    }
}
