package pt.ipp.isep.dei.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import pt.ipp.isep.dei.domain.Player;

import java.io.IOException;

public class PlayerMenuGUI {

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    @FXML
    private void handleBuildStation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BuildStation.fxml"));
            Parent root = loader.load();
            BuildStationGUI controller = loader.getController();
            controller.setPlayer(player);

            Stage stage = new Stage();
            stage.setTitle("Build Station");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleUpgradeStation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UpgradeStation.fxml"));
            Parent root = loader.load();
            UpgradeStationGUI controller = loader.getController();
            controller.setPlayer(player);
            controller.initialize();

            Stage stage = new Stage();
            stage.setTitle("Upgrade Station");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleViewStationDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewStationDetails.fxml"));
            Parent root = loader.load();

            ViewStationDetailsGUI controller = loader.getController();
            controller.initialize();

            Stage stage = new Stage();
            stage.setTitle("View Station Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleBuyLocomotive() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BuyLocomotive.fxml"));
            Parent root = loader.load();

            BuyLocomotiveGUI controller = loader.getController();
            controller.setPlayer(player);
            controller.initialize();

            Stage stage = new Stage();
            stage.setTitle("Buy Locomotive");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAssignRoute() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AssignRoute.fxml"));
            Parent root = loader.load();

            AssignRouteGUI controller = loader.getController();
            controller.setPlayer(player);
            controller.initialize();

            Stage stage = new Stage();
            stage.setTitle("Assign Route");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSimulator() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Simulator.fxml"));
            Parent root = loader.load();

            SimulatorGUI controller = loader.getController();
            controller.setPlayer(player);
            controller.initialize();

            Stage stage = new Stage();
            stage.setTitle("Simulator");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Logged out.");
        alert.showAndWait();
    }
}
