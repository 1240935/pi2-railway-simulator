package pt.ipp.isep.dei.ui.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.SimulatorController;
import pt.ipp.isep.dei.domain.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Graphical User Interface (GUI) controller for the Simulator feature.
 * <p>
 * This class manages the interaction between the user and the simulation system,
 * allowing the user to select a scenario, choose routes to simulate, specify simulation duration,
 * start the simulation month by month, and view feedback including budget and date updates.
 * </p>
 * <p>
 * It connects UI components defined in the FXML file with the underlying {@link SimulatorController} and the current {@link Player}.
 * </p>
 */
public class SimulatorGUI {

    @FXML
    private ComboBox<String> scenarioComboBox;

    @FXML
    private ListView<String> routeListView;

    @FXML
    private TextField yearsField;

    @FXML
    private Button simulateButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Label currentDateLabel;

    @FXML
    private Label currentBudgetLabel;

    private final SimulatorController controller = new SimulatorController();
    private Player player;
    private int totalMonths = 0;
    private int currentMonth = 0;
    private boolean simulationStarted = false;

    /**
     * Sets the player who is performing the simulation.
     * Updates the controller and UI with the player's budget and available scenarios.
     *
     * @param player the Player instance representing the current user
     */
    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);

        List<String> scenarios = controller.getAvailableScenarioDescriptions();
        scenarioComboBox.setItems(FXCollections.observableArrayList(scenarios));

        updateBudgetAndDate();
    }

    /**
     * Initializes the GUI components and their event handlers.
     * <p>
     * - Allows multiple route selections.
     * - Loads routes when a scenario is selected.
     * - Starts and progresses simulation on simulate button press.
     * - Ends simulation on exit button press.
     * </p>
     * This method is automatically called by JavaFX after the FXML fields are injected.
     */
    @FXML
    public void initialize() {
        routeListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        scenarioComboBox.setOnAction(e -> {
            int selectedIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (controller.setScenario(selectedIndex)) {
                List<String> routes = controller.getAvailableRouteDescriptions();
                routeListView.setItems(FXCollections.observableArrayList(routes));
                feedbackLabel.setText("Scenario loaded successfully.");
            } else {
                routeListView.getItems().clear();
                feedbackLabel.setText("Invalid scenario selection.");
            }

            simulationStarted = false;
            currentMonth = 0;
            totalMonths = 0;
        });

        simulateButton.setOnAction(e -> {
            try {
                if (!simulationStarted) {
                    List<Integer> selectedIndexes = new ArrayList<>(routeListView.getSelectionModel().getSelectedIndices());
                    if (selectedIndexes.isEmpty()) {
                        feedbackLabel.setText("Select at least one route to simulate.");
                        return;
                    }

                    controller.selectRoutesByIndexes(selectedIndexes);

                    totalMonths = Integer.parseInt(yearsField.getText().trim()) * 12;
                    if (totalMonths <= 0) {
                        feedbackLabel.setText("Enter a positive number of years.");
                        return;
                    }

                    controller.startSimulation();
                    simulationStarted = true;
                    currentMonth = 0;

                    feedbackLabel.setText("Simulation started.");
                }

                if (currentMonth < totalMonths) {
                    controller.simulateMonths(1);
                    currentMonth++;
                    updateBudgetAndDate();

                    String monthlySummary = controller.getCurrentMonthSummary();
                    feedbackLabel.setText("Month " + currentMonth + " simulated.\n" + monthlySummary);

                    if (currentMonth == totalMonths) {
                        String finalSummary = controller.getSimulationSummary();
                        feedbackLabel.setText("Simulation finished.\n" + finalSummary);
                        simulationStarted = false;
                    }
                } else {
                    feedbackLabel.setText("Simulation already completed.");
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Invalid number of years.");
            } catch (Exception ex) {
                feedbackLabel.setText("Error during simulation: " + ex.getMessage());
            }
        });

        exitButton.setOnAction(e -> {
            if (!simulationStarted) {
                feedbackLabel.setText("Simulation not started yet.");
                return;
            }
            String finalSummary = controller.getSimulationSummary();
            feedbackLabel.setText("Simulation ended by user.\n" + finalSummary);
            simulationStarted = false;
        });
    }

    /**
     * Updates the UI labels for current budget and current simulation date.
     * Called after each simulation step or when player data changes.
     */
    private void updateBudgetAndDate() {
        if (player != null) {
            currentBudgetLabel.setText("Budget: " + player.getBudget());
        }
        currentDateLabel.setText("Current Date: " + controller.getCurrentSimulationDate());
    }
}
