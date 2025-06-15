package pt.ipp.isep.dei.ui.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import pt.ipp.isep.dei.controller.AssignRouteController;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.dto.PointOfRouteDTO;
import pt.ipp.isep.dei.dto.RouteDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX GUI controller class responsible for assigning routes in the application.
 *
 * <p>This class provides a graphical user interface allowing the player to:</p>
 * <ul>
 *     <li>Select a scenario from available scenarios.</li>
 *     <li>Select stations within the chosen scenario, filtering out already used stations.</li>
 *     <li>Select cargo and cargo modes associated with stations.</li>
 *     <li>Add points of route (station + cargo + cargo mode) to build a full route.</li>
 *     <li>Select a locomotive and create the final route.</li>
 *     <li>Preview the route as it is built, with feedback messages shown to the user.</li>
 * </ul>
 *
 * <p>Interacts with the {@link AssignRouteController} to retrieve domain data and perform operations.</p>
 */
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

    /**
     * Sets the current player interacting with this GUI.
     *
     * @param player The player to set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Initializes the GUI components and sets up event handlers.
     *
     * <p>Populates the scenario dropdown and configures logic for station, cargo, cargo mode,
     * and locomotive selection. Also initializes action listeners for adding points to the route
     * and creating the final route.</p>
     */
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

    /**
     * Updates the station ComboBox filtering out stations already used in the route.
     */
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

    /**
     * Updates the cargo ComboBox based on the selected station.
     *
     * @param realStationIndex The actual index of the station in the scenario.
     */
    private void updateCargoes(int realStationIndex) {
        if (selectedScenarioIndex >= 0) {
            List<String> cargoNames = controller.getAvailableCargoNames(selectedScenarioIndex + 1, realStationIndex + 1);
            cargoComboBox.setItems(FXCollections.observableArrayList(cargoNames));
            cargoComboBox.getSelectionModel().clearSelection();
        }
    }

    /**
     * Updates the locomotive ComboBox based on the selected scenario.
     */
    private void updateLocomotives() {
        if (selectedScenarioIndex >= 0) {
            List<String> locomotiveNames = controller.getAvailableLocomotiveNames(selectedScenarioIndex + 1);
            locomotiveComboBox.setItems(FXCollections.observableArrayList(locomotiveNames));
            locomotiveComboBox.getSelectionModel().clearSelection();
        }
    }

    /**
     * Calculates the actual index of a station given its filtered index in the ComboBox.
     *
     * @param filteredIndex The filtered index in the ComboBox (stations not yet used).
     * @return The actual station index in the full scenario list.
     */
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

    /**
     * Adds a new point of the route using the current selections in the GUI.
     *
     * <p>Validates if a station and cargo mode are selected, retrieves the domain objects,
     * creates a PointOfRouteDTO, updates the used stations list, and refreshes the GUI preview.</p>
     */
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
        pointOfRouteDTO.setStation(station);
        pointOfRouteDTO.setCargo(selectedCargoList);
        pointOfRouteDTO.setCargoMode(cargoMode);
        controller.createPointOfRoute(pointOfRouteDTO);

        usedStationIndices.add(realStationIndex);

        String stationName = stationComboBox.getSelectionModel().getSelectedItem();
        routePreview.add("→ " + stationName);
        refreshRoutePreview();

        updateStations();
        cargoComboBox.getItems().clear();
        feedbackLabel.setText("");
    }

    /**
     * Creates the final route using the selected locomotive and the added points of the route.
     *
     * <p>Validates locomotive selection and attempts to create the route through the controller.
     * Provides feedback on success or failure.</p>
     */
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

    /**
     * Refreshes the route preview area in the GUI, displaying the current points of the route.
     */
    private void refreshRoutePreview() {
        routePreviewBox.getChildren().clear();
        for (String point : routePreview) {
            Label label = new Label(point);
            routePreviewBox.getChildren().add(label);
        }
    }
}
