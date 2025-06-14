package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import pt.ipp.isep.dei.controller.ViewStationDetailsController;

import java.util.List;

public class ViewStationDetailsGUI {

    @FXML
    private ComboBox<String> scenarioComboBox;

    @FXML
    private ComboBox<String> stationComboBox;

    @FXML
    private TextArea detailsTextArea;

    private final ViewStationDetailsController controller = new ViewStationDetailsController();

    @FXML
    public void initialize() {
        loadScenarios();

        scenarioComboBox.setOnAction(event -> {
            int selectedScenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (selectedScenarioIndex >= 0) {
                loadStations(selectedScenarioIndex);
            } else {
                stationComboBox.getItems().clear();
                detailsTextArea.clear();
            }
        });
    }

    private void loadScenarios() {
        List<String> scenarios = controller.getAvailableScenarioNames();
        if (scenarios.isEmpty()) {
            showAlert("No scenarios available.");
            scenarioComboBox.setDisable(true);
            stationComboBox.setDisable(true);
            return;
        }

        scenarioComboBox.getItems().setAll(scenarios);
        scenarioComboBox.getSelectionModel().selectFirst();
        loadStations(0);  // Load stations for first scenario by default
    }

    private void loadStations(int scenarioIndex) {
        List<String> stations = controller.getAvailableStationNames(scenarioIndex);
        if (stations.isEmpty()) {
            stationComboBox.getItems().clear();
            detailsTextArea.clear();
            showAlert("No stations available for the selected scenario.");
            return;
        }

        stationComboBox.getItems().setAll(stations);
        stationComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleShowDetails() {
        int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
        int stationIndex = stationComboBox.getSelectionModel().getSelectedIndex();

        if (scenarioIndex < 0 || stationIndex < 0) {
            showAlert("Please select both a scenario and a station.");
            return;
        }

        String buildingDetails = controller.getBuildingDetailsString(stationIndex, scenarioIndex);
        String cargoInfo = controller.getCargoInfo(stationIndex, scenarioIndex);

        String displayedCargoInfo = (cargoInfo == null || cargoInfo.trim().isEmpty())
                ? "No available cargo"
                : cargoInfo;

        detailsTextArea.setText("Building Details:\n" + buildingDetails +
                "\n\nCargo Information:\n" + displayedCargoInfo);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
