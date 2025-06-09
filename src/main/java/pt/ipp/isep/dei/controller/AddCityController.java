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

    public int getMapSizeByIndex(int index) {
        List<Map> maps = Repositories.getInstance().getMapRepository().getMaps();
        return maps.get(index).getSize();
    }


    public List<CityBlock> createBlocks(CityBlockDTO cityBlockDTO){
        CityBlockMapper cityBlockMapper = new CityBlockMapper();
        List<CityBlock> cityBlocks;
        cityBlocks = cityBlockMapper.toCityBlock(cityBlockDTO);
        for (CityBlock block : cityBlocks) {
            Repositories.getInstance().getCityBlockRepository().addCityBlock(block);
        }
        return cityBlocks;
    }

    public List<Position> createPositions(int[] x, int[] y){
        CityBlock cityBlock = new CityBlock();
        return cityBlock.createPositions(x, y);
    }

    public void createCity(CityDTO cityDTO) {
        CityMapper cityMapper = new CityMapper();
        City city = cityMapper.toCity(cityDTO);
        Repositories.getInstance().getCityRepository().addCity(city);
    }
}
