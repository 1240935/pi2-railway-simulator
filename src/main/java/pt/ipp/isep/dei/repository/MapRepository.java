package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Map;
import java.util.ArrayList;
import java.io.*;

/**
 * Repository class responsible for storing and managing {@link Map} objects.
 * Provides methods to addScenario maps and retrieve the list of stored maps.
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
    /**
     * Saves a map object to a serialized file (.ser)
     *
     * @param map      the map to serialize
     * @param filePath the destination file path (e.g., "maps/portugal.ser")
     */
    public boolean saveMapToSerializedFile(Map map, String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(map);
            return true;
        } catch (IOException e) {
            System.err.println("Error serializing map: " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads a serialized map from a file.
     *
     * @param filePath the path to the serialized file
     * @return the deserialized Map object, or null if loading fails
     */
    public Map loadMapFromSerializedFile(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Map map = (Map) in.readObject();
            return map;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading map: " + e.getMessage());
            return null;
        }
    }
}
