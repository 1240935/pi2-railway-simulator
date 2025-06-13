package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class MapTest {

    private Map map;

    @BeforeEach
    public void setUp() {
        map = new Map(10, "TestMap", 30);
    }

    // Testa se o nome do mapa é retornado corretamente
    @Test
    public void testGetName() {
        assertEquals("TestMap", map.getName());
    }

    // Testa se o tamanho do mapa é retornado corretamente
    @Test
    public void testGetSize() {
        assertEquals(10, map.getSize());
    }

    // Testa se o nome do mapa é atualizado corretamente
    @Test
    public void testSetName() {
        map.setName("NewName");
        assertEquals("NewName", map.getName());
    }

    // Testa se o tamanho do mapa é atualizado corretamente
    @Test
    public void testSetSize() {
        map.setSize(20);
        assertEquals(20, map.getSize());
    }

    // Testa se uma posição válida dentro dos limites é aceite
    @Test
    public void testCheckCoordinates_ValidPosition() {
        Position validPos = new Position(5, 5);
        assertTrue(map.checkCoordinates(validPos));
    }

    // Testa se uma posição com coordenada X negativa é rejeitada
    @Test
    public void testCheckCoordinates_InvalidPosition_NegativeX() {
        Position invalidPos = new Position(-1, 5);
        assertFalse(map.checkCoordinates(invalidPos));
    }

    // Testa se uma posição com coordenada Y negativa é rejeitada
    @Test
    public void testCheckCoordinates_InvalidPosition_NegativeY() {
        Position invalidPos = new Position(5, -1);
        assertFalse(map.checkCoordinates(invalidPos));
    }

    // Testa se uma posição fora do limite no eixo X é rejeitada
    @Test
    public void testCheckCoordinates_InvalidPosition_OutsideSizeX() {
        Position invalidPos = new Position(10, 5);
        assertFalse(map.checkCoordinates(invalidPos));
    }

    // Testa se uma posição fora do limite no eixo Y é rejeitada
    @Test
    public void testCheckCoordinates_InvalidPosition_OutsideSizeY() {
        Position invalidPos = new Position(5, 10);
        assertFalse(map.checkCoordinates(invalidPos));
    }

    // Testa a representação textual do mapa
    @Test
    public void testToString() {
        String expected = "Map{name='TestMap', size=10}";
        assertEquals(expected, map.toString());
    }

    // Testa se nomes válidos (apenas letras e espaços) são aceites
    @Test
    public void testCheckName_Valid() {
        assertTrue(map.checkName("Valid Map Name"), "Nomes com letras e espaços devem ser válidos");
    }

    // Testa se nomes inválidos (com números ou símbolos) são rejeitados
    @Test
    public void testCheckName_Invalid() {
        assertFalse(map.checkName("Map123"), "Nomes com números devem ser inválidos");
        assertFalse(map.checkName("Map!@#"), "Nomes com símbolos especiais devem ser inválidos");
    }

    // Testa se tamanhos válidos e inválidos são corretamente verificados
    @Test
    public void testCheckSize_ValidAndInvalid() {
        assertTrue(map.checkSize(5), "Tamanho positivo deve ser válido");
        assertFalse(map.checkSize(0), "Tamanho zero deve ser inválido");
        assertFalse(map.checkSize(-1), "Tamanho negativo deve ser inválido");
    }

    // Testa se escalas válidas e inválidas são corretamente verificadas
    @Test
    public void testCheckScale_ValidAndInvalid() {
        assertTrue(map.checkScale(10), "Escala positiva deve ser válida");
        assertFalse(map.checkScale(0), "Escala zero deve ser inválida");
        assertFalse(map.checkScale(-5), "Escala negativa deve ser inválida");
    }

    // Testa o getter e setter da escala do mapa
    @Test
    public void testGetAndSetScale() {
        map.setScale(50);
        assertEquals(50, map.getScale(), "A escala deve ser atualizada corretamente");
    }

    // Testa se uma cidade é corretamente adicionada e recuperada da lista
    @Test
    public void testAddAndGetCities() {
        City city = new City("Lisbon", new Position(1, 1), List.of(new CityBlock(new Position(1, 1))));
        map.addCity(city);
        assertTrue(map.getCities().contains(city), "A cidade deve ser adicionada corretamente à lista");
    }

    // Testa se uma indústria é corretamente adicionada e recuperada da lista
    @Test
    public void testAddAndGetIndustries() {
        Industry industry = new Industry(IndustryType.BAKERY, new Position(2, 3));
        map.addIndustry(industry);
        assertTrue(map.getIndustries().contains(industry), "A indústria deve ser adicionada corretamente à lista");
    }
}
