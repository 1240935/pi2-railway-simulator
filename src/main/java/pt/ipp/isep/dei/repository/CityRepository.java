package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing City objects.
 */
public class CityRepository {
    private final ArrayList<City> cities = new ArrayList<>();

    /**
     * Adds a new City to the repository.
     *
     * @param city the City object to add
     */
    public void addCity(City city) {
        cities.add(city);
    }

    /**
     * Retrieves all City objects stored in the repository.
     *
     * @return a list of City objects
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     * Creates a City with given parameters after validating them.
     *
     * @param x      X coordinate for the city's position
     * @param y      Y coordinate for the city's position
     * @param name   Name of the city
     * @param blocks List of CityBlock objects representing city blocks
     * @param size   Size parameter for position validation
     * @return the created City if parameters are valid; null otherwise
     */
    public static City createCity(int x, int y, String name, List<CityBlock> blocks, int size) {
        Position position = City.createPosition(x, y);
        City city = new City(name, position, blocks);
        if (city.getPosition().checkPosition(position, size) && city.checkName(name) && city.checkCityBlocks(blocks)) {
            System.out.println("City created successfully!");
            return city;
        } else {
            System.out.println("Invalid city parameters.");
            return null;
        }
    }
}
