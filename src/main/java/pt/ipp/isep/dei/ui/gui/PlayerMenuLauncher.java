package pt.ipp.isep.dei.ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.domain.Player;

/**
 * JavaFX application launcher for the Player Menu screen.
 * This class is responsible for initializing the JavaFX runtime,
 * loading the Player Menu FXML layout, and injecting the current player.
 */
public class PlayerMenuLauncher extends Application {

    /**
     * The player currently logged in. This is set before launching the UI.
     */
    private static Player player;

    /**
     * Sets the player to be passed into the PlayerMenuGUI controller.
     *
     * @param p the Player instance
     */
    public static void setPlayer(Player p) {
        player = p;
    }

    /**
     * Starts the JavaFX application. Loads the FXML file for the Player Menu,
     * injects the Player into the controller, and shows the window.
     *
     * @param primaryStage the main stage for this application
     * @throws Exception if the FXML file cannot be loaded
     */
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

    /**
     * Static method to launch the Player Menu GUI.
     * Must be called with a valid Player instance before JavaFX starts.
     *
     * @param player the current player using the menu
     */
    public static void launchUI(Player player) {
        setPlayer(player);
        Application.launch(PlayerMenuLauncher.class);
    }
}
