package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Map;
import java.util.ArrayList;

/**
 * Repository class responsible for storing and managing {@link Map} objects.
 * Provides methods to add maps and retrieve the list of stored maps.
 */
public class MapRepository {

    /**
     * Internal list that holds the stored maps.
     */
    private ArrayList<Map> maps = new ArrayList<>();

    /**
     * Returns the list of all maps stored in the repository.
     * Note that this returns a direct reference to the internal list,
     * so external modifications will affect the repository's state.
     *
     * @return the list of {@link Map} objects
     */
    public ArrayList<Map> getMaps() {
        return maps;
    }

    /**
     * Adds a {@link Map} to the repository.
     *
     * @param map the {@link Map} to be added
     */
    public void addMap(Map map) {
        maps.add(map);
    }

    public static Map createMap(int size, String name, int scale) {
        Map map = new Map(size, name, scale);
        if (map.checkName(name) && map.checkSize(size) && map.checkScale(scale)) {
            System.out.println("Map created successfully!");
            return map;
        } else {
            System.out.println("Invalid map parameters.");
            return null;
        }
    }
}
