package pt.ipp.isep.dei.domain;

/**
 * Represents a player in the railway system.
 * A player has a username, a password, and a budget to use within the game.
 */
public class Player {

    private final String username;
    private final String password;
    private final double budget;

    /**
     * Constructs a new {@code Player} with the specified username, password, and budget.
     *
     * @param username the player's username
     * @param password the player's password
     * @param budget the player's budget
     */
    public Player(String username, String password, double budget) {
        this.username = username;
        this.password = password;
        this.budget = budget;
    }

    /**
     * Returns the player's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the player's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the player's available budget.
     *
     * @return the budget
     */
    public double getBudget() {
        return budget;
    }
}
