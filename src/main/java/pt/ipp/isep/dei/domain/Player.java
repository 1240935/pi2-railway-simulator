package pt.ipp.isep.dei.domain;

public class Player {
    private final String username;
    private final String password;
    private final double budget;

    public Player(String username, String password, double budget) {
        this.username = username;
        this.password = password;
        this.budget = budget;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBudget() {
        return budget;
    }
}
