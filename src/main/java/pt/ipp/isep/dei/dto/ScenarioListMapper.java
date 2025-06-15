package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Scenario;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class responsible for converting a list of {@link Scenario} domain objects
 * into a list of {@link ScenarioDTO} data transfer objects.
 */
public class ScenarioListMapper {

    /**
     * Converts a list of {@link Scenario} objects to a list of {@link ScenarioDTO} objects.
     *
     * @param scenarios the list of {@code Scenario} domain objects to convert
     * @return a list of {@code ScenarioDTO} objects representing the given scenarios
     */
    public List<ScenarioDTO> toDTOList(List<Scenario> scenarios) {
        List<ScenarioDTO> result = new ArrayList<>();
        for (Scenario scenario : scenarios) {
            ScenarioDTO dto = new ScenarioDTO();
            dto.setChanger(scenario.getIndustryChanger());
            dto.setExportation(scenario.getExportation());
            dto.setImportation(scenario.getImportation());
            dto.setYear(scenario.getYear());
            dto.setMap(scenario.getMap());
            dto.setLocomotiveTypes(scenario.getLocomotiveTypes());
            result.add(dto);
        }
        return result;
    }
}
