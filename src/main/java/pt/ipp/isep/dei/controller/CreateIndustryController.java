package pt.ipp.isep.dei.controller;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.Repositories;

/**
 * Controller responsible for the creation and management of Industry entities.
 * Handles interaction with repositories and mapping between DTOs and domain models.
 */
public class CreateIndustryController {

    // Singleton instance of Repositories to access data
    private final Repositories repositories = Repositories.getInstance();

    // Mapper to convert Map objects to DTOs
    private final MapListMapper mapMapper = new MapListMapper();

    // Mapper to convert IndustryType objects to DTOs
    private final IndustryTypeListMapper typeMapper = new IndustryTypeListMapper();

    // Mapper to convert IndustryDTO to Industry domain model
    private final IndustryMapper industryMapper = new IndustryMapper();

    /**
     * Retrieves a list of available map names from the repository.
     *
     * @return list of map names
     */
    public List<String> getAvailableMapNames() {
        List<Map> maps = repositories.getMapRepository().getMaps();
        List<MapDTO> mapDTOs = mapMapper.toDTOList(maps);

        List<String> names = new ArrayList<>();
        for (int i = 0; i < mapDTOs.size(); i++) {
            names.add(mapDTOs.get(i).getName());
        }

        return names;
    }

    /**
     * Retrieves the size of a map by its index.
     *
     * @param index index of the map
     * @return size of the map
     */
    public int getMapSizeByIndex(int index) {
        List<Map> maps = repositories.getMapRepository().getMaps();
        return maps.get(index).getSize();
    }

    /**
     * Retrieves an IndustryType by its index.
     *
     * @param index index of the IndustryType
     * @return corresponding IndustryType
     */
    public IndustryType getIndustryTypeByIndex(int index) {
        List<IndustryType> industryTypes = repositories.getIndustryTypeRepository().getIndustryTypes();
        return industryTypes.get(index);
    }

    /**
     * Retrieves the names of all available industry types.
     *
     * @return list of industry type names
     */
    public List<String> getAvailableIndustryTypeNames() {
        List<IndustryType> types = repositories.getIndustryTypeRepository().getIndustryTypes();
        List<IndustryTypeDTO> dtos = typeMapper.toDTOList(types);

        List<String> names = new ArrayList<>();
        for (IndustryTypeDTO dto : dtos) {
            names.add(dto.toString());
        }

        return names;
    }

    /**
     * Creates a new industry and adds it to the selected map and repository.
     *
     * @param dto      DTO containing industry data
     * @param mapIndex index of the selected map
     */
    public void createIndustry(IndustryDTO dto, int mapIndex) {
        List<Map> maps = repositories.getMapRepository().getMaps();
        Map selectedMap = maps.get(mapIndex);

        Industry industry = industryMapper.toIndustry(dto);
        repositories.getIndustryRepository().addIndustry(industry);
        selectedMap.addIndustry(industry);
    }
}
