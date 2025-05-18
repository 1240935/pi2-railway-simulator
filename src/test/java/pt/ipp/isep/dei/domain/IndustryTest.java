package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IndustryTest {

    private Industry industry;
    private IndustryType industryType;
    private Position position;

    @BeforeEach
    public void setUp() {
        // Exemplo de IndustryType
        industryType = IndustryType.ALUMINUM_MILL;

        // Exemplo de Position (x=10, y=20)
        position = new Position(10, 20);

        // Criar a Industry com os valores acima
        industry = new Industry(industryType, position);
    }

    @Test
    public void testGetType() {
        assertEquals(industryType, industry.getType(), "O tipo da indústria deve ser ALUMINUM_MILL");
    }

    @Test
    public void testSetType() {
        IndustryType newType = IndustryType.BAKERY;
        industry.setType(newType);
        assertEquals(newType, industry.getType(), "O tipo da indústria deve ser atualizado para BAKERY");
    }

    @Test
    public void testGetPosition() {
        assertEquals(position, industry.getPosition(), "A posição da indústria deve ser a inicializada");
    }

    @Test
    public void testSetPosition() {
        Position newPosition = new Position(30, 40);
        industry.setPosition(newPosition);
        assertEquals(newPosition, industry.getPosition(), "A posição da indústria deve ser atualizada");
    }
}
