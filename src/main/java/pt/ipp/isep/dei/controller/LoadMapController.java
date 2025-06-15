package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for loading a serialized Map object from a file.
 * This controller handles validation, duplication checks, and registration of the loaded map.
 */
public class LoadMapController {

    /**
     * Loads a serialized map from the specified file path.
     * Ensures the map is not null and does not duplicate an existing map.
     *
     * @param filePath the file path to load the serialized map from
     * @return true if the map was successfully loaded and registered, false otherwise
     */
    public boolean loadSerializedMap(String filePath) {
        Map map = Repositories.getInstance().getMapRepository().loadMapFromSerializedFile(filePath);
        if (map == null) {
            System.out.println("Failed to load map from file.");
            return false;
        }

        // Check for duplicate map name
        if (mapExists(map.getName())) {
            System.out.println("A map with the name '" + map.getName() + "' already exists.");
            return false;
        }

        // Add map to the repository
        Repositories.getInstance().getMapRepository().addMap(map);
        System.out.println("Map loaded successfully from file.");
        return true;
    }

    /**
     * Checks whether a map with the specified name already exists in the repository.
     *
     * @param name the name of the map to check
     * @return true if a map with the name exists, false otherwise
     */
    private boolean mapExists(String name) {
        return Repositories.getInstance()
                .getMapRepository()
                .getMaps()
                .stream()
                .anyMatch(m -> m.getName().equalsIgnoreCase(name));
    }
}
