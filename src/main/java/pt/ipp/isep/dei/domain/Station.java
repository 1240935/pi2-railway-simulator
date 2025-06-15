package pt.ipp.isep.dei.domain;

import pt.ipp.isep.dei.repository.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Represents a Station within the railway system.
 * <p>
 * A station is characterized by its name, geographical position, center position, type,
 * building type, and cargo demands and supplies.
 * It can provide cargo status based on nearby industries and cities within its influence radius.
 * </p>
 */
public class Station implements Serializable {

    private String name;
    private StationCenter center;
    private Position position;
    private StationType type;
    private List<Building> buildings = new ArrayList<>();
    private List<Cargo> demands;
    private List<Cargo> supplies;
    private int demandLevel;
    private int deliveriesCount;

    /**
     * Creates a Station with specified name, center, position, type, demands, and supplies.
     *
     * @param name     Name of the station.
     * @param center   The center position of the station.
     * @param position The geographical position of the station on the map.
     * @param type     The type/category of the station.
     * @param demands  List of cargo demands of the station.
     * @param supplies List of cargo supplies of the station.
     */
    public Station(String name, StationCenter center, Position position, StationType type, List<Cargo> demands, List<Cargo> supplies) {
        this.name = name;
        this.center = center;
        this.position = position;
        this.type = type;
        this.demands = demands;
        this.supplies = supplies;
    }

    /**
     * Creates a Station including buildings along with other attributes.
     *
     * @param name      Name of the station.
     * @param position  Geographical position of the station.
     * @param center    The center position of the station.
     * @param type      Station type.
     * @param buildings Buildings associated with the station.
     * @param demands   Cargo demands list.
     * @param supplies  Cargo supplies list.
     */
    public Station(String name, Position position, StationCenter center, StationType type, List<Building> buildings, List<Cargo> demands, List<Cargo> supplies) {
        this.name = name;
        this.center = center;
        this.position = position;
        this.type = type;
        this.buildings = buildings;
        this.demands = demands;
        this.supplies = supplies;
    }

    /**
     * Constructs a Station with only name and type.
     * Useful when position, center, and building details are not yet defined.
     *
     * @param name The name of the station.
     * @param type The type of the station.
     */
    public Station(String name, StationType type) {
        this.name = name;
        this.type = type;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setCenter(StationCenter center) {
        this.center = center;
    }

    public void setType(StationType type) {
        this.type = type;
    }

    public void setDemands(List<Cargo> demands) {
        this.demands = demands;
    }

    public void setSupplies(List<Cargo> supplies) {
        this.supplies = supplies;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    // Getters

    public String getName() {
        return name;
    }

    public StationCenter getCenter() {
        return center;
    }

    public StationType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Cargo> getDemands() {
        return demands;
    }

    public List<Cargo> getSupplies() {
        return supplies;
    }

    /**
     * Adds a building to the station's list of buildings.
     *
     * @param building The building to add.
     */
    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    /**
     * Computes the cargo status (supplied and demanded cargo) for the station
     * based on industries and cities within the station's influence radius.
     * <p>
     * Industries and cities are considered if their positions are within the station's
     * radius (defined by the station type) from the station center.
     * Supplied and demanded cargoes from these industries and cities are aggregated
     * and returned in a {@link CargoStatus} object.
     *
     * @param industries List of industries to consider.
     * @param cities     List of cities to consider.
     * @return CargoStatus containing lists of supplied and demanded cargo.
     */
    public CargoStatus getCargoes(List<Industry> industries, List<City> cities) {
        int radius = type.getRadius();
        List<Cargo> supplied = new ArrayList<>();
        List<Cargo> demanded = new ArrayList<>();

        for (Industry industry : industries) {
            if (center.getPosition().euclideanDistance(industry.getPosition()) <= radius) {
                for (ResourceType resourceType : industry.getType().getExportation()) {
                    supplied.add(new Cargo(resourceType, 0)); // quantity 0 placeholder
                }
                for (ResourceType resourceType : industry.getType().getImportation()) {
                    demanded.add(new Cargo(resourceType, 0));
                }
            }
        }

        for (City city : cities) {
            if (center.getPosition().euclideanDistance(city.getPosition()) <= radius) {
                for (ResourceType resourceType : city.getSuppliedCargoes()) {
                    supplied.add(new Cargo(resourceType, 0));
                }
                for (ResourceType resourceType : city.getDemandedCargoes()) {
                    demanded.add(new Cargo(resourceType, 0));
                }
            }
        }

        return new CargoStatus(supplied, demanded);
    }

    /**
     * Computes only the cargo supplies for the station based on nearby industries and cities.
     * <p>
     * Industries and cities are considered if within the station's radius.
     *
     * @param industries List of industries.
     * @param cities     List of cities.
     * @return List of supplied cargo.
     */
    public List<Cargo> getSuppliesOnly(List<Industry> industries, List<City> cities) {
        int radius = type.getRadius();
        List<Cargo> supplied = new ArrayList<>();

        for (Industry industry : industries) {
            if (center.getPosition().euclideanDistance(industry.getPosition()) <= radius) {
                for (ResourceType resourceType : industry.getType().getExportation()) {
                    supplied.add(new Cargo(resourceType, 0));
                }
            }
        }

        for (City city : cities) {
            if (center.getPosition().euclideanDistance(city.getPosition()) <= radius) {
                for (ResourceType resourceType : city.getSuppliedCargoes()) {
                    supplied.add(new Cargo(resourceType, 0));
                }
            }
        }

        return supplied;
    }

    // Static helpers

    public static Position createPosition(int x, int y) {
        return Position.createPosition(x, y);
    }

    public static Building createBuilding(BuildingType buildingType) {
        return Repositories.getInstance().getBuildingRepository().createBuilding(buildingType);
    }

    public static boolean checkName(String name) {
        return name != null && name.matches("^[A-Za-z ]+$");
    }

    /**
     * Checks if the given position is valid for placing a station on the map,
     * ensuring no overlap with cities, industries, or existing stations.
     *
     * @param position Position to check.
     * @param map      Map containing cities and industries.
     * @param scenario Scenario containing stations.
     * @return true if position is valid (no overlap), false otherwise.
     */
    public static boolean checkOverbuilding(Position position, Map map, Scenario scenario) {
        if (!map.checkCoordinates(position)) {
            return false;
        }

        for (City city : map.getCities()) {
            if (city.getPosition().equals(position)) {
                return false;
            }
        }

        for (Industry industry : map.getIndustries()) {
            if (industry.getPosition().equals(position)) {
                return false;
            }
        }

        for (Station station : scenario.getStations()) {
            if (station.getPosition().equals(position)) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkCenter(StationCenter center) {
        return center != null;
    }

    public static boolean checkType(StationType type) {
        return type != null;
    }

    // Demand level accessors

    public int getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(int demandLevel) {
        this.demandLevel = demandLevel;
    }

    /**
     * Calculates total revenue bonus from all buildings associated with this station.
     *
     * @return sum of revenue bonuses
     */
    public double getTotalRevenueBonus() {
        double totalBonus = 0;
        for (Building b : buildings) {
            totalBonus += b.getRevenueBonus();
        }
        return totalBonus;
    }

    /**
     * Increments the delivery count by one.
     */
    public void incrementDeliveries() {
        deliveriesCount++;
    }

    public int getDeliveriesCount() {
        return deliveriesCount;
    }

    /**
     * Resets the delivery count to zero.
     */
    public void resetDeliveries() {
        this.deliveriesCount = 0;
    }

    /**
     * Returns the name of the station as its string representation.
     *
     * @return station name
     */
    @Override
    public String toString() {
        return name;
    }
}
