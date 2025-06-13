package pt.ipp.isep.dei.ui.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.controller.AssignRouteController;
import pt.ipp.isep.dei.domain.Player;

import java.util.ArrayList;
import java.util.List;

public class AssignRouteGUI {

    private final AssignRouteController controller = new AssignRouteController();
    private Player player;

    @FXML
    private ComboBox<String> scenarioComboBox;

    @FXML
    private ComboBox<String> stationComboBox;

    @FXML
    private ComboBox<String> cargoComboBox;

    @FXML
    private ComboBox<String> cargoModeComboBox;

    @FXML
    private ComboBox<String> locomotiveComboBox;

    @FXML
    private Button addPointButton, createRouteButton;

    @FXML
    private Label feedbackLabel;

    @FXML
    private VBox routePreviewBox;

    private int selectedScenarioIndex = -1;
    private final List<Integer> usedStationIndices = new ArrayList<>();
    private final List<String> routePreview = new ArrayList<>();

    public void setPlayer(Player player) {
        this.player = player;
        initializeUI();
    }

    private void initializeUI() {
        scenarioComboBox.setItems(FXCollections.observableArrayList(controller.getAvailableScenarioNames()));

        scenarioComboBox.setOnAction(e -> {
            selectedScenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            updateStations();
            updateLocomotives();
            feedbackLabel.setText("");
            routePreview.clear();
            routePreviewBox.getChildren().clear();
            usedStationIndices.clear();
        });

        addPointButton.setOnAction(e -> addPointOfRoute());
        createRouteButton.setOnAction(e -> createFinalRoute());

        cargoModeComboBox.setItems(FXCollections.observableArrayList(controller.getCargoModeNames()));
    }

    private void updateStations() {
        if (selectedScenarioIndex >= 0) {
            List<String> stationNames = controller.getAvailableStationNames(selectedScenarioIndex);
            List<String> filteredStations = new ArrayList<>();
            for (int i = 0; i < stationNames.size(); i++) {
                if (!usedStationIndices.contains(i)) {
                    filteredStations.add(stationNames.get(i));
                }
            }
            stationComboBox.setItems(FXCollections.observableArrayList(filteredStations));
        }
    }

    private void updateCargoes(int stationIndex) {
        if (selectedScenarioIndex >= 0) {
            List<String> cargoNames = controller.getAvailableCargoNames(selectedScenarioIndex, stationIndex + 1);
            cargoComboBox.setItems(FXCollections.observableArrayList(cargoNames));
        }
    }

    private void updateLocomotives() {
        if (selectedScenarioIndex >= 0) {
            List<String> locomotiveNames = controller.getAvailableLocomotiveNames(selectedScenarioIndex);
            locomotiveComboBox.setItems(FXCollections.observableArrayList(locomotiveNames));
        }
    }

    private void addPointOfRoute() {
        int stationIndex = stationComboBox.getSelectionModel().getSelectedIndex();
        int cargoModeIndex = cargoModeComboBox.getSelectionModel().getSelectedIndex();

        if (stationIndex < 0 || cargoModeIndex < 0) {
            feedbackLabel.setText("Select a station and cargo mode.");
            return;
        }

        int realStationIndex = calculateRealStationIndex(stationIndex);
        usedStationIndices.add(realStationIndex);

        List<Integer> cargoIndices = new ArrayList<>();
        int selectedCargoIndex = cargoComboBox.getSelectionModel().getSelectedIndex();
        if (selectedCargoIndex >= 0) {
            cargoIndices.add(selectedCargoIndex + 1); // 1-based
        }

        controller.createPointOfRoute(
                controller.getStationByIndex(selectedScenarioIndex, realStationIndex + 1),
                controller.getSelectedCargoList(
                        cargoIndices,
                        controller.getStationByIndex(selectedScenarioIndex, realStationIndex + 1).getSupplies()
                ),
                controller.getCargoModeByIndex(cargoModeIndex)
        );

        String stationName = stationComboBox.getSelectionModel().getSelectedItem();
        routePreview.add("→ " + stationName);
        refreshRoutePreview();
        updateStations(); // to refresh unused stations
    }

    private int calculateRealStationIndex(int comboIndex) {
        // Map visible index to real station index in original list
        List<String> allStations = controller.getAvailableStationNames(selectedScenarioIndex);
        int count = -1;
        for (int i = 0; i < allStations.size(); i++) {
            if (!usedStationIndices.contains(i)) {
                count++;
                if (count == comboIndex) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void refreshRoutePreview() {
        routePreviewBox.getChildren().clear();
        for (String routeStep : routePreview) {
            routePreviewBox.getChildren().add(new Label(routeStep));
        }
    }

    private void createFinalRoute() {
        int locoIndex = locomotiveComboBox.getSelectionModel().getSelectedIndex();

        if (locoIndex < 0) {
            feedbackLabel.setText("Please select a locomotive.");
            return;
        }

        try {
            controller.createRoute(
                    controller.getScenarioByIndex(selectedScenarioIndex),
                    controller.getLocomotiveByIndex(selectedScenarioIndex, locoIndex),
                    controller.getPointsOfRoute()
            );
            feedbackLabel.setStyle("-fx-text-fill: green;");
            feedbackLabel.setText("Route assigned successfully!");
        } catch (Exception ex) {
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("Error: " + ex.getMessage());
        }
    }
}
