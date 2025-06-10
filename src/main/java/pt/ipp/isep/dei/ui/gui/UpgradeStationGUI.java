package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.UpgradeStationController;
import pt.ipp.isep.dei.domain.*;

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

    private UpgradeStationController controller = new UpgradeStationController();
    private Player player;

    private Scenario selectedScenario;
    private Station selectedStation;
    private BuildingType selectedBuildingType;

    public void setPlayer(Player player) {
        this.player = player;
        budgetLabel.setText(String.valueOf(player.getBudget()));

        if (controller != null) {
            controller.setPlayer(player);
        }
    }

    public void initialize() {

        List<String> scenarios = controller.getAvailableScenarioNames();
        scenarioComboBox.getItems().clear();
        scenarioComboBox.getItems().addAll(scenarios);


        scenarioComboBox.setOnAction(e -> {
            int index = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                selectedScenario = controller.setScenarioByIndex(index);
                updateStations();
            }
        });

        stationComboBox.setOnAction(e -> {
            int index = stationComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0 && selectedScenario != null) {
                selectedStation = controller.setStationByIndex(index, selectedScenario);
                updateBuildingTypes();
            }
        });

        buildingTypeComboBox.setOnAction(e -> {
            int index = buildingTypeComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0 && selectedScenario != null && selectedStation != null) {
                selectedBuildingType = controller.setBuildingTypeByIndex(index, selectedScenario, selectedStation);
                updateCostLabel();
                updateBuyButtonState();
            }
        });

        buyButton.setOnAction(e -> handleBuyButton());

        stationComboBox.setDisable(true);
        buildingTypeComboBox.setDisable(true);
        buyButton.setDisable(true);
    }

    private void updateStations() {
        stationComboBox.getItems().clear();
        buildingTypeComboBox.getItems().clear();
        selectedStation = null;
        selectedBuildingType = null;
        buyButton.setDisable(true);
        feedbackLabel.setText("");

        if (selectedScenario != null) {
            List<String> stations = controller.getAvailableStationNames(selectedScenario);
            stationComboBox.getItems().addAll(stations);
            stationComboBox.setDisable(stations.isEmpty());
        } else {
            stationComboBox.setDisable(true);
        }
        buildingTypeComboBox.setDisable(true);
        costLabel.setText("0");
    }

    private void updateBuildingTypes() {
        buildingTypeComboBox.getItems().clear();
        selectedBuildingType = null;
        buyButton.setDisable(true);
        feedbackLabel.setText("");

        if (selectedScenario != null && selectedStation != null) {
            List<String> buildings = controller.getAvailableBuildingNames(selectedScenario, selectedStation);
            buildingTypeComboBox.getItems().addAll(buildings);
            buildingTypeComboBox.setDisable(buildings.isEmpty());
        } else {
            buildingTypeComboBox.setDisable(true);
        }
        costLabel.setText("0");
    }

    private void updateCostLabel() {
        if (selectedBuildingType != null) {
            int cost = controller.getSelectedBuildingCost(selectedBuildingType);
            costLabel.setText(String.valueOf(cost));
        } else {
            costLabel.setText("0");
        }
    }

    private void updateBuyButtonState() {
        buyButton.setDisable(selectedBuildingType == null);
    }

    private void handleBuyButton() {
        feedbackLabel.setText("");
        if (selectedScenario == null || selectedStation == null || selectedBuildingType == null) {
            feedbackLabel.setStyle("-fx-text-fill: darkred;");
            feedbackLabel.setText("Please select scenario, station and building type.");
            return;
        }

        System.out.println("Attempting to buy building: " + selectedBuildingType);
        System.out.println("Player budget before purchase: " + player.getBudget());

        if (controller.possibleBuilding(selectedBuildingType)) {
            controller.createBuilding(selectedStation, selectedBuildingType);
            System.out.println("Building purchased successfully.");
            System.out.println("Player budget after purchase: " + player.getBudget());

            feedbackLabel.setStyle("-fx-text-fill: green;");
            feedbackLabel.setText("Station upgraded successfully!");
            budgetLabel.setText(String.valueOf(player.getBudget()));
        } else {
            feedbackLabel.setStyle("-fx-text-fill: darkred;");
            feedbackLabel.setText("Not enough budget to upgrade this station.");
            System.out.println("Purchase failed: Not enough budget.");
        }
    }

}
