package pt.ipp.isep.dei.domain;
import java.io.Serializable;
/**
 * Represents a player in the railway system.
 * A player has a username, a password, and a budget to use within the game.
 */
public class Player implements Serializable{

    private final String username;
    private final String password;
    private int budget;

    /**
     * Constructs a new {@code Player} with the specified username, password, and budget.
     *
     * @param username the player's username
     * @param password the player's password
     * @param budget the player's budget
     */
    public Player(String username, String password, int budget) {
        this.username = username;
        this.password = password;
        this.budget = budget;
    }

    /**
     * Returns the player's username.
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the player's password.
     *
     * @return the password of the player
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the current available budget of the player.
     *
     * @return the player's budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Checks whether the player can afford an item with the specified cost.
     *
     * @param cost the cost to check against the player's budget
     * @return {@code true} if the player has enough budget, {@code false} otherwise
     */
    public boolean canAfford(int cost) {
        return budget >= cost;
    }

    /**
     * Deducts a specified amount from the player's budget if affordable.
     * If the amount is greater than the current budget, no deduction is performed.
     *
     * @param amount the amount to deduct from the budget
     */
    public void deductBudget(int amount) {
        if (amount <= budget) {
            budget -= amount;
        }
    }

    /**
     * Adds a specified amount to the player's budget.
     *
     * @param amount the amount to addScenario to the budget
     */
    public void addBudget(int amount) {
        budget = budget + amount;
    }
}
