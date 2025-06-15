package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.MapRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsible for saving map data to serialized files and retrieving map-related information.
 */
public class SaveMapController {

    /**
     * Repository instance to access and manage maps.
     */
    private final MapRepository repository;

    /**
     * Constructs a SaveMapController and initializes the map repository.
     */
    public SaveMapController() {
        this.repository = Repositories.getInstance().getMapRepository();
    }

    /**
     * Saves a specific map to the specified file path.
     *
     * @param map      The map to be saved.
     * @param filePath The path where the map should be serialized and stored.
     * @return true if the map was saved successfully, false otherwise.
     */
    public boolean saveMapToFile(Map map, String filePath) {
        return Repositories.getInstance().getMapRepository().saveMapToSerializedFile(map, filePath);
    }

    /**
     * Retrieves all maps currently stored in the repository.
     *
     * @return A list of all available maps.
     */
    public List<Map> getMaps() {
        return repository.getMaps();
    }

    /**
     * Returns the number of maps currently available in the repository.
     *
     * @return The count of stored maps.
     */
    public int getMapCount() {
        return repository.getMaps().size();
    }

    /**
     * Retrieves the names of all maps available in the repository.
     *
     * @return A list of map names.
     */
    public List<String> getMapNames() {
        List<String> names = new ArrayList<>();
        for (Map map : repository.getMaps()) {
            names.add(map.getName());
        }
        return names;
    }

    /**
     * Saves a map selected by index to the specified file path.
     *
     * @param index    The index of the map in the repository's list.
     * @param filePath The path where the map should be saved.
     * @return true if the map was saved successfully, false otherwise.
     */
    public boolean saveMapByIndex(int index, String filePath) {
        List<Map> maps = repository.getMaps();
        if (index < 0 || index >= maps.size()) {
            return false;
        }
        Map selectedMap = maps.get(index);
        return repository.saveMapToSerializedFile(selectedMap, filePath);
    }
}
