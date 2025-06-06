package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.ScenarioRepository;

public class ScenarioMapper {
    public Scenario toScenario(ScenarioDTO scenarioDTO){
        return ScenarioRepository.createScenario(
                scenarioDTO.getMap(),
                scenarioDTO.getYear(),
                scenarioDTO.getLocomotiveTypes(),
                scenarioDTO.getImportation(),
                scenarioDTO.getExportation(),
                scenarioDTO.getChanger()
        );
    }
}
