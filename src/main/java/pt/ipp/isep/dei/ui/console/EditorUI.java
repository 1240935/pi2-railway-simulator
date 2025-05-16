package pt.ipp.isep.dei.ui.console;

import pt.ipp.isep.dei.domain.Editor;

public class EditorUI {
    private final Editor editor;

    public EditorUI(Editor editor) {
        this.editor = editor;
    }

    public void start() {
        System.out.println("Welcome " + editor.getUsername());
    }
}
