package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @Test
    void getBuildingType() {
        BuildingType buildingType = BuildingType.SMALL_CAFE;
        Building building = new Building(buildingType);

        assertEquals(buildingType, building.getBuildingType());
    }
    @Test
    void setBuildingType() {
        BuildingType initialType = BuildingType.SMALL_CAFE;
        BuildingType newType = BuildingType.LARGE_CAFE;

        Building building = new Building(initialType);
        building.setBuildingType(newType);

        assertEquals(newType, building.getBuildingType());
    }
    @Test
    void constructorShouldThrowExceptionWhenTypeIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Building(null);
        });
        assertEquals("Building type cannot be null.", exception.getMessage());
    }
    @Test
    void mutuallyExclusiveTypesShouldBeDetectedCorrectly() {
        assertTrue(BuildingType.SMALL_CAFE.isMutuallyExclusiveWith(BuildingType.LARGE_CAFE));
        assertFalse(BuildingType.SMALL_CAFE.isMutuallyExclusiveWith(BuildingType.SMALL_HOTEL));
    }
    @Test
    void upgradeRelationshipShouldBeCorrectlyDetected() {
        assertTrue(BuildingType.LARGE_CAFE.isUpgradeFrom(BuildingType.SMALL_CAFE));
        assertFalse(BuildingType.SMALL_CAFE.isUpgradeFrom(BuildingType.LARGE_CAFE));
        assertFalse(BuildingType.SMALL_CAFE.isUpgradeFrom(BuildingType.SMALL_HOTEL));
    }

    @Test
    void toStringShouldIncludeNameAndCost() {
        String result = BuildingType.TELEGRAPH.toString();
        assertTrue(result.contains("TELEGRAPH"));
        assertTrue(result.contains("100"));
    }
    @Test
    void getMutuallyExclusiveTypesShouldReturnCorrectTypes() {
        BuildingType[] result = BuildingType.SMALL_CAFE.getMutuallyExclusiveTypes();

        assertEquals(1, result.length);
        assertEquals(BuildingType.LARGE_CAFE, result[0]);
    }
}