package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.City;
import pt.ipp.isep.dei.domain.Position;
import pt.ipp.isep.dei.domain.CityBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityRepositoryTest {

    private CityRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CityRepository();
    }

    @Test
    void testAddCity_AddsSuccessfully() {
        Position pos = new Position(0, 0);  // Exemplo de posição
        List<CityBlock> blocks = new ArrayList<>();  // Lista vazia de cityBlocks
        City city = new City("Lisbon", pos, blocks);

        repository.addCity(city);

        List<City> cities = repository.getCities();
        assertEquals(1, cities.size());
        assertTrue(cities.contains(city));
    }

    @Test
    void testGetCities_ReturnsAllAddedCities() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(1, 1);

        City city1 = new City("Porto", pos1, Collections.emptyList());
        City city2 = new City("Coimbra", pos2, Collections.emptyList());

        repository.addCity(city1);
        repository.addCity(city2);

        List<City> cities = repository.getCities();
        assertEquals(2, cities.size());
        assertTrue(cities.contains(city1));
        assertTrue(cities.contains(city2));
    }

    @Test
    void testGetCities_ModifyingReturnedListAffectsOriginal() {
        Position pos = new Position(0, 0);
        City city = new City("Faro", pos, Collections.emptyList());

        repository.addCity(city);

        List<City> cities = repository.getCities();
        cities.clear();

        // Como getCities() retorna a lista interna, limpar a lista externa deve afetar a interna
        assertEquals(0, repository.getCities().size());
    }
}
