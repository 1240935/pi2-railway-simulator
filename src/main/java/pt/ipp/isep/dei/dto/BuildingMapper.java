package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Building;
import pt.ipp.isep.dei.repository.BuildingRepository;

public class BuildingMapper {
    public Building toBuilding(BuildingDTO dto) {
        return BuildingRepository.createBuilding(dto.getBuildingType());
    }
}
