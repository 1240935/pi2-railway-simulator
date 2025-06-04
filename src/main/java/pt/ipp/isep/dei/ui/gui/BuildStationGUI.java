package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;

import java.util.List;

public class BuildStationGUI {

    @FXML
    private ComboBox<String> scenarioComboBox;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private ComboBox<String> stationTypeComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField compassDirectionField;
    @FXML
    private HBox compassDirectionBox;
    @FXML
    private Button proposeNameButton;
    @FXML
    private Button buyButton;
    @FXML
    private Label feedbackLabel;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label costLabel;

    private final BuildStationController controller = new BuildStationController();

    public void setPlayer(Player player) {
        controller.setPlayer(player);
        loadScenarios();
        updateBudgetLabel();
    }

    private void loadScenarios() {
        List<String> scenarios = controller.getAvailableScenarioDescriptions();
        scenarioComboBox.getItems().clear();
        scenarioComboBox.getItems().addAll(scenarios);
    }

    private void loadStationTypes() {
        List<String> stationTypes = controller.getAvailableStationTypes();
        stationTypeComboBox.getItems().clear();
        stationTypeComboBox.getItems().addAll(stationTypes);
    }

    private void updateBudgetLabel() {
        budgetLabel.setText("Budget: " + controller.getPlayer().getBudget());
    }

    private void updateCompassDirectionVisibility() {
        boolean isStation = controller.isSelectedTypeStation();
        compassDirectionBox.setVisible(isStation);
        compassDirectionBox.setManaged(isStation);
    }

    @FXML
    public void initialize() {
        scenarioComboBox.setOnAction(event -> {
            int index = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                controller.setScenario(index);
                loadStationTypes();
                clearFields();
            }
        });

        stationTypeComboBox.setOnAction(event -> {
            int index = stationTypeComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                controller.setType(index);
                updateCompassDirectionVisibility();
                updateCostLabel();
            }
        });

        proposeNameButton.setOnAction(event -> proposeName());
        buyButton.setOnAction(event -> handleBuy());
    }

    private void updateCostLabel() {
        if (controller.getSelectedType() != null) {
            costLabel.setText("Cost: " + controller.getSelectedType().getCost());
        } else {
            costLabel.setText("Cost: N/A");
        }
    }

    private void proposeName() {
        try {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            String proposedName = controller.getRecommendedName(x, y);
            nameField.setText(proposedName);
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid coordinates for proposing name.");
        }
    }

    private void handleBuy() {
        try {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());

            if (!controller.checkOverbuilding(x, y)) {
                feedbackLabel.setText("Invalid position on the map.");
                return;
            }

            controller.setPosition(x, y);

            int typeIndex = stationTypeComboBox.getSelectionModel().getSelectedIndex();
            if (!controller.setType(typeIndex)) {
                feedbackLabel.setText("Invalid station type.");
                return;
            }

            controller.determineStationCargoes(x, y);

            if (controller.isSelectedTypeStation()) {
                String direction = compassDirectionField.getText().trim();
                controller.setCompassCenter(direction, x, y);
            } else {
                controller.setGeometricCenter();
            }

            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                feedbackLabel.setText("Station name cannot be empty.");
                return;
            }
            controller.setName(name);

            boolean success = controller.createStation(x, y);
            if (success) {
                feedbackLabel.setText("Station '" + name + "' created successfully.");
                updateBudgetLabel();
                clearFields();
            } else {
                feedbackLabel.setText("Not enough budget to buy this station.");
            }

        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid input. Please enter valid numbers for coordinates.");
        }
    }

    private void clearFields() {
        xField.clear();
        yField.clear();
        nameField.clear();
        compassDirectionField.clear();
        feedbackLabel.setText("");
        costLabel.setText("Cost: N/A");
        updateCompassDirectionVisibility();
    }
}