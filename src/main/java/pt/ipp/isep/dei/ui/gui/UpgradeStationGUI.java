package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.UpgradeStationController;
import pt.ipp.isep.dei.domain.BuildingType;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.dto.BuildingDTO;


import java.util.List;

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

    private int scenarioIndex = -1;
    private int stationIndex = -1;
    private int buildingTypeIndex = -1;

    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);
        budgetLabel.setText("Budget: " + player.getBudget());
    }

    public void initialize() {
        loadScenarios();

        scenarioComboBox.setOnAction(e -> {
            scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex() + 1;
            if (scenarioIndex > 0) {
                loadStations();
            }
        });

        stationComboBox.setOnAction(e -> {
            stationIndex = stationComboBox.getSelectionModel().getSelectedIndex() + 1;
            if (stationIndex > 0) {
                loadBuildingTypes();
            }
        });

        buildingTypeComboBox.setOnAction(e -> {
            buildingTypeIndex = buildingTypeComboBox.getSelectionModel().getSelectedIndex() + 1;
            if (buildingTypeIndex > 0) {
                updateBuildingCost();
            }
        });

        buyButton.setOnAction(e -> handleBuy());
    }

    private void loadScenarios() {
        scenarioComboBox.getItems().clear();
        List<String> scenarioNames = controller.getAvailableScenarioNames();
        scenarioComboBox.getItems().addAll(scenarioNames);
    }

    private void loadStations() {
        stationComboBox.getItems().clear();
        buildingTypeComboBox.getItems().clear();
        costLabel.setText("Cost: 0");
        feedbackLabel.setText("");
        buyButton.setDisable(true);

        List<String> stationNames = controller.getAvailableStationNames(scenarioIndex);
        stationComboBox.getItems().addAll(stationNames);
    }

    private void loadBuildingTypes() {
        buildingTypeComboBox.getItems().clear();
        costLabel.setText("Cost: 0");
        feedbackLabel.setText("");
        buyButton.setDisable(true);

        List<String> buildingNames = controller.getAvailableBuildingTypeNames(scenarioIndex, stationIndex);
        buildingTypeComboBox.getItems().addAll(buildingNames);
    }

    private void updateBuildingCost() {
        feedbackLabel.setText("");
        if (scenarioIndex > 0 && stationIndex > 0 && buildingTypeIndex > 0) {
            BuildingType type = controller.getBuildingTypeByIndex(scenarioIndex, stationIndex, buildingTypeIndex);
            costLabel.setText("Cost: " + type.getCost());
            buyButton.setDisable(false);
        }
    }

    private void handleBuy() {
        feedbackLabel.setText("");

        if (scenarioIndex <= 0 || stationIndex <= 0 || buildingTypeIndex <= 0) {
            feedbackLabel.setText("Please make all selections.");
            feedbackLabel.setStyle("-fx-text-fill: darkred;");
            return;
        }

        BuildingType type = controller.getBuildingTypeByIndex(scenarioIndex, stationIndex, buildingTypeIndex);

        int cost = type.getCost();

        if (cost > player.getBudget()) {
            feedbackLabel.setText("Not enough budget to upgrade.");
            feedbackLabel.setStyle("-fx-text-fill: darkred;");
            return;
        }

        // Confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Purchase");
        alert.setHeaderText("Confirm upgrade");
        alert.setContentText("This will cost " + cost + ". Proceed?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                BuildingDTO buildingDTO = new BuildingDTO();
                controller.createBuilding(buildingDTO, stationIndex);
                feedbackLabel.setText("Station upgraded successfully!");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                budgetLabel.setText("Budget: " + player.getBudget());
                buyButton.setDisable(true);
            } else {
                feedbackLabel.setText("Purchase cancelled.");
                feedbackLabel.setStyle("-fx-text-fill: darkorange;");
            }
        });
    }
}
