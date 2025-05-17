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
        selectedPositions = new ArrayList<>();
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
     * Sets the list of selected city blocks.
     *
     * @param blocks List of CityBlock objects to assign.
     */
    public void setSelectedBlocks(List<CityBlock> blocks){
        selectedBlocks = blocks;
    }

    /**
     * Sets the name of the city to be created.
     *
     * @param name The city name.
     */
    public void setSelectedName(String name){
        selectedName = name;
    }

    /**
     * Sets the center position of the city.
     *
     * @param position The Position object representing city center coordinates.
     */
    public void setCenter(Position position){
        center = position;
    }

    /**
     * Sets the selected map where the city will be added.
     *
     * @param map The Map object.
     */
    public void setMap(Map map) {
        this.selectedMap = map;
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
     * Validates the city name to ensure it contains only letters and spaces.
     *
     * @param name The city name to validate.
     * @return True if the name is valid; false otherwise.
     */
    public boolean checkName(String name) {
        return name.matches("^[a-zA-Z\\s]+$");
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
     * Creates a new Position object with given x and y coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return A new Position object.
     */
    public Position createPosition(int x, int y) {
        return new Position(x, y);
    }

    /**
     * Checks if the given position is valid within the selected map.
     *
     * @param position The Position to validate.
     * @return True if coordinates are valid in the map; false otherwise.
     */
    public boolean checkCoordinates(Position position) {
        return selectedMap != null && selectedMap.checkCoordinates(position);
    }

    /**
     * Creates a list of city blocks at specified positions.
     *
     * @param positions List of Position objects where blocks should be created.
     * @param cityblocks Number of city blocks to create.
     * @return List of CityBlock objects created.
     */
    public List<CityBlock> createBlocks(List<Position> positions, int cityblocks){
        List<CityBlock> blocks = new ArrayList<>();
        for(int i = 0; i < cityblocks; i++){
            CityBlock block = new CityBlock(positions.get(i));
            blocks.add(block);
        }
        return blocks;
    }

    /**
     * Generates city blocks randomly or fixed around a center position.
     *
     * If randomize is true, blocks are placed near the center with some variation.
     * If false, all blocks are placed at the center.
     *
     * @param center The central Position of the city.
     * @param number The number of blocks to generate.
     * @param randomize Whether to randomize the block positions around the center.
     * @return List of generated CityBlock objects.
     */
    public List<CityBlock> generateBlocks(Position center, int number, boolean randomize) {
        List<CityBlock> blocks = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < number; i++) {
            Position pos;
            if (randomize) {
                int dx = (int) Math.round(rand.nextGaussian() * 2);
                int dy = (int) Math.round(rand.nextGaussian() * 2);
                pos = new Position(center.getxCoordinate() + dx, center.getyCoordinate() + dy);
            } else {
                pos = center;
            }
            blocks.add(new CityBlock(pos));
        }

        return blocks;
    }

    /**
     * Adds a new position to the list of selected positions for city blocks.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void addSelectedPositions(int x, int y){
        selectedPositions.add(createPosition(x,y));
    }

    /**
     * Creates the city using the selected name, center, and blocks,
     * and adds it to the city repository.
     *
     * Validates city name and coordinates before creation.
     * Throws IllegalArgumentException on invalid inputs.
     */
    public void createCity() {
        if (!checkName(selectedName)) {
            throw new IllegalArgumentException("Invalid city name");
        }
        if (!checkCoordinates(center)) {
            throw new IllegalArgumentException("Invalid position");
        }

        City city = new City(selectedName, center, selectedBlocks);
        Repositories.getInstance().getCityRepository().addCity(city);
    }
}
