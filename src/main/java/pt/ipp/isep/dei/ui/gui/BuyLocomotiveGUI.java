package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.LocomotiveModel;
import pt.ipp.isep.dei.dto.LocomotiveDTO;

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
    public void initialize() {
        scenarioComboBox.setOnAction(event -> {
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
