package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.CityRepository;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for the business logic of adding a new city to a map.
 *
 * It manages selection of the map, city name, city center, and city blocks,
 * as well as validation and creation of the city entity.
 */
public class AddCityController {

    /**
     * Constructs an AddCityController instance.
     * Initializes the list of selected positions for city blocks.
     */
    public AddCityController() {
    }

    /**
     * Retrieves a list of all available maps from the repository.
     *
     * @return List of Map objects available.
     */
    public List<Map> getAvailableMaps() {
        return Repositories.getInstance().getMapRepository().getMaps();
    }

    /**
     * Returns the names of all available maps.
     * Useful for UI display without exposing map objects.
     *
     * @return a list of map names
     */
    public List<String> getAvailableMapNames() {
        List<String> names = new ArrayList<>();
        for (Map map : getAvailableMaps()) {
            names.add(map.getName());
        }
        return names;
    }

    /**
     * Selects a map from the available maps using its index.
     *
     * @param index the index of the selected map
     * @throws IllegalArgumentException if the index is invalid
     */
    public Map getMapByIndex(int index) {
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        if (index >= 0 && index < maps.size()) {
            Map map = maps.get(index);
            return map;
        } else {
            throw new IllegalArgumentException("Invalid map index");
        }
    }

    public Position createPosition(int x, int y){
        return City.createPosition(x, y);
    }

    /**
     * Creates the city using the selected name, center, and blocks,
     * and adds it to the city repository.
     *
     * Validates city name and coordinates before creation.
     * Throws IllegalArgumentException on invalid inputs.
     */
    public void createCity(Position position, String name, List<CityBlock> blocks, int mapIndex) {
        City city = CityRepository.createCity(position, name, blocks);
        Repositories.getInstance().getCityRepository().addCity(city);
        getMapByIndex(mapIndex).addCity(city);
    }
}
