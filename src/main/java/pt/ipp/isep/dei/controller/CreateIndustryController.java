package pt.ipp.isep.dei.controller;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.Repositories;


public class CreateIndustryController {
    private final Repositories repositories = Repositories.getInstance();
    private final MapListMapper mapMapper = new MapListMapper();
    private final IndustryTypeListMapper typeMapper = new IndustryTypeListMapper();
    private final IndustryMapper industryMapper = new IndustryMapper();

    public List<String> getAvailableMapNames() {
        List<Map> maps = repositories.getMapRepository().getMaps();
        List<MapDTO> mapDTOs = mapMapper.toDTOList(maps);

        List<String> names = new ArrayList<String>();
        for (int i = 0; i < mapDTOs.size(); i++) {
            names.add(mapDTOs.get(i).getName());
        }

        return names;
    }

    public int getMapSizeByIndex(int index) {
        List<Map> maps = repositories.getMapRepository().getMaps();
        return maps.get(index).getSize();
    }

    public List<String> getAvailableIndustryTypeNames() {
        List<IndustryType> types = repositories.getIndustryTypeRepository().getIndustryTypes();
        List<IndustryTypeDTO> dtos = typeMapper.toDTOList(types);

        List<String> names = new ArrayList<String>();
        for (int i = 0; i < dtos.size(); i++) {
            names.add(dtos.get(i).getName());
        }

        return names;
    }

    public boolean createIndustry(IndustryDTO dto) {
        List<Map> maps = repositories.getMapRepository().getMaps();
        Map selectedMap = maps.get(dto.getMapIndex());

        Industry industry = industryMapper.fromDTO(dto, selectedMap, repositories.getIndustryTypeRepository());

        if (!industry.checkPosition(industry.getPosition(), selectedMap.getSize())) {
            return false;
        }

        if (!industry.checkIndustryType(industry.getIndustryType())) {
            return false;
        }

        repositories.getIndustryRepository().addIndustry(industry);
        selectedMap.addIndustry(industry);

        return true;
    }
}
