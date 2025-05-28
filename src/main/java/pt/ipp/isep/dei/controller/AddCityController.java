package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller class responsible for the business logic of adding a new city to a map.
 *
 * It manages selection of the map, city name, city center, and city blocks,
 * as well as validation and creation of the city entity.
 */
public class AddCityController {
    private Map selectedMap;
    private List<Position> selectedPositions;
    private Position center;
    private String selectedName;
    private List<CityBlock> selectedBlocks;

    /**
     * Constructs an AddCityController instance.
     * Initializes the list of selected positions for city blocks.
     */
    public AddCityController() {
    }

    /**
     * Returns the list of positions selected for city blocks.
     *
     * @return List of Position objects representing block locations.
     */
    public List<Position> getSelectedPositions() {
        return selectedPositions;
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
     * Selects a map from a list based on the user's choice.
     *
     * @param maps List of available Map objects.
     * @param choice The user selected index (1-based).
     * @return The selected Map object.
     */
    public Map chooseMap(List<Map> maps, int choice){
        return maps.get(choice - 1);
    }

    /**
     * Retrieves a list of the names of all available maps.
     *
     * @return List of map names as strings.
     */
    public List<String> getAvailableMapNames() {
        List<String> names = new ArrayList<>();
        for (Map map : getAvailableMaps()) {
            names.add(map.getName());
        }
        return names;
    }


    /**
     * Creates the city using the selected name, center, and blocks,
     * and adds it to the city repository.
     *
     * Validates city name and coordinates before creation.
     * Throws IllegalArgumentException on invalid inputs.
     */
    public void createCity() {
        City city = new City(selectedName, center, selectedBlocks);
        selectedMap.addCity(city);
        Repositories.getInstance().getCityRepository().addCity(city);
    }
}
