package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.Editor;

/**
 * Represents the user interface for an {@link Editor}.
 * Displays a welcome message upon login.
 */
public class EditorUI {
    private final Editor editor;

    /**
     * Constructs a new {@code EditorUI} with the given editor.
     *
     * @param editor the editor to associate with this UI
     */
    public EditorUI(Editor editor) {
        this.editor = editor;
    }

    /**
     * Starts the editor interface, displaying a welcome message.
     */
    public void start() {
        System.out.println("Welcome " + editor.getUsername());
    }
}
