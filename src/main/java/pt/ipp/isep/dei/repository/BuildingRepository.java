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
     * @param building the Building to add
     * @throws NullPointerException if the building is null
     */
    public void addBuilding(Building building) {
        if (building == null) {
            throw new NullPointerException("Building cannot be null");
        }
        buildings.add(building);
    }

    /**
     * Creates a Building with the given BuildingType and adds it to the repository.
     *
     * @param buildingType the type of building to create
     * @return the created Building, or null if the type is invalid
     */
    public static Building createBuilding(BuildingType buildingType) {
        if (!Building.checkType(buildingType)) {
            System.out.println("Invalid type.");
            return null;
        }
        Building building = new Building(buildingType);
        System.out.println("Building created successfully!");
        Repositories.getInstance().getBuildingRepository().addBuilding(building);
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
