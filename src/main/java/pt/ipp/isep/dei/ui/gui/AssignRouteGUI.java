package pt.ipp.isep.dei.ui.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.controller.AssignRouteController;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.PointOfRouteDTO;
import pt.ipp.isep.dei.dto.RouteDTO;

import java.awt.*;
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
    }

    @FXML
    public void initialize() {
        scenarioComboBox.setItems(FXCollections.observableArrayList(controller.getAvailableScenarioNames()));

        scenarioComboBox.setOnAction(e -> {
            selectedScenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            usedStationIndices.clear();
            routePreview.clear();
            routePreviewBox.getChildren().clear();
            feedbackLabel.setText("");
            updateStations();
            updateLocomotives();
            cargoComboBox.getItems().clear();
        });

        stationComboBox.setOnAction(e -> {
            int stationIndexFiltered = stationComboBox.getSelectionModel().getSelectedIndex();
            if (stationIndexFiltered >= 0) {
                int realStationIndex = calculateRealStationIndex(stationIndexFiltered);
                updateCargoes(realStationIndex);
            } else {
                cargoComboBox.getItems().clear();
            }
        });

        addPointButton.setOnAction(e -> addPointOfRoute());
        createRouteButton.setOnAction(e -> createFinalRoute());

        cargoModeComboBox.setItems(FXCollections.observableArrayList(controller.getCargoModeNames()));
    }

    private void updateStations() {
        if (selectedScenarioIndex >= 0) {
            List<String> stationNames = controller.getAvailableStationNames(selectedScenarioIndex + 1);
            List<String> filteredStations = new ArrayList<>();
            for (int i = 0; i < stationNames.size(); i++) {
                if (!usedStationIndices.contains(i)) {
                    filteredStations.add(stationNames.get(i));
                }
            }
            stationComboBox.setItems(FXCollections.observableArrayList(filteredStations));
            stationComboBox.getSelectionModel().clearSelection();
            cargoComboBox.getItems().clear();
        }
    }

    private void updateCargoes(int realStationIndex) {
        if (selectedScenarioIndex >= 0) {
            List<String> cargoNames = controller.getAvailableCargoNames(selectedScenarioIndex + 1, realStationIndex + 1);
            cargoComboBox.setItems(FXCollections.observableArrayList(cargoNames));
            cargoComboBox.getSelectionModel().clearSelection();
        }
    }

    private void updateLocomotives() {
        if (selectedScenarioIndex >= 0) {
            List<String> locomotiveNames = controller.getAvailableLocomotiveNames(selectedScenarioIndex + 1);
            locomotiveComboBox.setItems(FXCollections.observableArrayList(locomotiveNames));
            locomotiveComboBox.getSelectionModel().clearSelection();
        }
    }

    private int calculateRealStationIndex(int filteredIndex) {
        int realIndex = -1;
        if (selectedScenarioIndex >= 0) {
            List<String> stationNames = controller.getAvailableStationNames(selectedScenarioIndex + 1);
            int count = -1;
            for (int i = 0; i < stationNames.size(); i++) {
                if (!usedStationIndices.contains(i)) {
                    count++;
                    if (count == filteredIndex) {
                        realIndex = i;
                        break;
                    }
                }
            }
        }
        return realIndex;
    }

    @FXML
    private void addPointOfRoute() {
        int stationIndexFiltered = stationComboBox.getSelectionModel().getSelectedIndex();
        int cargoIndexSelected = cargoComboBox.getSelectionModel().getSelectedIndex();
        int cargoModeIndex = cargoModeComboBox.getSelectionModel().getSelectedIndex();

        if (stationIndexFiltered < 0 || cargoModeIndex < 0) {
            feedbackLabel.setText("Select a station and cargo mode.");
            return;
        }

        int realStationIndex = calculateRealStationIndex(stationIndexFiltered);

        Station station = controller.getStationByIndex(selectedScenarioIndex + 1, realStationIndex + 1);

        List<Industry> industries = controller.getScenarioByIndex(selectedScenarioIndex + 1).getMap().getIndustries();
        List<City> cities = controller.getScenarioByIndex(selectedScenarioIndex + 1).getMap().getCities();
        List<Cargo> availableCargoList = station.getSuppliesOnly(industries, cities);

        List<Integer> selectedCargoIndices = new ArrayList<>();
        if (cargoIndexSelected >= 0) {
            selectedCargoIndices.add(cargoIndexSelected + 1);
        }

        List<Cargo> selectedCargoList = controller.getSelectedCargoList(selectedCargoIndices, availableCargoList);

        CargoMode cargoMode = controller.getCargoModeByIndex(cargoModeIndex + 1);

        PointOfRouteDTO pointOfRouteDTO = new PointOfRouteDTO();
        pointOfRouteDTO.setStation(controller.getStationByIndex(selectedScenarioIndex + 1, realStationIndex + 1));
        pointOfRouteDTO.setCargo(controller.getSelectedCargoList(selectedCargoIndices, availableCargoList));
        pointOfRouteDTO.setCargoMode(controller.getCargoModeByIndex(cargoModeIndex + 1));
        controller.createPointOfRoute(pointOfRouteDTO);

        usedStationIndices.add(realStationIndex);

        String stationName = stationComboBox.getSelectionModel().getSelectedItem();
        routePreview.add("→ " + stationName);
        refreshRoutePreview();

        updateStations();
        cargoComboBox.getItems().clear();
        feedbackLabel.setText("");
    }

    @FXML
    private void createFinalRoute() {
        int locoIndex = locomotiveComboBox.getSelectionModel().getSelectedIndex();

        if (locoIndex < 0) {
            feedbackLabel.setText("Please select a locomotive.");
            return;
        }

        try {
            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setLocomotive(controller.getLocomotiveByIndex(selectedScenarioIndex + 1, locoIndex + 1));
            routeDTO.setPointsOfRoute(controller.getPointsOfRoute());
            controller.createRoute(routeDTO, selectedScenarioIndex + 1);
            feedbackLabel.setStyle("-fx-text-fill: green;");
            feedbackLabel.setText("Route assigned successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            feedbackLabel.setStyle("-fx-text-fill: red;");
            feedbackLabel.setText("Error: " + ex.getMessage());
        }
    }


    private void refreshRoutePreview() {
        routePreviewBox.getChildren().clear();
        for (String point : routePreview) {
            Label label = new Label(point);
            routePreviewBox.getChildren().add(label);
        }
    }
}
