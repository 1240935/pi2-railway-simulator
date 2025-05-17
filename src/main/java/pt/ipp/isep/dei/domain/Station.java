package pt.ipp.isep.dei.domain;

/**
 * Represents a Station within the railway system.
 * <p>
 * A station has a name, position, center, type, building type, and cargo information.
 */
public class Station {
    private String name;

    private StationCenter center;

    private Position position;

    private StationType type;

    private BuildingType buildingType;

    private Cargo cargo;

    /**
     * Constructs a Station with a name, position, center, and type.
     *
     * @param name The name of the station.
     * @param position The position of the station on the map.
     * @param center The center of the station (e.g., geometric or compass center).
     * @param type The type of the station.
     */
    public Station(String name, Position position, StationCenter center, StationType type){
        this.name = name;
        this.center = center;
        this.position = position;
        this.type = type;
    }

    /**
     * Constructs a Station with only a name and type.
     * Useful when position and center are not yet defined.
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
     * @param name New name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the center of the station.
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

    /**
     * Returns the station's name.
     *
     * @return Name of the station.
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
     * Returns the position of the station on the map.
     *
     * @return Position object.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of the station on the map.
     *
     * @param position New position.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns the building type associated with this station.
     *
     * @return Building type, or null if not set.
     */
    public BuildingType getBuildingType() {
        return buildingType;
    }

    /**
     * Sets the building type associated with this station.
     *
     * @param buildingType New building type.
     */
    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    /**
     * Returns the cargo associated with this station.
     *
     * @return Cargo object, or null if not set.
     */
    public Cargo getCargo() {
        return cargo;
    }

    /**
     * Sets the cargo associated with this station.
     *
     * @param cargo New cargo.
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
