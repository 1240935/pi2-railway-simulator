package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.Player;

public class PlayerUI {
    private final Player player;

    public PlayerUI(Player player) {
        this.player = player;
    }

    public void start() {
        System.out.println("Welcome " + player.getUsername());
        System.out.println("Your budget is: " + player.getBudget());
    }
}
