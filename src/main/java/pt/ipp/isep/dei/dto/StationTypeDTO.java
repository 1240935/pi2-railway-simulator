package pt.ipp.isep.dei.dto;

public class StationTypeDTO {
    private int cost, radius;
    private String name;

    public StationTypeDTO(int cost, int radius, String name){
        this.cost = cost;
        this.radius = radius;
        this.name = name;
    }

    public StationTypeDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public int getRadius() {
        return radius;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return name + " (Radius: " + radius + ", Cost: " + cost + ")";
    }
}
