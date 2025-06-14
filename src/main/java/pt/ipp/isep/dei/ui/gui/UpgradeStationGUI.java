package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.UpgradeStationController;
import pt.ipp.isep.dei.domain.BuildingType;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.dto.BuildingDTO;

public class UpgradeStationGUI {

    @FXML
    private ComboBox<String> scenarioComboBox;

    @FXML
    private ComboBox<String> stationComboBox;

    @FXML
    private ComboBox<String> buildingTypeComboBox;

    @FXML
    private Label budgetLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Button buyButton;

    @FXML
    private Label feedbackLabel;

    private final UpgradeStationController controller = new UpgradeStationController();
    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);
        budgetLabel.setText(String.valueOf(player.getBudget()));
        loadScenarios();
    }

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

    private void loadScenarios() {
        scenarioComboBox.getItems().clear();
        scenarioComboBox.getItems().addAll(controller.getAvailableScenarioNames());

        stationComboBox.getItems().clear();
        buildingTypeComboBox.getItems().clear();
        costLabel.setText("0");
        feedbackLabel.setText("");
        buyButton.setDisable(true);
    }

    private void loadStations(int scenarioIndex) {
        stationComboBox.getItems().clear();
        stationComboBox.getItems().addAll(controller.getAvailableStationNames(scenarioIndex));

        buildingTypeComboBox.getItems().clear();
        costLabel.setText("0");
        feedbackLabel.setText("");
        buyButton.setDisable(true);
    }

    private void loadBuildingTypes(int scenarioIndex, int stationIndex) {
        buildingTypeComboBox.getItems().clear();
        buildingTypeComboBox.getItems().addAll(controller.getAvailableBuildingTypeNames(scenarioIndex, stationIndex));

        costLabel.setText("0");
        feedbackLabel.setText("");
        buyButton.setDisable(true);
    }

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

    private void showError(String message) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: darkred;");
        buyButton.setDisable(true);
    }
}
