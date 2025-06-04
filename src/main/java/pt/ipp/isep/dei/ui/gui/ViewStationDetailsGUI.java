package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import pt.ipp.isep.dei.controller.ViewStationController;

import java.util.List;

public class ViewStationDetailsGUI {

    @FXML
    private ComboBox<String> scenarioComboBox;

    @FXML
    private ComboBox<String> stationComboBox;

    @FXML
    private TextArea detailsTextArea;

    private final ViewStationController controller = new ViewStationController();

    @FXML
    public void start() {
        loadScenarios();

        scenarioComboBox.setOnAction(event -> {
            int selectedIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                controller.setScenario(selectedIndex);
                loadStations();
            }
        });
    }

    private void loadScenarios() {
        List<String> scenarios = controller.getAvailableScenarioDescriptions();
        if (scenarios.isEmpty()) {
            showAlert("No scenarios available.");
            return;
        }

        scenarioComboBox.getItems().setAll(scenarios);
        scenarioComboBox.getSelectionModel().selectFirst();
        controller.setScenario(0);
        loadStations();
    }

    private void loadStations() {
        List<String> stations = controller.getAvailableStationNames();
        stationComboBox.getItems().setAll(stations);
        if (!stations.isEmpty()) {
            stationComboBox.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void handleShowDetails() {
        int stationIndex = stationComboBox.getSelectionModel().getSelectedIndex();
        if (stationIndex < 0) {
            showAlert("Please select a valid station.");
            return;
        }

        String buildingDetails = controller.getBuildingDetailsString(stationIndex);
        String cargoInfo = controller.getCargoInfo(stationIndex);

        detailsTextArea.setText("Building Details:\n" + buildingDetails + "\n\nCargo Information:\n" + cargoInfo);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
