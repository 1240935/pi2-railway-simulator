package pt.ipp.isep.dei.dto;

public class StationTypeDTO {
    private int cost, radius;

    public StationTypeDTO(int cost, int radius){
        this.cost = cost;
        this.radius = radius;
    }

    public StationTypeDTO(){

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
        return ("Radius = " + radius + " Cost = " +cost);
    }
}
