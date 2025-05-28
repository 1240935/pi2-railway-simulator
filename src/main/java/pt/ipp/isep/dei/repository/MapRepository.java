package pt.ipp.isep.dei.repository;
import java.io.FileWriter;
import java.io.IOException;
import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.dto.MapDTO;
import pt.ipp.isep.dei.dto.MapMapper;

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

    public void saveMapToCSV(Map map, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("MapName;" + map.getName() + "\n");
            writer.write("MapSize;" + map.getSize() + "\n");
            writer.write("MapScale;" + map.getScale() + "\n\n");

            writer.write("Cities\n");
            writer.write("Name;X;Y\n");
            for (var city : map.getCities()) {
                writer.write(city.getName() + ";" + city.getPosition().getxCoordinate() + ";" + city.getPosition().getyCoordinate() + "\n");
            }

            writer.write("\nIndustries\n");
            writer.write("Type;X;Y\n");
            for (var industry : map.getIndustries()) {
                writer.write(industry.getType() + ";" + industry.getPosition().getxCoordinate() + ";" + industry.getPosition().getyCoordinate() + "\n");
            }

            System.out.println("Map saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving map: " + e.getMessage());
        }
    }
}
