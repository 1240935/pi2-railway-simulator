package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.*;
import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for handling the logic
 * related to buying locomotives within a given scenario by a player.
 * It manages scenario selection, locomotive model filtering based on scenario,
 * player budget checking, and locomotive creation.
 */
public class BuyLocomotiveController {

    private Player player;

    /**
     * Default constructor for BuyLocomotiveController.
     */
    public BuyLocomotiveController() {}

    /**
     * Retrieves the list of available scenarios from the repository.
     *
     * @return List of all scenarios.
     */
    public List<Scenario> getAvailableScenarios() {
        return Repositories.getInstance().getScenarioRepository().getScenarios();
    }

    /**
     * Returns a list of descriptive strings for each available scenario,
     * formatted as "Map: [map name], Year: [year]".
     *
     * @return List of scenario descriptions.
     */
    public List<String> getAvailableScenarioNames() {
        ScenarioListMapper scenarioListMapper = new ScenarioListMapper();
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();
        List<ScenarioDTO> scenarioDTOS = scenarioListMapper.toDTOList(scenarios);

        List<String> names = new ArrayList<>();
        for (ScenarioDTO scenarioDTO : scenarioDTOS) {
            names.add(scenarioDTO.toString());
        }

        return names;
    }

    /**
     * Returns the scenario at the specified index from the scenario repository.
     *
     * @param index The index of the scenario.
     * @return The Scenario object at the given index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public Scenario getScenarioByIndex(int index) {
        List<Scenario> scenarios = Repositories.getInstance().getScenarioRepository().getScenarios();

        if (index < 0 || index >= scenarios.size()) {
            throw new IndexOutOfBoundsException("Invalid scenario index: " + index);
        }

        return scenarios.get(index);
    }

    /**
     * Retrieves a specific locomotive model by its index in the filtered list
     * of available locomotive models for the selected scenario.
     *
     * @param index the index of the locomotive model to retrieve.
     * @param scenarioIndex the index of the selected scenario.
     * @return the LocomotiveModel at the specified index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public LocomotiveModel getModelByIndex(int index, int scenarioIndex) {
        List<LocomotiveModelDTO> models = getAvailableLocomotiveModels(scenarioIndex);

        if (index < 0 || index >= models.size()) {
            throw new IndexOutOfBoundsException("Invalid locomotive model index: " + index);
        }

        LocomotiveModelDTO chosenDTO = models.get(index);
        return LocomotiveModel.valueOf(chosenDTO.getName());
    }

    /**
     * Retrieves a filtered list of locomotive models that are allowed
     * in the current scenario based on locomotive type and scenario year.
     *
     * @param scenarioIndex The index of the scenario for filtering models.
     * @return List of locomotive model DTOs available for purchase.
     */
    public List<LocomotiveModelDTO> getAvailableLocomotiveModels(int scenarioIndex) {
        LocomotiveModelListMapper locomotiveModelListMapper = new LocomotiveModelListMapper();
        List<LocomotiveModel> allModels = Repositories.getInstance()
                .getLocomotiveModelRepository()
                .getLocomotiveModels();

        List<LocomotiveModel> available = new ArrayList<>();

        List<LocomotiveType> allowedTypesInScenario = getScenarioByIndex(scenarioIndex).getLocomotiveTypes();
        int scenarioYear = getScenarioByIndex(scenarioIndex).getYear();

        for (LocomotiveModel model : allModels) {
            if (allowedTypesInScenario.contains(model.getType()) &&
                    model.getStartYear() <= scenarioYear) {
                available.add(model);
            }
        }
        return locomotiveModelListMapper.toDTO(available);
    }

    /**
     * Sets the current player who will purchase the locomotive.
     *
     * @param player Player instance.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Returns descriptive strings for all available locomotive models,
     * including stats like power, acceleration, top speed, cost, and type.
     *
     * @param scenarioIndex The index of the scenario to filter locomotive models.
     * @return List of locomotive model descriptions.
     */
    public List<String> getAvailableModelDescriptions(int scenarioIndex) {
        List<String> descriptions = new ArrayList<>();
        for (LocomotiveModelDTO model : getAvailableLocomotiveModels(scenarioIndex)) {
            String desc = model.getName() +
                    " | Power: " + model.getPower() +
                    " | Acceleration: " + model.getAcceleration() +
                    " | Top Speed: " + model.getTopSpeed() +
                    " | Year: " + model.getOperationStartYear() +
                    " | Fuel Cost: " + model.getFuelCost() +
                    " | Maintenance/Year: " + model.getMaintenancePYear() +
                    " | Cost: " + model.getCost() +
                    " | Type: " + model.getLocomotiveType();
            descriptions.add(desc);
        }
        return descriptions;
    }

    /**
     * Creates a new locomotive for the player if they have sufficient budget.
     * Deducts the cost from the player's budget and adds the locomotive to the scenario and repository.
     *
     * @param locomotiveDTO Data Transfer Object containing locomotive details.
     * @param scenarioIndex The index of the scenario where the locomotive will be added.
     */
    public void createLocomotive(LocomotiveDTO locomotiveDTO, int scenarioIndex) {
        LocomotiveMapper locomotiveMapper = new LocomotiveMapper();
        int iD = Repositories.getInstance().getLocomotiveRepository().nextID();
        Locomotive locomotive = locomotiveMapper.toLocomotive(locomotiveDTO, iD);
        player.deductBudget(locomotiveDTO.getLocomotiveModel().getCost());
        Scenario scenario = getScenarioByIndex(scenarioIndex);
        scenario.addLocomotive(locomotive);
        Repositories.getInstance().getLocomotiveRepository().addLocomotive(locomotive);
    }
}
