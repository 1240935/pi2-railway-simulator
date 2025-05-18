package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Building;

import java.util.ArrayList;
import java.util.List;

public class BuildingRepository {
    private final ArrayList<Building> buildings = new ArrayList<>();

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public List<Building> getBuildings() {
        return buildings;
    }
}
