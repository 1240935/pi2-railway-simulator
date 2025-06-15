package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import pt.ipp.isep.dei.controller.ViewStationDetailsController;

import java.util.List;

/**
 * GUI controller class responsible for displaying details of stations
 * within selected scenarios in the railway simulation game.
 * <p>
 * Provides UI controls for scenario and station selection, and displays
 * related building details and cargo information for the selected station.
 * <p>
 * Interacts with the {@link ViewStationDetailsController} to retrieve data.
 */
public class ViewStationDetailsGUI {

    @FXML
    private ComboBox<String> scenarioComboBox;  // Dropdown to select a scenario

    @FXML
    private ComboBox<String> stationComboBox;   // Dropdown to select a station in the scenario

    @FXML
    private TextArea detailsTextArea;            // Text area to display station details and cargo info

    private final ViewStationDetailsController controller = new ViewStationDetailsController();

    /**
     * Initializes the GUI components.
     * Loads available scenarios into the scenario combo box,
     * sets up listeners to load stations when a scenario is selected,
     * and clears details if no scenario or station is selected.
     */
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

    /**
     * Loads all available scenario names into the scenario combo box.
     * Disables UI controls and shows an alert if no scenarios are available.
     * Automatically loads stations for the first scenario if present.
     */
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
        loadStations(0);  // Load stations for the first scenario by default
    }

    /**
     * Loads all stations available for a given scenario index into the station combo box.
     * Clears the details text area and shows an alert if no stations are available.
     *
     * @param scenarioIndex Index of the selected scenario
     */
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

    /**
     * Event handler triggered when the user requests to view details of the selected station.
     * Retrieves and displays building details and cargo information.
     * Shows an alert if either scenario or station is not selected.
     */
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

    /**
     * Utility method to show an information alert dialog with a specified message.
     *
     * @param message The message to display in the alert
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
