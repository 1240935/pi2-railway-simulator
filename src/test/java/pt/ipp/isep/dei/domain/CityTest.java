package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    // Testa se getName() retorna corretamente o nome da cidade
    @Test
    void getName_ShouldReturnCorrectName() {
        City city = new City("Lisboa", new Position(10, 20), List.of());
        assertEquals("Lisboa", city.getName());
    }

    // Testa se checkName() aceita nomes válidos (apenas letras e espaços)
    @Test
    void checkName_ShouldReturnTrue_ForValidNames() {
        City city = new City("Test", new Position(0, 0), List.of());
        assertTrue(city.checkName("Lisboa"));
        assertTrue(city.checkName("São João da Madeira"));
    }

    // Testa se checkName() rejeita nomes inválidos (com números ou símbolos)
    @Test
    void checkName_ShouldReturnFalse_ForInvalidNames() {
        City city = new City("Test", new Position(0, 0), List.of());
        assertFalse(city.checkName("Lisboa123"));
        assertFalse(city.checkName("!@#"));
    }

    // Testa se getPosition() retorna corretamente a posição da cidade
    @Test
    void getPosition_ShouldReturnCorrectPosition() {
        Position pos = new Position(10, 20);
        City city = new City("Porto", pos, List.of());
        assertEquals(pos, city.getPosition());
    }

    // Testa se checkCityBlocks() retorna false quando um bloco tem mesma posição que a cidade
    @Test
    void checkCityBlocks_ShouldReturnFalse_IfBlockPositionEqualsCityPosition() {
        Position pos = new Position(5, 5);
        CityBlock block = new CityBlock(pos);
        City city = new City("Test", pos, List.of(block));

        assertFalse(city.checkCityBlocks(List.of(block)));
    }

    // Testa se checkCityBlocks() retorna false quando há blocos com posições duplicadas
    @Test
    void checkCityBlocks_ShouldReturnFalse_IfDuplicatePositions() {
        Position p1 = new Position(2, 2);
        CityBlock block1 = new CityBlock(p1);
        CityBlock block2 = new CityBlock(p1); // mesma posição

        City city = new City("Test", new Position(0, 0), List.of(block1, block2));
        assertFalse(city.checkCityBlocks(List.of(block1, block2)));
    }

    // Testa se checkCityBlocks() retorna true quando todas as posições são únicas e diferentes da central
    @Test
    void checkCityBlocks_ShouldReturnTrue_IfAllUniqueAndDifferentFromCityCenter() {
        Position center = new Position(5, 5);
        CityBlock block1 = new CityBlock(new Position(1, 1));
        CityBlock block2 = new CityBlock(new Position(2, 2));
        City city = new City("ValidCity", center, List.of(block1, block2));

        assertTrue(city.checkCityBlocks(List.of(block1, block2)));
    }

    // Testa se getBlocks() retorna corretamente a lista de blocos da cidade
    @Test
    void getBlocks_ShouldReturnCorrectBlocksList() {
        CityBlock block1 = new CityBlock(new Position(1, 1));
        CityBlock block2 = new CityBlock(new Position(2, 2));
        List<CityBlock> blocks = List.of(block1, block2);

        City city = new City("Coimbra", new Position(5, 5), blocks);
        assertEquals(blocks, city.getBlocks());
    }

    // Testa se createBlocks() cria corretamente os blocos com base nas posições dadas
    @Test
    void createBlocks_ShouldCreateCorrectNumberOfBlocks() {
        List<Position> positions = List.of(
                new Position(1, 1),
                new Position(2, 2),
                new Position(3, 3)
        );

        List<CityBlock> blocks = City.createBlocks(positions, 3);

        assertEquals(3, blocks.size());
        assertEquals(positions.get(0), blocks.get(0).getPosition());
    }

    // Testa se getSuppliedCargoes() retorna os tipos de recursos fornecidos esperados
    @Test
    void getSuppliedCargoes_ShouldReturnExpectedList() {
        City city = new City("Faro", new Position(1, 1), List.of());
        List<ResourceType> expectedSupplied = List.of(ResourceType.PASSENGERS, ResourceType.MAIL);
        assertEquals(expectedSupplied, city.getSuppliedCargoes());
    }

    // Testa se getDemandedCargoes() retorna os tipos de recursos exigidos esperados
    @Test
    void getDemandedCargoes_ShouldReturnExpectedList() {
        City city = new City("Faro", new Position(1, 1), List.of());
        List<ResourceType> expectedDemanded = List.of(ResourceType.PASSENGERS, ResourceType.MAIL, ResourceType.GOODS, ResourceType.FOOD);
        assertEquals(expectedDemanded, city.getDemandedCargoes());
    }
}
