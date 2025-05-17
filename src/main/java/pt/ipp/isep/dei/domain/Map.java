package pt.ipp.isep.dei.domain;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private String name;
    private int size;

    public Map(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean checkCoordinates(Position position) {
        return position.getxCoordinate() >= 0 && position.getxCoordinate() < size &&
                position.getyCoordinate() >= 0 && position.getyCoordinate() < size;
    }

    @Override
    public String toString() {
        return "Map{name='" + name + "', size=" + size + "}";
    }
}