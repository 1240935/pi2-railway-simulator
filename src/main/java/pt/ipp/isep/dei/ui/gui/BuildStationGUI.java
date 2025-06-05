package pt.ipp.isep.dei.ui.gui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.controller.BuildStationController;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.StationType;

public class BuildStationGUI {

    private final BuildStationController controller = new BuildStationController();

    public void start(Player player) {
        controller.setPlayer(player);

        Stage stage = new Stage();
        stage.setTitle("Build Station");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        // Scenario ComboBox
        Label scenarioLabel = new Label("Select Scenario:");
        ComboBox<String> scenarioComboBox = new ComboBox<>(FXCollections.observableArrayList(controller.getAvailableScenarioDescriptions()));
        grid.add(scenarioLabel, 0, 0);
        grid.add(scenarioComboBox, 1, 0);

        // Coordinates
        Label xLabel = new Label("X Coordinate:");
        TextField xField = new TextField();
        grid.add(xLabel, 0, 1);
        grid.add(xField, 1, 1);

        Label yLabel = new Label("Y Coordinate:");
        TextField yField = new TextField();
        grid.add(yLabel, 0, 2);
        grid.add(yField, 1, 2);

        // Station Type
        Label typeLabel = new Label("Station Type:");
        ComboBox<String> typeComboBox = new ComboBox<>(FXCollections.observableArrayList(controller.getAvailableStationTypes()));
        grid.add(typeLabel, 0, 3);
        grid.add(typeComboBox, 1, 3);

        // Direction (only if needed)
        Label directionLabel = new Label("Compass Direction (NE/SE/NW/SW):");
        TextField directionField = new TextField();
        directionLabel.setVisible(false);
        directionField.setVisible(false);
        grid.add(directionLabel, 0, 4);
        grid.add(directionField, 1, 4);

        // Name proposal
        Label nameLabel = new Label("Station Name:");
        TextField nameField = new TextField();
        grid.add(nameLabel, 0, 5);
        grid.add(nameField, 1, 5);

        // Confirm button
        Button buildButton = new Button("Build Station");
        grid.add(buildButton, 1, 6);

        // Events
        typeComboBox.setOnAction(e -> {
            int typeIndex = typeComboBox.getSelectionModel().getSelectedIndex();
            if (controller.isSelectedTypeStation(typeIndex)) {
                directionLabel.setVisible(true);
                directionField.setVisible(true);
            } else {
                directionLabel.setVisible(false);
                directionField.setVisible(false);
            }
        });

        buildButton.setOnAction(e -> {
            try {
                int scenarioIndex = scenarioComboBox.getSelectionModel().getSelectedIndex();
                int typeIndex = typeComboBox.getSelectionModel().getSelectedIndex();
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());

                String name = nameField.getText();
                if (name.isEmpty()) {
                    name = controller.getRecommendedName(x, y, scenarioIndex, typeIndex);
                }

                String direction = directionField.getText();

                controller.createStation(
                        controller.createPosition(x, y),
                        name,
                        controller.getCenter(direction, x, y, typeIndex),
                        controller.getStationTypeByIndex(typeIndex),
                        controller.getDemandedCargoes(x, y, scenarioIndex, typeIndex),
                        controller.getSuppliedCargoes(x, y, scenarioIndex, typeIndex),
                        player,
                        scenarioIndex,
                        typeIndex,
                        controller.getMap(scenarioIndex),
                        controller.getScenarioByIndex(scenarioIndex)
                );

                Alert success = new Alert(Alert.AlertType.INFORMATION, "Station built successfully!");
                success.show();
                stage.close();

            } catch (Exception ex) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage());
                error.show();
            }
        });

        stage.setScene(new Scene(grid, 500, 400));
        stage.show();
    }
}
