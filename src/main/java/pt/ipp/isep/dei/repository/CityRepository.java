package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.City;

import java.util.ArrayList;
import java.util.List;

public class CityRepository {
    private final ArrayList<City> cities = new ArrayList<>();

    public void addCity(City city) {
        cities.add(city);
    }

    public List<City> getCities() {
        return cities;
    }

}
