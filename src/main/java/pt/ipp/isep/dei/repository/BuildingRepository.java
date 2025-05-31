package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Building;
import pt.ipp.isep.dei.domain.BuildingType;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class to manage Building objects.
 */
public class BuildingRepository {
    private final ArrayList<Building> buildings = new ArrayList<>();

    /**
     * Adds a Building to the repository.
     *
     * @param building the Building to addScenario
     * @throws NullPointerException if the building is null
     */
    public void addBuilding(Building building) {
        if (building == null) {
            throw new NullPointerException("Building cannot be null");
        }
        buildings.add(building);
    }

    public Building createBuilding(BuildingType buildingType){
        Building building = new Building(buildingType);
        return building;
    }

    /**
     * Returns the list of Buildings in the repository.
     *
     * @return list of Building objects
     */
    public List<Building> getBuildings() {
        return buildings;
    }
}
