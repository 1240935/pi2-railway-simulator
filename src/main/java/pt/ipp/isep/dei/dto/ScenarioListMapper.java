package pt.ipp.isep.dei.dto;
import pt.ipp.isep.dei.domain.Scenario;

import java.util.ArrayList;
import java.util.List;

public class ScenarioListMapper {
    public List<ScenarioDTO> toDTOList(List<Scenario> scenarios) {
        List<ScenarioDTO> result = new ArrayList<>();
        for (Scenario scenario : scenarios) {
            ScenarioDTO dto = new ScenarioDTO();
            dto.setChanger(scenario.getIndustryChangers());
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
