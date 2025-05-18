package pt.ipp.isep.dei.controller;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for managing the process of creating industries on a map.
 * <p>
 * It provides UI-independent methods to:
 * <ul>
 *     <li>Retrieve and select available maps</li>
 *     <li>Retrieve and select available industry types</li>
 *     <li>Validate coordinates</li>
 *     <li>Create and persist new industry instances</li>
 * </ul>
 */
public class CreateIndustryController {

    private Map selectedMap;
    private IndustryType selectedType;
    private Position selectedPosition;

    /**
     * Constructs an empty instance of the controller.
     */
    public CreateIndustryController() {}

    /**
     * Sets the selected position for the industry.
     *
     * @param selectedPosition the position to be selected
     */
    public void setSelectedPosition(Position selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /**
     * Creates a new {@link Position} instance with the specified coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return a new Position object
     */
    public Position createPosition(int x, int y){
        Position pos = new Position(x, y);
        return pos;
    }

    /**
     * Retrieves all available maps from the repository.
     *
     * @return a list of {@link Map} objects
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
    public void setMapByIndex(int index) {
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        if (index >= 0 && index < maps.size()) {
            this.selectedMap = maps.get(index);
        } else {
            throw new IllegalArgumentException("Invalid map index");
        }
    }

    /**
     * Returns the names of all defined industry types.
     *
     * @return a list of industry type names
     */
    public List<String> getAvailableIndustryTypeNames() {
        List<String> names = new ArrayList<>();
        for (IndustryType type : IndustryType.values()) {
            names.add(type.name());
        }
        return names;
    }

    /**
     * Selects an industry type using its index from the {@link IndustryType} enum.
     *
     * @param index the index of the industry type
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public void setIndustryTypeByIndex(int index) {
        IndustryType[] types = IndustryType.values();
        if (index >= 0 && index < types.length) {
            this.selectedType = types[index];
        } else {
            throw new IllegalArgumentException("Invalid industry type index");
        }
    }

    /**
     * Checks if the given coordinates are valid within the selected map.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return true if the coordinates are valid, false otherwise
     */
    public boolean checkCoordinates(int x, int y) {
        if (selectedMap == null) return false;
        return selectedMap.checkCoordinates(new Position(x, y));
    }

    /**
     * Directly sets the selected industry type.
     *
     * @param type the industry type to set
     */
    public void setType(IndustryType type) {
        this.selectedType = type;
    }

    /**
     * Creates and persists a new industry at the specified coordinates,
     * if they are valid within the selected map.
     *
     * @param x the X coordinate for the new industry
     * @param y the Y coordinate for the new industry
     */
    public void createIndustry(int x, int y) {
        int size = selectedMap.getSize();
        if (x >= size || y >= size || x < 0 || y < 0) {
            System.out.println("Invalid position: coordinates exceed map bounds.");
            return;
        }
        Position position = new Position(x, y);
        Industry industry = new Industry(selectedType, position);
        Repositories.getInstance().getIndustryRepository().addIndustry(industry);
        selectedMap.addIndustry(industry);
    }
}
