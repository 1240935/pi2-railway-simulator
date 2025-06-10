package pt.ipp.isep.dei.ui.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;


public class BuildStationGUI {

    private final BuildStationController controller = new BuildStationController();
    private Player player;

    @FXML
    private ComboBox<String> scenarioComboBox;

    @FXML
    private TextField xField, yField, nameField, compassDirectionField;

    @FXML
    private ComboBox<String> stationTypeComboBox;

    @FXML
    private Label feedbackLabel, costLabel, budgetLabel;

    @FXML
    private HBox compassDirectionBox;

    @FXML
    private Button buyButton, proposeNameButton;

    private boolean initialized = false;

    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);
        if (!initialized) {
            start();
            initialized = true;
        }
        populateFields();
    }

    public void start() {
        scenarioComboBox.getItems().clear();
        stationTypeComboBox.getItems().clear();

        scenarioComboBox.setItems(FXCollections.observableArrayList(controller.getAvailableScenarioDescriptions()));
        stationTypeComboBox.setItems(FXCollections.observableArrayList(controller.getAvailableStationTypes()));

        stationTypeComboBox.setDisable(false);

        stationTypeComboBox.setOnAction(e -> {
            int index = stationTypeComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                boolean isStation = controller.isSelectedTypeStation(index);
                compassDirectionBox.setVisible(isStation);
                compassDirectionBox.setManaged(isStation);

                int cost = controller.getStationTypeByIndex(index).getCost();
                costLabel.setText(String.valueOf(cost));
            } else {
                compassDirectionBox.setVisible(false);
                compassDirectionBox.setManaged(false);
                costLabel.setText("0");
            }
        });

        scenarioComboBox.setOnAction(e -> {
            int index = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                // Optionally do something on scenario change, like reset feedback or update budget
                feedbackLabel.setText("");
                budgetLabel.setText(String.valueOf(player.getBudget()));
            }
        });

        proposeNameButton.setOnAction(e -> {
            try {
                int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
                int typeIndex = stationTypeComboBox.getSelectionModel().getSelectedIndex();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());

                String proposed = controller.getRecommendedName(x, y, scenarioIndex, typeIndex);

                if (proposed == null || proposed.isEmpty()) {
                    feedbackLabel.setStyle("-fx-text-fill: darkred;");
                    feedbackLabel.setText("No nearby city found within the allowed radius. Please enter a name manually.");
                    nameField.clear();
                } else {
                    nameField.setText(proposed);
                    feedbackLabel.setText(""); // Clear feedback if name proposed
                }
            } catch (Exception ex) {
                feedbackLabel.setStyle("-fx-text-fill: darkred;");
                feedbackLabel.setText("Error proposing name: " + ex.getMessage());
            }
        });


        buyButton.setOnAction(e -> {
            try {
                int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
                int typeIndex = stationTypeComboBox.getSelectionModel().getSelectedIndex();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());

                String direction = compassDirectionField.getText();
                String name = nameField.getText().isEmpty()
                        ? controller.getRecommendedName(x, y, scenarioIndex, typeIndex)
                        : nameField.getText();

                controller.createStation(
                        controller.createPosition(x, y),
                        name,
                        controller.getCenter(direction, x, y, typeIndex),
                        controller.getStationTypeByIndex(typeIndex),
                        controller.getDemandedCargoes(x, y, scenarioIndex, typeIndex),
                        controller.getSuppliedCargoes(x, y, scenarioIndex, typeIndex),
                        player,
                        scenarioIndex,
                        typeIndex,
                        controller.getMap(scenarioIndex),
                        controller.getScenarioByIndex(scenarioIndex)
                );

                feedbackLabel.setStyle("-fx-text-fill: green;");
                feedbackLabel.setText("Station built successfully!");

                // Update budget display after purchase
                budgetLabel.setText(String.valueOf(player.getBudget()));

            } catch (Exception ex) {
                feedbackLabel.setStyle("-fx-text-fill: red;");
                feedbackLabel.setText("Error: " + ex.getMessage());
            }
        });

        compassDirectionBox.setVisible(false);
        compassDirectionBox.setManaged(false);

        costLabel.setText("0");
        budgetLabel.setText(String.valueOf(player.getBudget()));
    }

    private void populateFields() {
        if (player != null) {
            budgetLabel.setText(String.valueOf(player.getBudget()));
        }
    }
}

