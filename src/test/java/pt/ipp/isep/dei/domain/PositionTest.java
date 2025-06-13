package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PositionTest {

    //Testa os métodos getters e setters das coordenadas x e y.

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

    //Testa se a distância euclidiana entre dois pontos iguais é zero.
    @Test
    public void testEuclideanDistanceSamePoint() {
        Position pos1 = new Position(3, 4);
        Position pos2 = new Position(3, 4);

        assertEquals(0.0, pos1.euclideanDistance(pos2), 1e-9);
    }

    //Testa a distância euclidiana entre dois pontos diferentes (teorema de Pitágoras).
    @Test
    public void testEuclideanDistanceDifferentPoints() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(3, 4);

        assertEquals(5.0, pos1.euclideanDistance(pos2), 1e-9);
    }

    //Verifica se a distância entre dois pontos é simétrica (A→B = B→A).
    @Test
    public void testEuclideanDistanceSymmetry() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(4, 6);

        double dist1 = pos1.euclideanDistance(pos2);
        double dist2 = pos2.euclideanDistance(pos1);

        assertEquals(dist1, dist2, 1e-9);
    }

    //Testa a distância entre dois pontos muito distantes.
    @Test
    public void testEuclideanDistance_LongDistance() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(3000, 4000);

        assertEquals(5000.0, pos1.euclideanDistance(pos2), 1e-9, "Deve calcular distâncias grandes corretamente");
    }

    // Verifica se uma posição exatamente no limite superior do mapa é considerada válida.
    @Test
    public void testCheckPosition_AtEdgeWithinBounds() {
        Position pos = new Position(9, 9);
        assertTrue(Position.isValid(pos, 10));
    }

    // Verifica se uma posição fora dos limites superiores do mapa é inválida.
    @Test
    public void testCheckPosition_OutsideBounds() {
        Position pos = new Position(10, 0);
        assertFalse(Position.isValid(pos, 10));
    }

    // Verifica se uma posição com coordenada negativa é considerada inválida.
    @Test
    public void testCheckPosition_NegativeCoordinates() {
        Position pos = new Position(-1, 5);
        assertFalse(Position.isValid(pos, 10));
    }

    //Testa o método de fábrica para criação de uma posição.
    @Test
    public void testCreatePositionFactoryMethod() {
        Position pos = Position.createPosition(2, 3);
        assertEquals(2, pos.getxCoordinate());
        assertEquals(3, pos.getyCoordinate());
    }

}
