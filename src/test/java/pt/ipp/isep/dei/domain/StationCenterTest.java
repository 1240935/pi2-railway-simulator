package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StationCenterTest {

    // Testa se o construtor cria corretamente um StationCenter e o método getPosition retorna a posição correta
    @Test
    public void testConstructorAndGetPosition() {
        Position pos = new Position(10, 20);
        StationCenter center = new StationCenter(pos);

        assertEquals(pos, center.getPosition());
    }

    // Testa se o método setPosition altera a posição corretamente
    @Test
    public void testSetPosition() {
        Position pos1 = new Position(10, 20);
        Position pos2 = new Position(30, 40);
        StationCenter center = new StationCenter(pos1);

        center.setPosition(pos2);

        assertEquals(pos2, center.getPosition());
    }

    // Testa se o construtor lança IllegalArgumentException quando a posição é null
    @Test
    public void testConstructorThrowsExceptionOnNullPosition() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StationCenter(null);
        });
        assertEquals("Position cannot be null", exception.getMessage());
    }

    // Testa se o método setPosition lança IllegalArgumentException quando tenta definir a posição como null
    @Test
    public void testSetPositionThrowsExceptionOnNull() {
        Position pos = new Position(10, 20);
        StationCenter center = new StationCenter(pos);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            center.setPosition(null);
        });
        assertEquals("Position cannot be null", exception.getMessage());
    }
}
