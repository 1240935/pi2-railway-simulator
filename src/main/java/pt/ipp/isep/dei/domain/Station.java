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
     * Creates a Station including a building type along with other attributes.
     *
     * @param name     Name of the station.
     * @param position Geographical position of the station.
     * @param center   The center position of the station.
     * @param type     Station type.
     * @param buildings Buildings associated with the station.
     * @param demands  Cargo demands list.
     * @param supplies Cargo supplies list.
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

    /**
     * Sets the name of the station.
     *
     * @param name New name for the station.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the center position of the station.
     *
     * @param center New center position.
     */
    public void setCenter(StationCenter center) {
        this.center = center;
    }

    /**
     * Sets the type of the station.
     *
     * @param type New station type.
     */
    public void setType(StationType type) {
        this.type = type;
    }

    public void setDemands(List<Cargo> demands) {
        this.demands = demands;
    }

    public void setSupplies(List<Cargo> supplies) {
        this.supplies = supplies;
    }

    /**
     * Returns the name of the station.
     *
     * @return Station's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the center position of the station.
     *
     * @return Station center.
     */
    public StationCenter getCenter() {
        return center;
    }

    /**
     * Returns the type of the station.
     *
     * @return Station type.
     */
    public StationType getType() {
        return type;
    }

    /**
     * Returns the geographical position of the station on the map.
     *
     * @return Position object representing the station location.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the geographical position of the station.
     *
     * @param position New position of the station.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    /**
     * Returns the list of cargo demands at this station.
     *
     * @return a list of Cargo objects representing what is demanded
     */
    public List<Cargo> getDemands() {
        return demands;
    }

    /**
     * Returns the list of cargo supplies available at this station.
     *
     * @return a list of Cargo objects representing what is supplied
     */
    public List<Cargo> getSupplies() {
        return supplies;
    }

    /**
     * Computes the cargo status (supplied and demanded cargo) for the station
     * based on the industries and cities within the station's influence radius.
     *
     * <p>Industries and cities are considered if their positions are within the station's
     * radius (defined by the station type) from the station center.
     * Supplied and demanded cargoes from these industries and cities are aggregated
     * and returned in a {@link CargoStatus} object.
     *
     * @param industries List of all industries to consider.
     * @param cities     List of all cities to consider.
     * @return A CargoStatus containing supplied and demanded cargo lists.
     */
    public CargoStatus getCargoes(List<Industry> industries, List<City> cities) {
        int radius = type.getRadius();
        List<Cargo> supplied = new ArrayList<>();
        List<Cargo> demanded = new ArrayList<>();

        for (Industry industry : industries) {
            if (center.getPosition().euclideanDistance(industry.getPosition()) <= radius) {
                for (ResourceType resourceType : industry.getType().getExportation()) {
                    supplied.add(new Cargo(resourceType, 0));  // quantity 0 (default or placeholder)
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
     * Computes the cargo supplies for the station
     * based on the industries and cities within the station's influence radius.
     *
     * <p>Industries and cities are considered if their positions are within the station's
     * radius (defined by the station type) from the station center.
     * Supplied cargoes from these industries and cities are aggregated
     * and returned as a list of Cargo objects.
     *
     * @param industries List of all industries to consider.
     * @param cities     List of all cities to consider.
     * @return A list of Cargo objects representing the supplies.
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

    public static Position createPosition(int x, int y) {
        return Position.createPosition(x, y);
    }

    public static Building createBuilding(BuildingType buildingType){
        return Repositories.getInstance().getBuildingRepository().createBuilding(buildingType);
    }

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

    /**
     * Checks if the station center is defined.
     *
     * @return true if the center is not null, false otherwise.
     */
    public static boolean checkCenter(StationCenter center) {
        return center != null;
    }

    /**
     * Checks if the station type is defined.
     *
     * @return true if the type is not null, false otherwise.
     */
    public static boolean checkType(StationType type) {
        return type != null;
    }

    public int getDemandLevel() {
        return demandLevel;
    }

    public void setDemandLevel(int demandLevel) {
        this.demandLevel = demandLevel;
    }

    public double getTotalRevenueBonus() {
        double totalBonus = 0;
        for (Building b : buildings) {
            totalBonus += b.getRevenueBonus();
        }
        return totalBonus;
    }

    public void incrementDeliveries() {
        deliveriesCount++;
    }

    public int getDeliveriesCount() {
        return deliveriesCount;
    }

    public void resetDeliveries() {
        this.deliveriesCount = 0;
    }

    /**
     * Returns the name of the station.
     *
     * @return Station name.
     */
    @Override
    public String toString() {
        return name;
    }
}
