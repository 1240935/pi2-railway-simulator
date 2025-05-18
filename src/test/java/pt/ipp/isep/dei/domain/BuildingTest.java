package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pt.ipp.isep.dei.domain.Building;
import pt.ipp.isep.dei.domain.BuildingType;


class BuildingTest {

    @Test
    void getBuildingType() {
        BuildingType buildingType = new BuildingType("200");
        Building building = new Building(buildingType);

        assertEquals(buildingType, building.getBuildingType());
    }

    @Test
    void setBuildingType() {
        BuildingType initialType = new BuildingType("Residential");
        BuildingType newType = new BuildingType("Commercial");

        Building building = new Building(initialType);
        building.setBuildingType(newType);

        assertEquals(newType, building.getBuildingType());
    }
}