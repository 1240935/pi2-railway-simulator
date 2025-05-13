package pt.ipp.isep.dei.domain;

public class Player {
    private String username;
    private String password;
    private int budget;

    private final int DEFAULT_BUDGET = 0;

    public Player(String username, String password) {
        this.password = password;
        this.username = username;
        this.budget = DEFAULT_BUDGET;
    }

    public Player(String username, String password, int budget) {
        this.password = password;
        this.username = username;
        this.budget = budget;
    }

    public int getBudget() {
        return budget;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}