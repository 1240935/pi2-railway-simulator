package pt.ipp.isep.dei.domain;
import java.util.List;
import java.util.ArrayList;

public class Map {
    private String name;
    private int size;
    private List<Industry> industries;
    private List<City> cities;

    public Map(String name, int size) {
        this.name = name;
        this.cities = new ArrayList<>();
        this.industries = new ArrayList<>();;
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

    public void addCity(City city) {
        cities.add(city);
    }

    public List<City> getCities() {
        return cities;
    }

    public void addIndustry(Industry industry){
        industries.add(industry);
    }

    public List<Industry> getIndustries() {
        return industries;
    }

    @Override
    public String toString() {
        return "Map{name='" + name + "', size=" + size + "}";
    }
}