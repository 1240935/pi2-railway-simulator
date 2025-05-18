package pt.ipp.isep.dei.controller;

import pt.ipp.isep.dei.domain.*;
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

    private Scenario scenario;
    private LocomotiveModel selectedModel;
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
     * Sets the current scenario to the given scenario.
     *
     * @param scenario Scenario selected by the user.
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Returns a list of descriptive strings for each available scenario,
     * formatted as "Map: [map name], Year: [year]".
     *
     * @return List of scenario descriptions.
     */
    public List<String> getAvailableScenarioDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Scenario s : getAvailableScenarios()) {
            descriptions.add("Map: " + s.getMap().getName() + ", Year: " + s.getYear());
        }
        return descriptions;
    }

    /**
     * Selects a scenario by its index in the available scenarios list.
     *
     * @param index Index of the scenario in the list.
     */
    public void chooseScenarioByIndex(int index) {
        List<Scenario> scenarios = getAvailableScenarios();
        this.scenario = scenarios.get(index);
    }

    /**
     * Retrieves a filtered list of locomotive models that are allowed
     * in the current scenario based on locomotive type and scenario year.
     *
     * @return List of locomotive models available for purchase.
     */
    public List<LocomotiveModel> getAvailableLocomotiveModels() {
        List<LocomotiveModel> allModels = Repositories.getInstance()
                .getLocomotiveModelRepository()
                .getLocomotiveModels();

        List<LocomotiveModel> available = new ArrayList<>();

        List<LocomotiveType> allowedTypesInScenario = scenario.getLocomotiveTypes();
        int scenarioYear = scenario.getYear();

        for (LocomotiveModel model : allModels) {
            if (allowedTypesInScenario.contains(model.getType()) &&
                    model.getStartYear() <= scenarioYear) {
                available.add(model);
            }
        }
        return available;
    }

    /**
     * Sets the locomotive model that the player has selected.
     *
     * @param model The locomotive model chosen.
     */
    public void setModel(LocomotiveModel model) {
        this.selectedModel = model;
    }

    /**
     * Gets the locomotive model currently selected by the player.
     *
     * @return Selected LocomotiveModel.
     */
    public LocomotiveModel getSelectedModel() {
        return selectedModel;
    }

    /**
     * Selects a locomotive model by its index in the available models list.
     *
     * @param index Index of the model in the available models list.
     */
    public void chooseModelByIndex(int index) {
        this.selectedModel = getAvailableLocomotiveModels().get(index);
    }

    /**
     * Gets the current player performing the purchase.
     *
     * @return Player instance.
     */
    public Player getPlayer() {
        return player;
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
     * @return List of model descriptions.
     */
    public List<String> getAvailableModelDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (LocomotiveModel model : getAvailableLocomotiveModels()) {
            String desc = model.name() +
                    " | Power: " + model.getPower() +
                    " | Acceleration: " + model.getAcceleration() +
                    " | Top Speed: " + model.getTopSpeed() +
                    " | Year: " + model.getStartYear() +
                    " | Fuel Cost: " + model.getFuelCost() +
                    " | Maintenance/Year: " + model.getMaintenancePYear() +
                    " | Cost: " + model.getCost() +
                    " | Type: " + model.getType();
            descriptions.add(desc);
        }
        return descriptions;
    }

    /**
     * Creates a new locomotive for the player if they have sufficient budget.
     * Deducts the cost from the player's budget and adds the locomotive to the repository.
     *
     * @return true if locomotive was successfully created and purchased, false if budget is insufficient.
     */
    public boolean createLocomotive() {
        LocomotiveModel model = selectedModel;
        if (!player.canAfford(model.getCost())) {
            return false;
        }
        int id = Repositories.getInstance().getLocomotiveRepository().nextID();
        Locomotive loco = new Locomotive(id, selectedModel);
        player.deductBudget(model.getCost());
        scenario.addLocomotive(loco);
        Repositories.getInstance().getLocomotiveRepository().addLocomotive(loco);
        return true;
    }
}
