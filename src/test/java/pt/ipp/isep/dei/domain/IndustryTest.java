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

    // Testa se o getter devolve corretamente o tipo da indústria inicial
    @Test
    public void testGetType() {
        assertEquals(industryType, industry.getType(), "O tipo da indústria deve ser ALUMINUM_MILL");
    }

    // Testa se o setter atualiza corretamente o tipo da indústria
    @Test
    public void testSetType() {
        IndustryType newType = IndustryType.BAKERY;
        industry.setType(newType);
        assertEquals(newType, industry.getType(), "O tipo da indústria deve ser atualizado para BAKERY");
    }

    // Testa se o getter devolve a posição correta da indústria
    @Test
    public void testGetPosition() {
        assertEquals(position, industry.getPosition(), "A posição da indústria deve ser a inicializada");
    }

    // Testa se o setter atualiza corretamente a posição da indústria
    @Test
    public void testSetPosition() {
        Position newPosition = new Position(30, 40);
        industry.setPosition(newPosition);
        assertEquals(newPosition, industry.getPosition(), "A posição da indústria deve ser atualizada");
    }

    // Testa se uma posição válida dentro dos limites do mapa (10x10) é aceite
    @Test
    public void testCheckPosition_ValidInsideBounds() {
        Position pos = new Position(5, 5);
        assertTrue(industry.checkPosition(pos, 10), "A posição (5,5) deve estar dentro dos limites da grelha 10x10");
    }

    // Testa se coordenadas negativas são corretamente rejeitadas
    @Test
    public void testCheckPosition_NegativeCoordinates() {
        Position pos = new Position(-1, 5);
        assertFalse(industry.checkPosition(pos, 10), "Coordenadas negativas devem ser consideradas inválidas");
    }

    // Testa se uma posição fora do limite (ex: 10,10) é rejeitada
    @Test
    public void testCheckPosition_ExceedsBounds() {
        Position pos = new Position(10, 10); // Limite exclusivo
        assertFalse(industry.checkPosition(pos, 10), "Coordenadas no limite devem ser inválidas pois size é exclusivo");
    }

    // Testa se um tipo de indústria null é rejeitado
    @Test
    public void testCheckIndustryType_NullType() {
        assertFalse(industry.checkIndustryType(null), "Tipo de indústria null deve ser considerado inválido");
    }

    // Testa se um tipo de indústria válido é aceite
    @Test
    public void testCheckIndustryType_ValidType() {
        assertTrue(industry.checkIndustryType(IndustryType.COFFEE_FARM), "Tipo de indústria válido deve ser aceite");
    }

    // Testa se o método createPosition cria corretamente uma nova posição
    @Test
    public void testCreatePosition_DelegatesCorrectly() {
        Position newPos = Industry.createPosition(3, 4);
        assertEquals(3, newPos.getxCoordinate());
        assertEquals(4, newPos.getyCoordinate());
    }
}
