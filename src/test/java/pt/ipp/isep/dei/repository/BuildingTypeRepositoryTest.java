package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.BuildingType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTypeRepositoryTest {

    private BuildingTypeRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BuildingTypeRepository();
    }

    @Test
    void testGetAvailableBuildingTypes_NotNull() {
        ArrayList<BuildingType> types = repository.getAvailableBuildingTypes();
        assertNotNull(types, "The returned list should not be null");
    }

    @Test
    void testGetAvailableBuildingTypes_ContainsAllEnumValues() {
        ArrayList<BuildingType> types = repository.getAvailableBuildingTypes();
        BuildingType[] expected = BuildingType.values();

        assertEquals(expected.length, types.size(), "List size should match number of enum values");

        for (BuildingType bt : expected) {
            assertTrue(types.contains(bt), "List should contain: " + bt);
        }
    }

    @Test
    void testGetAvailableBuildingTypes_ReturnsNewList() {
        ArrayList<BuildingType> types = repository.getAvailableBuildingTypes();
        types.clear(); // Try modifying it

        ArrayList<BuildingType> secondCall = repository.getAvailableBuildingTypes();

        assertFalse(secondCall.isEmpty(), "Modifying the returned list should not affect future calls");
        assertEquals(BuildingType.values().length, secondCall.size(), "Subsequent calls should return full list again");
    }
}
