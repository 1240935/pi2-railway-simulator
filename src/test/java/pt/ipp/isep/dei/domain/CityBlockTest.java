package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityBlockTest {

    @Test
    void getPosition_ShouldReturnCorrectPosition() {
        Position pos = new Position(5, 10);
        CityBlock block = new CityBlock(pos);

        assertEquals(pos, block.getPosition());
    }
}
