package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Scenario;
import pt.ipp.isep.dei.domain.LocomotiveModel;

import java.util.List;

public class BuyLocomotiveGUI {

    @FXML
    private ComboBox<String> scenarioComboBox;

    @FXML
    private ComboBox<String> modelComboBox;

    @FXML
    private Label budgetLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Button buyButton;

    private final BuyLocomotiveController controller = new BuyLocomotiveController();

    private Player player;

    private int selectedScenarioIndex = -1;
    private int selectedModelIndex = -1;

    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);
        updateBudgetLabel();
    }

    @FXML
    public void initialize() {scenarioComboBox.setOnAction(event -> {
            selectedScenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (selectedScenarioIndex >= 0) {
                loadModels();
            }
        });

        modelComboBox.setOnAction(event -> {
            selectedModelIndex = modelComboBox.getSelectionModel().getSelectedIndex();
            updateCostLabel();
        });

        buyButton.setOnAction(event -> handleBuy());

        loadScenarios();
    }

    private void loadScenarios() {
        List<String> scenarios = controller.getAvailableScenarioDescriptions();
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

    private void loadModels() {
        if (selectedScenarioIndex < 0) {
            modelComboBox.getItems().clear();
            selectedModelIndex = -1;
            updateCostLabel();
            return;
        }

        List<String> models = controller.getAvailableModelDescriptions(selectedScenarioIndex);
        modelComboBox.getItems().setAll(models);

        if (!models.isEmpty()) {
            modelComboBox.getSelectionModel().selectFirst();
            selectedModelIndex = 0;
        } else {
            selectedModelIndex = -1;
        }

        updateCostLabel();
    }

    private void updateBudgetLabel() {
        budgetLabel.setText(player != null ? String.valueOf(player.getBudget()) : "-");
    }



    private void updateCostLabel() {
        if (selectedScenarioIndex >= 0 && selectedModelIndex >= 0) {
            LocomotiveModel model = controller.getModelByIndex(selectedModelIndex, selectedScenarioIndex);
            costLabel.setText(String.valueOf(model.getCost()));
        } else {
            costLabel.setText("-");
        }
    }



    private void handleBuy() {
        if (player == null) {
            showAlert("Player not set.");
            return;
        }
        if (selectedScenarioIndex < 0 || selectedModelIndex < 0) {
            showAlert("Please select a valid scenario and model.");
            return;
        }

        Scenario scenario = controller.getScenarioByIndex(selectedScenarioIndex);
        LocomotiveModel model = controller.getModelByIndex(selectedModelIndex, selectedScenarioIndex);

        if (player.getBudget() < model.getCost()) {
            showAlert("Not enough budget to buy this locomotive.");
            return;
        }

        try {
            controller.createLocomotive(model, scenario);
            showAlert("Locomotive successfully bought!");
            updateBudgetLabel();
        } catch (Exception e) {
            showAlert("Purchase failed: " + e.getMessage());
        }
    }

    private void disableAll() {
        scenarioComboBox.setDisable(true);
        modelComboBox.setDisable(true);
        buyButton.setDisable(true);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
