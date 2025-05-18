package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StationCenterTest {

    @Test
    public void testConstructorAndGetPosition() {
        Position pos = new Position(10, 20);
        StationCenter center = new StationCenter(pos);

        assertEquals(pos, center.getPosition());
    }

    @Test
    public void testSetPosition() {
        Position pos1 = new Position(10, 20);
        Position pos2 = new Position(30, 40);
        StationCenter center = new StationCenter(pos1);

        center.setPosition(pos2);

        assertEquals(pos2, center.getPosition());
    }
}
