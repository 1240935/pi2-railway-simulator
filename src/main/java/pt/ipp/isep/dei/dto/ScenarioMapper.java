package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.repository.ScenarioRepository;

/**
 * Mapper class responsible for converting a {@link ScenarioDTO}
 * into a {@link Scenario} domain object.
 */
public class ScenarioMapper {

    /**
     * Converts a {@link ScenarioDTO} to a {@link Scenario} domain object.
     *
     * This method delegates the creation of the {@code Scenario} to the {@link ScenarioRepository}.
     *
     * @param scenarioDTO the {@code ScenarioDTO} to convert
     * @return the created {@code Scenario} domain object
     */
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
