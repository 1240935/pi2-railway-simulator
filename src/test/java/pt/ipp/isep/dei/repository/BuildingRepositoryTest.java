package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Building;
import pt.ipp.isep.dei.domain.BuildingType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildingRepositoryTest {

    private BuildingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BuildingRepository();
    }

    @Test
    void testGetBuildings_EmptyInitially() {
        List<Building> result = repository.getBuildings();
        assertTrue(result.isEmpty(), "Expected no buildings initially");
    }

    @Test
    void testAddSingleBuilding() {
        Building building = new Building(BuildingType.SMALL_CAFE);
        repository.addBuilding(building);

        List<Building> result = repository.getBuildings();
        assertEquals(1, result.size());
        assertSame(building, result.get(0));
        assertEquals(BuildingType.SMALL_CAFE, result.get(0).getBuildingType());
    }

    @Test
    void testAddMultipleBuildings() {
        Building b1 = new Building(BuildingType.SMALL_CAFE);
        Building b2 = new Building(BuildingType.LARGE_CAFE);
        Building b3 = new Building(BuildingType.CUSTOMS);

        repository.addBuilding(b1);
        repository.addBuilding(b2);
        repository.addBuilding(b3);

        List<Building> result = repository.getBuildings();
        assertEquals(3, result.size());
        assertSame(b1, result.get(0));
        assertSame(b2, result.get(1));
        assertSame(b3, result.get(2));
    }

    @Test
    void testAddBuilding_NullReference() {
        assertThrows(NullPointerException.class, () -> {
            repository.addBuilding(null);
        });
    }
}
