package pt.ipp.isep.dei.ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.domain.Player;

public class PlayerMenuLauncher extends Application {

    private static Player player;

    public static void setPlayer(Player p) {
        player = p;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PlayerMenu.fxml"));
        Scene scene = new Scene(loader.load());

        PlayerMenuGUI controller = loader.getController();
        controller.setPlayer(player);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Player Menu");
        primaryStage.show();
    }

    public static void launchUI(Player player) {
        setPlayer(player);
        Application.launch(PlayerMenuLauncher.class);
    }
}
