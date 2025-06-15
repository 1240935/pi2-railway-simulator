package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.LocomotiveModel;
import pt.ipp.isep.dei.dto.LocomotiveDTO;

import java.util.List;

/**
 * GUI class for the "Buy Locomotive" screen.
 * Allows the player to select a scenario and a locomotive model, shows the cost and player's budget,
 * and enables the player to buy the selected locomotive if budget allows.
 */
public class BuyLocomotiveGUI {

    @FXML
    private ComboBox<String> scenarioComboBox; // Dropdown for selecting scenario

    @FXML
    private ComboBox<String> modelComboBox; // Dropdown for selecting locomotive model

    @FXML
    private Label budgetLabel; // Displays the player's current budget

    @FXML
    private Label costLabel; // Displays the cost of the selected locomotive model

    @FXML
    private Button buyButton; // Button to confirm and buy the selected locomotive

    private final BuyLocomotiveController controller = new BuyLocomotiveController();

    private Player player; // The current player using this GUI

    private int selectedScenarioIndex = -1; // Index of selected scenario
    private int selectedModelIndex = -1;    // Index of selected locomotive model

    /**
     * Sets the player for this GUI and updates the budget label.
     * @param player The Player instance
     */
    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);
        updateBudgetLabel();
    }

    /**
     * Initializes GUI components, loads scenarios,
     * and sets event handlers for combo boxes and buy button.
     * This method is automatically called by JavaFX after FXML loading.
     */
    @FXML
    public void initialize() {
        // When scenario is selected, load available locomotive models for that scenario
        scenarioComboBox.setOnAction(event -> {
            selectedScenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (selectedScenarioIndex >= 0) {
                loadModels();
            }
        });

        // When a locomotive model is selected, update the cost label
        modelComboBox.setOnAction(event -> {
            selectedModelIndex = modelComboBox.getSelectionModel().getSelectedIndex();
            updateCostLabel();
        });

        // Buy button click event triggers purchase logic
        buyButton.setOnAction(event -> handleBuy());

        // Initially load all available scenarios
        loadScenarios();
    }

    /**
     * Loads the list of available scenarios from the controller and populates the scenario combo box.
     * Selects the first scenario by default if any are available.
     * Shows alert and disables UI if no scenarios exist.
     */
    private void loadScenarios() {
        List<String> scenarios = controller.getAvailableScenarioNames();
        if (scenarios.isEmpty()) {
            showAlert("No scenarios available.");
            disableAll();
            return;
        }

        scenarioComboBox.getItems().setAll(scenarios);
        scenarioComboBox.getSelectionModel().selectFirst();
        selectedScenarioIndex = 0;
        loadModels();
    }

    /**
     * Loads available locomotive models for the currently selected scenario
     * and populates the model combo box.
     * Shows alert if no models are available.
     */
    private void loadModels() {
        modelComboBox.getItems().clear();
        selectedModelIndex = -1;
        costLabel.setText("-");

        if (selectedScenarioIndex < 0) return;

        List<String> models = controller.getAvailableModelDescriptions(selectedScenarioIndex);
        if (models.isEmpty()) {
            showAlert("No locomotive models available for the selected scenario.");
            return;
        }

        modelComboBox.getItems().setAll(models);
        modelComboBox.getSelectionModel().selectFirst();
        selectedModelIndex = 0;
        updateCostLabel();
    }

    /**
     * Updates the budget label to show the player's current budget.
     */
    private void updateBudgetLabel() {
        budgetLabel.setText(player != null ? String.valueOf(player.getBudget()) : "-");
    }

    /**
     * Updates the cost label to show the cost of the selected locomotive model.
     * Shows "-" if no valid selection is made.
     */
    private void updateCostLabel() {
        if (selectedScenarioIndex >= 0 && selectedModelIndex >= 0) {
            LocomotiveModel model = controller.getModelByIndex(selectedModelIndex, selectedScenarioIndex);
            costLabel.setText(String.valueOf(model.getCost()));
        } else {
            costLabel.setText("-");
        }
    }

    /**
     * Handles the buying process when the buy button is clicked.
     * Checks if the player has enough budget and tries to create the locomotive.
     * Shows alerts for errors or success.
     */
    private void handleBuy() {
        if (player == null) {
            showAlert("Player not set.");
            return;
        }

        if (selectedScenarioIndex < 0 || selectedModelIndex < 0) {
            showAlert("Please select a valid scenario and locomotive model.");
            return;
        }

        LocomotiveModel model = controller.getModelByIndex(selectedModelIndex, selectedScenarioIndex);
        if (player.getBudget() < model.getCost()) {
            showAlert("Not enough budget to buy this locomotive.");
            return;
        }

        LocomotiveDTO dto = new LocomotiveDTO();
        dto.setLocomotiveModel(model);

        try {
            controller.createLocomotive(dto, selectedScenarioIndex);
            showAlert("Locomotive successfully bought!");
            updateBudgetLabel();
        } catch (Exception e) {
            showAlert("Purchase failed: " + e.getMessage());
        }
    }

    /**
     * Disables all interactive GUI components.
     * Used when no scenarios or models are available.
     */
    private void disableAll() {
        scenarioComboBox.setDisable(true);
        modelComboBox.setDisable(true);
        buyButton.setDisable(true);
    }

    /**
     * Displays an informational alert dialog with the provided message.
     * @param message The message to display
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
