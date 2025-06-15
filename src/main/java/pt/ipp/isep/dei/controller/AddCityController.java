package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.CityBlockRepository;
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
     * Retrieves the names of all available maps from the repository.
     *
     * @return a list of map names as Strings.
     */
    public List<String> getAvailableMapNames() {
        MapListMapper mapListMapper = new MapListMapper();
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        List<MapDTO> mapDTOs = mapListMapper.toDTOList(maps);

        List<String> names = new ArrayList<String>();
        for (MapDTO mapDTO : mapDTOs) {
            names.add(mapDTO.getName());
        }

        return names;
    }

    /**
     * Gets the size of a map by its index in the repository.
     *
     * @param index Index of the map in the list.
     * @return the size of the map.
     */
    public int getMapSizeByIndex(int index) {
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        return maps.get(index).getSize();
    }

    /**
     * Creates and stores city blocks based on a CityBlockDTO.
     *
     * @param cityBlockDTO DTO containing data to create city blocks.
     * @return a list of created CityBlock objects.
     */
    public List<CityBlock> createBlocks(CityBlockDTO cityBlockDTO) {
        CityBlockMapper cityBlockMapper = new CityBlockMapper();
        List<CityBlock> cityBlocks;
        cityBlocks = cityBlockMapper.toCityBlock(cityBlockDTO);
        for (CityBlock block : cityBlocks) {
            Repositories.getInstance().getCityBlockRepository().addCityBlock(block);
        }
        return cityBlocks;
    }

    /**
     * Creates a list of Position objects from arrays of X and Y coordinates.
     *
     * @param x array of X coordinates.
     * @param y array of Y coordinates.
     * @return a list of Position objects.
     */
    public List<Position> createPositions(int[] x, int[] y) {
        CityBlock cityBlock = new CityBlock();
        return cityBlock.createPositions(x, y);
    }

    /**
     * Creates and stores a city using the provided CityDTO.
     *
     * @param cityDTO DTO containing data for the city to be created.
     */
    public void createCity(CityDTO cityDTO) {
        CityMapper cityMapper = new CityMapper();
        City city = cityMapper.toCity(cityDTO);
        Repositories.getInstance().getCityRepository().addCity(city);
    }
}
