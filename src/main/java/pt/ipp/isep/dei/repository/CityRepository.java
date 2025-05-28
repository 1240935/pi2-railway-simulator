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

    public static City createCity(Position position, String name, List<CityBlock> blocks) {

    }
}
