package pt.ipp.isep.dei.dto;

/**
 * Data Transfer Object (DTO) for StationType.
 * Contains basic information about a station type such as cost, radius, and name.
 */
public class StationTypeDTO {
    private int cost;
    private int radius;
    private String name;

    /**
     * Constructs a StationTypeDTO with the specified cost, radius, and name.
     *
     * @param cost   the cost associated with the station type
     * @param radius the radius value for the station type
     * @param name   the name of the station type
     */
    public StationTypeDTO(int cost, int radius, String name){
        this.cost = cost;
        this.radius = radius;
        this.name = name;
    }

    /**
     * Default constructor.
     */
    public StationTypeDTO(){
    }

    /**
     * Returns the name of the station type.
     *
     * @return the station type name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the station type.
     *
     * @param name the new name of the station type
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the cost associated with the station type.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost associated with the station type.
     *
     * @param cost the new cost value
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Returns the radius value for the station type.
     *
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Sets the radius value for the station type.
     *
     * @param radius the new radius value
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Returns a string representation of the station type, including its name,
     * radius, and cost.
     *
     * @return a formatted string describing the station type
     */
    @Override
    public String toString() {
        return name + " (Radius: " + radius + ", Cost: " + cost + ")";
    }
}
