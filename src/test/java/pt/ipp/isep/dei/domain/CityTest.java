package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    @Test
    void getName_ShouldReturnCorrectName() {
        City city = new City("Lisboa", new Position(10, 20), List.of());
        assertEquals("Lisboa", city.getName());
    }

    @Test
    void getPosition_ShouldReturnCorrectPosition() {
        Position pos = new Position(10, 20);
        City city = new City("Porto", pos, List.of());
        assertEquals(pos, city.getPosition());
    }

    @Test
    void getBlocks_ShouldReturnCorrectBlocksList() {
        CityBlock block1 = new CityBlock(new Position(1, 1));
        CityBlock block2 = new CityBlock(new Position(2, 2));
        List<CityBlock> blocks = List.of(block1, block2);

        City city = new City("Coimbra", new Position(5, 5), blocks);
        assertEquals(blocks, city.getBlocks());
    }

    @Test
    void getSuppliedCargoes_ShouldReturnExpectedList() {
        City city = new City("Faro", new Position(1, 1), List.of());
        List<ResourceType> expectedSupplied = List.of(ResourceType.PASSENGERS, ResourceType.MAIL);
        assertEquals(expectedSupplied, city.getSuppliedCargoes());
    }

    @Test
    void getDemandedCargoes_ShouldReturnExpectedList() {
        City city = new City("Faro", new Position(1, 1), List.of());
        List<ResourceType> expectedDemanded = List.of(ResourceType.PASSENGERS, ResourceType.MAIL, ResourceType.GOODS, ResourceType.FOOD);
        assertEquals(expectedDemanded, city.getDemandedCargoes());
    }
}
