package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.Map;
import pt.ipp.isep.dei.dto.MapDTO;
import pt.ipp.isep.dei.dto.MapMapper;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for creating and managing Map entities.
 * Handles the logic for converting DTOs into domain objects and saving them to the repository.
 */
public class CreateMapController {

    /**
     * Default constructor for CreateMapController.
     */
    public CreateMapController() {
    }

    /**
     * Creates a MapDTO object from the provided parameters.
     *
     * @param size  the size of the map (typically its width/height)
     * @param name  the name of the map
     * @param scale the scale of the map (units per coordinate)
     * @return a MapDTO object with the specified attributes
     */
    public MapDTO createMapDTO(int size, String name, int scale){
        return new MapDTO(size, name, scale);
    }

    /**
     * Converts a MapDTO into a Map domain object and saves it into the repository.
     *
     * @param mapDTO the DTO containing map data
     */
    public void createMap(MapDTO mapDTO) {
        MapMapper mapMapper = new MapMapper();
        Map map = mapMapper.toMap(mapDTO);
        Repositories.getInstance().getMapRepository().addMap(map);
    }
}
