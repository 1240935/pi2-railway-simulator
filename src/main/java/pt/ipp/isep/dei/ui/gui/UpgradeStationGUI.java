package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.UpgradeStationController;
import pt.ipp.isep.dei.domain.BuildingType;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.dto.BuildingDTO;

/**
 * GUI controller for the "Upgrade Station" feature.
 * <p>
 * Allows a player to select a scenario, then a station within that scenario,
 * and finally a building type to upgrade the station with.
 * The interface shows the player’s current budget, the cost of the selected upgrade,
 * and provides feedback on purchase success or failure.
 * <p>
 * Coordinates with {@link UpgradeStationController} for business logic and data retrieval.
 * Handles user input events, updates UI elements accordingly,
 * and manages budget validation and purchase confirmation dialogs.
 */
public class UpgradeStationGUI {

    @FXML
    private ComboBox<String> scenarioComboBox;      // Dropdown to select the scenario

    @FXML
    private ComboBox<String> stationComboBox;       // Dropdown to select the station within the scenario

    @FXML
    private ComboBox<String> buildingTypeComboBox;  // Dropdown to select the building type for upgrade

    @FXML
    private Label budgetLabel;                       // Displays player's current budget

    @FXML
    private Label costLabel;                         // Displays cost of selected building upgrade

    @FXML
    private Button buyButton;                        // Button to confirm and perform the purchase

    @FXML
    private Label feedbackLabel;                     // Label to show feedback messages to the user

    private final UpgradeStationController controller = new UpgradeStationController();
    private Player player;

    /**
     * Sets the current player, initializing the controller and updating the budget display.
     * Also triggers loading of available scenarios.
     *
     * @param player the current player
     */
    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);
        budgetLabel.setText(String.valueOf(player.getBudget()));
        loadScenarios();
    }

    /**
     * Initializes the GUI controls and sets event handlers.
     * Disables the buy button until valid selections are made.
     * Event handlers load dependent dropdown options and update cost and feedback labels.
     */
    public void initialize() {
        buyButton.setDisable(true);

        scenarioComboBox.setOnAction(e -> {
            int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (scenarioIndex >= 0) {
                loadStations(scenarioIndex);
            }
        });

        stationComboBox.setOnAction(e -> {
            int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            int stationIndex = stationComboBox.getSelectionModel().getSelectedIndex();
            if (stationIndex >= 0) {
                loadBuildingTypes(scenarioIndex, stationIndex);
            }
        });

        buildingTypeComboBox.setOnAction(e -> {
            int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            int stationIndex = stationComboBox.getSelectionModel().getSelectedIndex();
            int buildingTypeIndex = buildingTypeComboBox.getSelectionModel().getSelectedIndex();
            if (buildingTypeIndex >= 0) {
                BuildingType type = controller.getBuildingTypeByIndex(scenarioIndex, stationIndex, buildingTypeIndex);
                costLabel.setText(String.valueOf(type.getCost()));
                buyButton.setDisable(false);
                feedbackLabel.setText("");
            } else {
                costLabel.setText("0");
                buyButton.setDisable(true);
            }
        });

        buyButton.setOnAction(e -> handleBuy());
    }

    /**
     * Loads available scenarios into the scenario combo box.
     * Clears stations and building types selections and resets related UI fields.
     */
    private void loadScenarios() {
        scenarioComboBox.getItems().clear();
        scenarioComboBox.getItems().addAll(controller.getAvailableScenarioNames());

        stationComboBox.getItems().clear();
        buildingTypeComboBox.getItems().clear();
        costLabel.setText("0");
        feedbackLabel.setText("");
        buyButton.setDisable(true);
    }

    /**
     * Loads stations available for the selected scenario.
     * Clears building types and resets cost and feedback labels.
     *
     * @param scenarioIndex index of the selected scenario
     */
    private void loadStations(int scenarioIndex) {
        stationComboBox.getItems().clear();
        stationComboBox.getItems().addAll(controller.getAvailableStationNames(scenarioIndex));

        buildingTypeComboBox.getItems().clear();
        costLabel.setText("0");
        feedbackLabel.setText("");
        buyButton.setDisable(true);
    }

    /**
     * Loads building types available for the selected scenario and station.
     * Resets cost, feedback labels, and disables the buy button.
     *
     * @param scenarioIndex index of the selected scenario
     * @param stationIndex  index of the selected station
     */
    private void loadBuildingTypes(int scenarioIndex, int stationIndex) {
        buildingTypeComboBox.getItems().clear();
        buildingTypeComboBox.getItems().addAll(controller.getAvailableBuildingTypeNames(scenarioIndex, stationIndex));

        costLabel.setText("0");
        feedbackLabel.setText("");
        buyButton.setDisable(true);
    }

    /**
     * Handles the purchase action when the buy button is clicked.
     * Validates selections and budget, prompts for confirmation,
     * creates the building upgrade, deducts budget, updates UI,
     * and displays feedback messages.
     */
    private void handleBuy() {
        int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
        int stationIndex = stationComboBox.getSelectionModel().getSelectedIndex();
        int buildingTypeIndex = buildingTypeComboBox.getSelectionModel().getSelectedIndex();

        if (scenarioIndex < 0 || stationIndex < 0 || buildingTypeIndex < 0) {
            showError("Please make all selections.");
            return;
        }

        BuildingType selectedType = controller.getBuildingTypeByIndex(scenarioIndex, stationIndex, buildingTypeIndex);
        int cost = selectedType.getCost();

        if (player.getBudget() < cost) {
            showError("Not enough budget.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Purchase");
        alert.setHeaderText("Upgrade Station");
        alert.setContentText("This will cost " + cost + ". Proceed?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    BuildingDTO dto = new BuildingDTO();
                    dto.setBuildingType(selectedType);

                    controller.createBuilding(dto, stationIndex);  // Uses index from current scenario's stations

                    player.deductBudget(cost);
                    budgetLabel.setText(String.valueOf(player.getBudget()));

                    feedbackLabel.setText("Upgrade successful!");
                    feedbackLabel.setStyle("-fx-text-fill: green;");
                    buyButton.setDisable(true);
                    costLabel.setText("0");
                    buildingTypeComboBox.getSelectionModel().clearSelection();
                } catch (Exception e) {
                    showError("Upgrade failed: " + e.getMessage());
                }
            } else {
                feedbackLabel.setText("Upgrade canceled.");
                feedbackLabel.setStyle("-fx-text-fill: orange;");
            }
        });
    }

    /**
     * Shows an error message in the feedback label and disables the buy button.
     *
     * @param message the error message to display
     */
    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: darkred;");
        buyButton.setDisable(true);
    }
}
