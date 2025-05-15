package pt.ipp.isep.dei.domain;

public class Station {
    private String name;
    private int capacity;
    private Position center;
    private StationType type;

    public Station(String name,int capacity, Position center, StationType type){
        this.name = name;
        this.capacity = capacity;
        this.center = center;
        this.type = type;
    }

    public Station(String name, StationType type) {
        this.name = name;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCenter(Position center) {
        this.center = center;
    }

    public void setType(StationType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Position getCenter() {
        return center;
    }

    public StationType getType() {
        return type;
    }
}
