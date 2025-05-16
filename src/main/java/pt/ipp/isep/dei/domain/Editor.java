package pt.ipp.isep.dei.domain;

public class Editor {
    private final String username;
    private final String password;

    public Editor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
