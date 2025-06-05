package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.ui.console.*;

public class PlayerMenuGUI {

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    @FXML
    private void handleBuildStation() {
        new BuildStationGUI().start(player);
    }

    @FXML
    private void handleUpgradeStation() {
        new UpgradeStationGUI().start();
    }

    @FXML
    private void handleViewStationDetails() {
        new ViewStationDetailsGUI().start();
    }

    @FXML
    private void handleBuyLocomotive() {
        new BuyLocomotiveGUI().start();
    }

    @FXML
    private void handleAssignRoute() {
        new AssignRouteUI().start();
    }

    @FXML
    private void handleSimulator() {
        new SimulatorUI().start(player);
    }

    @FXML
    private void handleVerifyTrainRoute() {
        new VerifyTrainTravelUI().start();
    }

    @FXML
    private void handleVerifyTrainRouteTest() {
        new VerifyTrainTravelTestUI().start();
    }

    @FXML
    private void handleRailwayMaintenance() {
        new RailwayMaintenanceUI().start();
    }

    @FXML
    private void handleRailwayMaintenanceTest() {
        new RailwayMaintenanceTestUI().start();
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Logged out.");
        alert.showAndWait();
    }
}
