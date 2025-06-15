package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.BuildingType;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTypeRepositoryTest {

    private BuildingTypeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BuildingTypeRepository();
    }

    // Testa se o método não retorna null
    @Test
    void testGetAvailableBuildingTypes_NotNull() {
        ArrayList<BuildingType> types = repository.getAvailableBuildingTypes();
        assertNotNull(types, "The returned list should not be null");
    }

    // Testa se todos os valores do enum estão presentes na lista retornada
    @Test
    void testGetAvailableBuildingTypes_ContainsAllEnumValues() {
        ArrayList<BuildingType> types = repository.getAvailableBuildingTypes();
        BuildingType[] expected = BuildingType.values();

        assertEquals(expected.length, types.size(), "List size should match number of enum values");

        for (BuildingType bt : expected) {
            assertTrue(types.contains(bt), "List should contain: " + bt);
        }
    }

    // Testa se a lista retornada é uma nova instância a cada chamada
    @Test
    void testGetAvailableBuildingTypes_ReturnsNewList() {
        ArrayList<BuildingType> types = repository.getAvailableBuildingTypes();
        types.clear(); // Tenta modificar a lista

        ArrayList<BuildingType> secondCall = repository.getAvailableBuildingTypes();

        assertFalse(secondCall.isEmpty(), "Modifying the returned list should not affect future calls");
        assertEquals(BuildingType.values().length, secondCall.size(), "Subsequent calls should return full list again");
    }

    // Extra: Testa se os elementos retornados são exatamente os mesmos (mesma ordem do enum)
    @Test
    void testGetAvailableBuildingTypes_OrderMatchesEnum() {
        ArrayList<BuildingType> types = repository.getAvailableBuildingTypes();
        BuildingType[] expected = BuildingType.values();

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], types.get(i), "Element at index " + i + " should match enum order");
        }
    }
}
