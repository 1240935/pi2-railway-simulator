package pt.ipp.isep.dei.controller;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for managing the logic of creating an industry on a map.
 * <p>
 * Interacts with the domain layer and repositories to:
 * <ul>
 *     <li>Access available maps</li>
 *     <li>Select a map and industry type</li>
 *     <li>Validate coordinates</li>
 *     <li>Create and persist industry instances</li>
 * </ul>
 */
public class CreateIndustryController {
    private Map selectedMap;
    private IndustryType selectedType;

    /**
     * Creates an empty instance of the controller.
     */
    public CreateIndustryController() {}

    /**
     * Retrieves the list of all maps from the repository.
     *
     * @return list of {@link Map} objects
     */
    public List<Map> getAvailableMaps() {
        return Repositories.getInstance().getMapRepository().getMaps();
    }

    /**
     * Retrieves the names of all available maps.
     * Useful for displaying in a UI without exposing full map objects.
     *
     * @return list of map names
     */
    public List<String> getAvailableMapNames() {
        List<String> names = new ArrayList<>();
        for (Map map : getAvailableMaps()) {
            names.add(map.getName());
        }
        return names;
    }

    /**
     * Sets the selected map by its index from the available list.
     *
     * @param index index of the selected map
     * @throws IllegalArgumentException if the index is out of bounds
     */
    public void setMapByIndex(int index) {
        List<Map> maps = getAvailableMaps();
        if (index >= 0 && index < maps.size()) {
            this.selectedMap = maps.get(index);
        } else {
            throw new IllegalArgumentException("Invalid map index");
        }
    }

    /**
     * Retrieves a list of available industry type names.
     *
     * @return list of industry type names as strings
     */
    public List<String> getAvailableIndustryTypeNames() {
        List<String> names = new ArrayList<>();
        for (IndustryType type : IndustryType.values()) {
            names.add(type.name());
        }
        return names;
    }

    /**
     * Sets the selected industry type by index.
     *
     * @param index index of the selected industry type
     * @throws IllegalArgumentException if the index is invalid
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
     * Checks if a pair of coordinates is valid within the selected map.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if coordinates are within bounds, false otherwise
     */
    public boolean checkCoordinates(int x, int y) {
        if (selectedMap == null) return false;
        return selectedMap.checkCoordinates(new Position(x, y));
    }

    /**
     * Creates and registers an industry using the current selected type and position.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return description of the created industry
     * @throws IllegalStateException if map or industry type has not been selected
     */
    public String createIndustry(int x, int y) {
        if (selectedMap == null || selectedType == null) {
            throw new IllegalStateException("Map or IndustryType not set.");
        }
        Position position = new Position(x, y);
        Industry industry = new Industry(selectedType, position);
        Repositories.getInstance().getIndustryRepository().add(industry);
        return industry.toString();
    }

    /**
     * Creates a {@link Position} object after validating its bounds against the selected map.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return new Position object
     * @deprecated Use {@link #checkCoordinates(int, int)} and {@link #createIndustry(int, int)} instead
     */
    public Position createPosition(int x, int y) {
        int size = selectedMap.getSize();
        if (x >= size || y >= size || x < 0 || y < 0) {
            System.out.println("Invalid position: coordinates exceed map bounds.");
            System.exit(1);
        }
        return new Position(x, y);
    }

    /**
     * Validates if a {@link Position} is inside the selected map bounds.
     *
     * @param position position to check
     * @return true if valid, false otherwise
     */
    public boolean checkCoordinates(Position position) {
        return selectedMap != null && selectedMap.checkCoordinates(position);
    }

    /**
     * Returns the available industry types as enum values.
     * Used in older UIs.
     *
     * @return array of {@link IndustryType}
     */
    public IndustryType[] getAvailableIndustriesTypes() {
        return IndustryType.values();
    }

    /**
     * Sets the selected industry type directly.
     *
     * @param type the industry type to select
     */
    public void setType(IndustryType type) {
        this.selectedType = type;
    }

    /**
     * Returns available resource types (not currently used in this controller).
     *
     * @return array of {@link ResourceType}
     */
    public ResourceType[] getAvailableResourceTypes() {
        return ResourceType.values();
    }

    /**
     * Creates an industry directly from a given position.
     * Used by legacy UI.
     *
     * @param pos position for the industry
     * @return new {@link Industry} object
     */
    public Industry createIndustry(Position pos) {
        return new Industry(selectedType, pos);
    }
}
