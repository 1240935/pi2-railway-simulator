package pt.ipp.isep.dei.ui.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;

/**
 * GUI controller for the "Build Station" feature.
 * <p>
 * Manages the user interface for selecting scenarios, station types,
 * coordinates, compass directions, and station naming.
 * Coordinates user input with the BuildStationController to create stations,
 * update player budget, and provide user feedback.
 * </p>
 */
public class BuildStationGUI {

    private final BuildStationController controller = new BuildStationController();
    private Player player;

    @FXML
    private ComboBox<String> scenarioComboBox; // Dropdown to select the scenario

    @FXML
    private TextField xField, yField, nameField, compassDirectionField; // Input fields for position, name, and compass direction

    @FXML
    private ComboBox<String> stationTypeComboBox; // Dropdown for selecting station type

    @FXML
    private Label feedbackLabel, costLabel, budgetLabel; // Labels to display feedback, cost, and current budget

    @FXML
    private HBox compassDirectionBox; // Container for compass direction input, shown only for certain station types

    @FXML
    private Button buyButton, proposeNameButton; // Buttons for purchasing station and proposing a name

    private boolean initialized = false; // Flag to ensure initialization happens only once

    /**
     * Sets the current player and initializes the UI if not already done.
     * Updates the UI fields with player-related data such as budget.
     *
     * @param player the Player currently logged in and building stations
     */
    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);
        if (!initialized) {
            start();
            initialized = true;
        }
        populateFields();
    }

    /**
     * Initializes UI components and their event handlers.
     * Populates scenario and station type dropdowns.
     * Sets up dynamic UI behavior based on selections.
     */
    public void start() {
        // Clear any existing items before populating
        scenarioComboBox.getItems().clear();
        stationTypeComboBox.getItems().clear();

        // Populate scenario and station type combo boxes with data from controller
        scenarioComboBox.setItems(FXCollections.observableArrayList(controller.getAvailableScenarioDescriptions()));
        stationTypeComboBox.setItems(FXCollections.observableArrayList(controller.getAvailableStationTypes()));

        // Enable station type selection
        stationTypeComboBox.setDisable(false);

        // Show/hide compass direction input and update cost when station type changes
        stationTypeComboBox.setOnAction(e -> {
            int index = stationTypeComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                boolean isStation = controller.isSelectedTypeStation(index);
                // Only show compass direction box if the selected type is a Station (not Depot or Terminal)
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

        // Update feedback and budget when scenario changes
        scenarioComboBox.setOnAction(e -> {
            int index = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                feedbackLabel.setText("");
                budgetLabel.setText(String.valueOf(player.getBudget()));
            }
        });

        // Propose a station name based on position, scenario, and station type
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
                    feedbackLabel.setText(""); // Clear any previous error feedback
                }
            } catch (Exception ex) {
                feedbackLabel.setStyle("-fx-text-fill: darkred;");
                feedbackLabel.setText("Error proposing name: " + ex.getMessage());
            }
        });

        // Attempt to create and buy the station with the specified parameters
        buyButton.setOnAction(e -> {
            try {
                int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
                int typeIndex = stationTypeComboBox.getSelectionModel().getSelectedIndex();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());

                // Use proposed name if name field is empty
                String direction = compassDirectionField.getText();
                String name = nameField.getText().isEmpty()
                        ? controller.getRecommendedName(x, y, scenarioIndex, typeIndex)
                        : nameField.getText();

                // Call controller to create the station
                controller.createStationGUI(
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

                // Indicate success and update budget display
                feedbackLabel.setStyle("-fx-text-fill: green;");
                feedbackLabel.setText("Station built successfully!");
                budgetLabel.setText(String.valueOf(player.getBudget()));

            } catch (Exception ex) {
                feedbackLabel.setStyle("-fx-text-fill: red;");
                feedbackLabel.setText("Error: " + ex.getMessage());
            }
        });

        // Initially hide compass direction input (only relevant for some station types)
        compassDirectionBox.setVisible(false);
        compassDirectionBox.setManaged(false);

        // Initialize cost and budget labels
        costLabel.setText("0");
        budgetLabel.setText(String.valueOf(player.getBudget()));
    }

    /**
     * Populates UI fields related to player data, such as the budget label.
     * Called after player is set or when UI is refreshed.
     */
    private void populateFields() {
        if (player != null) {
            budgetLabel.setText(String.valueOf(player.getBudget()));
        }
    }
}
