package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditorTest {

    // Testa se o método getUsername retorna o nome de utilizador corretamente
    @Test
    void getUsername_ShouldReturnCorrectUsername() {
        Editor editor = new Editor("user123", "pass456");
        assertEquals("user123", editor.getUsername());
    }

    // Testa se o método getPassword retorna a password corretamente
    @Test
    void getPassword_ShouldReturnCorrectPassword() {
        Editor editor = new Editor("user123", "pass456");
        assertEquals("pass456", editor.getPassword());
    }

    // Testa se os campos username e password não são nulos após a criação
    @Test
    void editorFields_ShouldNotBeNull() {
        Editor editor = new Editor("editorName", "editorPass");
        assertNotNull(editor.getUsername());
        assertNotNull(editor.getPassword());
    }

    // Testa se é lançada exceção quando o username é uma string vazia
    @Test
    void constructor_ShouldThrowException_WhenUsernameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Editor("", "validPass123"));
    }

    // Testa se é lançada exceção quando a password é uma string vazia
    @Test
    void constructor_ShouldThrowException_WhenPasswordIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Editor("validUser", ""));
    }

    // Testa se é lançada exceção quando o username contém apenas espaços em branco
    @Test
    void constructor_ShouldThrowException_WhenUsernameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Editor("   ", "validPass123"));
    }

    // Testa se é lançada exceção quando a password contém apenas espaços em branco
    @Test
    void constructor_ShouldThrowException_WhenPasswordIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Editor("validUser", "   "));
    }

    // Testa se é lançada exceção quando o username é null
    @Test
    void constructor_ShouldThrowException_WhenUsernameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Editor(null, "validPass123"));
    }

    // Testa se é lançada exceção quando a password é null
    @Test
    void constructor_ShouldThrowException_WhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Editor("validUser", null));
    }
}