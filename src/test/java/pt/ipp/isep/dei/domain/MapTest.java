package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapTest {

    private Map map;

    @BeforeEach
    public void setUp() {
        map = new Map(10, "TestMap", 30);
    }

    @Test
    public void testGetName() {
        assertEquals("TestMap", map.getName());
    }

    @Test
    public void testGetSize() {
        assertEquals(10, map.getSize());
    }

    @Test
    public void testSetName() {
        map.setName("NewName");
        assertEquals("NewName", map.getName());
    }

    @Test
    public void testSetSize() {
        map.setSize(20);
        assertEquals(20, map.getSize());
    }

    @Test
    public void testCheckCoordinates_ValidPosition() {
        Position validPos = new Position(5, 5);
        assertTrue(map.checkCoordinates(validPos));
    }

    @Test
    public void testCheckCoordinates_InvalidPosition_NegativeX() {
        Position invalidPos = new Position(-1, 5);
        assertFalse(map.checkCoordinates(invalidPos));
    }

    @Test
    public void testCheckCoordinates_InvalidPosition_NegativeY() {
        Position invalidPos = new Position(5, -1);
        assertFalse(map.checkCoordinates(invalidPos));
    }

    @Test
    public void testCheckCoordinates_InvalidPosition_OutsideSizeX() {
        Position invalidPos = new Position(10, 5);
        assertFalse(map.checkCoordinates(invalidPos));
    }

    @Test
    public void testCheckCoordinates_InvalidPosition_OutsideSizeY() {
        Position invalidPos = new Position(5, 10);
        assertFalse(map.checkCoordinates(invalidPos));
    }

    @Test
    public void testToString() {
        String expected = "Map{name='TestMap', size=10}";
        assertEquals(expected, map.toString());
    }
}
