package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.repository.CityRepository;

public class CityMapper {
    public City toCity(CityDTO dto) {
        return CityRepository.createCity(
                dto.getxCoordinate(),
                dto.getyCoordinate(),
                dto.getName(),
                dto.getCityBlocks(),
                dto.getMapSize()
        );
    }
}