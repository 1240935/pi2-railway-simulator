package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pt.ipp.isep.dei.domain.Building;
import pt.ipp.isep.dei.domain.BuildingType;


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
}