package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityBlockTest {

    // Testa se o método getPosition() retorna corretamente a posição atribuída no construtor
    @Test
    void getPosition_ShouldReturnCorrectPosition() {
        Position pos = new Position(5, 10);
        CityBlock block = new CityBlock(pos);

        assertEquals(pos, block.getPosition());
    }

    // Testa se o método createPositions() cria corretamente uma lista de posições a partir de arrays de coordenadas
    @Test
    void createPositions_ShouldReturnCorrectListOfPositions() {
        int[] x = {1, 2, 3};
        int[] y = {4, 5, 6};

        CityBlock block = new CityBlock(); // usa o construtor vazio

        List<Position> result = block.createPositions(x, y);

        assertEquals(3, result.size());
        assertEquals(new Position(1, 4), result.get(0));
        assertEquals(new Position(2, 5), result.get(1));
        assertEquals(new Position(3, 6), result.get(2));
    }

    // Testa se o método createPositions() retorna uma lista vazia quando os arrays estão vazios
    @Test
    void createPositions_ShouldReturnEmptyList_WhenArraysAreEmpty() {
        int[] x = {};
        int[] y = {};

        CityBlock block = new CityBlock();

        List<Position> result = block.createPositions(x, y);

        assertTrue(result.isEmpty());
    }

    // Testa se createPositions() lança exceção quando os arrays têm tamanhos diferentes
    @Test
    void createPositions_ShouldThrowException_WhenArraySizesDiffer() {
        int[] x = {1, 2};
        int[] y = {4};

        CityBlock block = new CityBlock();

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> block.createPositions(x, y));
    }
}
