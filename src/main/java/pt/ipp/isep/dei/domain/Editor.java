package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Represents an editor in the railway system.
 * An editor is responsible for managing content such as maps, industries, cities, and scenarios.
 */
public class Editor implements Serializable {

    private final String username;
    private final String password;

    /**
     * Constructs a new {@code Editor} with the specified username and password.
     *
     * @param username the editor's username
     * @param password the editor's password
     */
    public Editor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the editor's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the editor's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
