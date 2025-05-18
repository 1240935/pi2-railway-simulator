package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void testGettersAndSetters() {
        Position pos = new Position(5, 10);
        assertEquals(5, pos.getxCoordinate());
        assertEquals(10, pos.getyCoordinate());

        pos.setxCoordinate(7);
        pos.setyCoordinate(12);

        assertEquals(7, pos.getxCoordinate());
        assertEquals(12, pos.getyCoordinate());
    }

    @Test
    public void testEuclideanDistanceSamePoint() {
        Position pos1 = new Position(3, 4);
        Position pos2 = new Position(3, 4);

        assertEquals(0.0, pos1.euclideanDistance(pos2), 1e-9);
    }

    @Test
    public void testEuclideanDistanceDifferentPoints() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(3, 4);

        assertEquals(5.0, pos1.euclideanDistance(pos2), 1e-9);
    }

    @Test
    public void testEuclideanDistanceSymmetry() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(4, 6);

        double dist1 = pos1.euclideanDistance(pos2);
        double dist2 = pos2.euclideanDistance(pos1);

        assertEquals(dist1, dist2, 1e-9);
    }
}
