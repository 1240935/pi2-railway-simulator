package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.Player;

/**
 * Represents the user interface for a {@link Player}.
 * Displays basic player information such as username and budget.
 */
public class PlayerUI {
    private final Player player;

    /**
     * Constructs a new {@code PlayerUI} with the given player.
     *
     * @param player the player to associate with this UI
     */
    public PlayerUI(Player player) {
        this.player = player;
    }

    /**
     * Starts the player interface, displaying a welcome message and the player's budget.
     */
    public void start() {
        System.out.println("Welcome " + player.getUsername());
        System.out.println("Your budget is: " + player.getBudget());
    }
}
