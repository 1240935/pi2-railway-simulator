package pt.ipp.isep.dei.ui.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pt.ipp.isep.dei.controller.SimulatorController;
import pt.ipp.isep.dei.domain.Player;

import java.util.ArrayList;
import java.util.List;

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

    public void setPlayer(Player player) {
        this.player = player;
        controller.setPlayer(player);

        List<String> scenarios = controller.getAvailableScenarioDescriptions();
        scenarioComboBox.setItems(FXCollections.observableArrayList(scenarios));

        updateBudgetAndDate();
    }

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

    private void updateBudgetAndDate() {
        if (player != null) {
            currentBudgetLabel.setText("Budget: " + player.getBudget());
        }
        currentDateLabel.setText("Current Date: " + controller.getCurrentSimulationDate());
    }
}
