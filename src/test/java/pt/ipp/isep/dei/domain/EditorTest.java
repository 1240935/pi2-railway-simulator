package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditorTest {

    @Test
    void getUsername_ShouldReturnCorrectUsername() {
        Editor editor = new Editor("user123", "pass456");
        assertEquals("user123", editor.getUsername());
    }

    @Test
    void getPassword_ShouldReturnCorrectPassword() {
        Editor editor = new Editor("user123", "pass456");
        assertEquals("pass456", editor.getPassword());
    }
}
