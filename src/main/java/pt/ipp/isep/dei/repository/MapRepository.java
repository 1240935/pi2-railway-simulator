package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Map;
import java.util.ArrayList;
import java.io.*;

/**
 * Repository class responsible for storing and managing {@link Map} objects.
 * Provides methods to add maps, retrieve the list of stored maps,
 * and save/load maps via serialization.
 */
public class MapRepository {

    /** Internal list that holds the stored maps. */
    private ArrayList<Map> maps = new ArrayList<>();

    /**
     * Returns the list of all maps stored in the repository.
     * Note: this returns a direct reference to the internal list,
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

    /**
     * Creates a new {@link Map} if the parameters are valid.
     *
     * @param size  size of the map
     * @param name  name of the map
     * @param scale scale factor of the map
     * @return a new Map object if parameters are valid; otherwise, null
     */
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
     * Saves a map object to a serialized file (.ser).
     *
     * @param map      the map to serialize
     * @param filePath the destination file path (e.g., "maps/portugal.ser")
     * @return true if the map was saved successfully, false otherwise
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
            return (Map) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading map: " + e.getMessage());
            return null;
        }
    }
}
