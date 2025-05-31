package pt.ipp.isep.dei.controller;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.IndustryRepository;
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


    /**
     * Constructs an empty instance of the controller.
     */
    public CreateIndustryController() {}

    /**
     * Creates a new {@link Position} instance with the specified coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return a new Position object
     */
    public Position createPosition(int x, int y){
        return Industry.createPosition(x,y);
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
    public Map getMapByIndex(int index) {
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        if (index >= 0 && index < maps.size()) {
            return maps.get(index);
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
        for (IndustryType type : Repositories.getInstance().getIndustryTypeRepository().getIndustryTypes()) {
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
    public IndustryType getIndustryTypeByIndex(int index) {
        IndustryType[] types = IndustryType.values();
        if (index >= 0 && index < types.length) {
            return types[index];
        } else {
            throw new IllegalArgumentException("Invalid industry type index");
        }
    }


    public void createIndustry(IndustryType industryType, Position position, int mapSize, int mapIndex) {
        Industry industry = IndustryRepository.createIndustry(industryType,position,mapSize);
        Repositories.getInstance().getIndustryRepository().addIndustry(industry);
        getMapByIndex(mapIndex).addIndustry(industry);
    }
}
