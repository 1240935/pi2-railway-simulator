package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.BuyLocomotiveController;
import pt.ipp.isep.dei.domain.Player;

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

    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);
        loadScenarios();
        updateBudgetLabel();
    }

    @FXML
    public void start() {
        scenarioComboBox = new ComboBox<>();
        modelComboBox = new ComboBox<>();
        buyButton = new Button();
        scenarioComboBox.setOnAction(event -> {
            int selectedIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                controller.chooseScenarioByIndex(selectedIndex);
                loadModels();
                updateCostLabel();
            }
        });

        modelComboBox.setOnAction(event -> {
            int selectedIndex = modelComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                controller.chooseModelByIndex(selectedIndex);
                updateCostLabel();
            }
        });

        buyButton.setOnAction(event -> handleBuy());
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
        controller.chooseScenarioByIndex(0);
        loadModels();
    }

    private void loadModels() {
        List<String> models = controller.getAvailableModelDescriptions();
        modelComboBox.getItems().setAll(models);
        if (!models.isEmpty()) {
            modelComboBox.getSelectionModel().selectFirst();
            controller.chooseModelByIndex(0);
        }
        updateCostLabel();
    }

    private void updateBudgetLabel() {
        if (player != null) {
            budgetLabel.setText("Player budget: " + player.getBudget());
        } else {
            budgetLabel.setText("Player budget: -");
        }
    }

    private void updateCostLabel() {
        if (controller.getSelectedModel() != null) {
            costLabel.setText("Locomotive cost: " + controller.getSelectedModel().getCost());
        } else {
            costLabel.setText("Locomotive cost: -");
        }
    }

    private void handleBuy() {
        if (player == null) {
            showAlert("Player not set.");
            return;
        }
        if (controller.getSelectedModel() == null) {
            showAlert("No locomotive model selected.");
            return;
        }
        if (player.getBudget() < controller.getSelectedModel().getCost()) {
            showAlert("Not enough budget to buy this locomotive.");
            return;
        }

        boolean success = controller.createLocomotive();
        if (success) {
            showAlert("Locomotive successfully bought!");
            updateBudgetLabel();
        } else {
            showAlert("Purchase failed.");
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
