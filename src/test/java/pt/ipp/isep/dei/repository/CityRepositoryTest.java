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

    // Testa se uma cidade é adicionada com sucesso ao repositório
    @Test
    void testAddCity_AddsSuccessfully() {
        Position pos = new Position(0, 0);
        List<CityBlock> blocks = new ArrayList<>();
        City city = new City("Lisboa", pos, blocks);

        repository.addCity(city);

        List<City> cities = repository.getCities();
        assertEquals(1, cities.size());
        assertTrue(cities.contains(city));
    }

    // Testa se todas as cidades adicionadas são corretamente retornadas
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

    // Testa se modificar a lista retornada por getCities() afeta a lista original (referência direta)
    @Test
    void testGetCities_ModifyingReturnedListAffectsOriginal() {
        Position pos = new Position(0, 0);
        City city = new City("Albufeira", pos, Collections.emptyList());

        repository.addCity(city);

        List<City> cities = repository.getCities();
        cities.clear();

        assertEquals(0, repository.getCities().size());
    }

    // Testa se uma cidade é criada corretamente com parâmetros válidos
    @Test
    void testCreateCity_WithValidParameters_ShouldCreateCity() {
        List<CityBlock> blocks = List.of(new CityBlock(new Position(1, 2)));
        int size = 10;

        City city = CityRepository.createCity(5, 5, "Póvoa de Varzim", blocks, size);

        assertNotNull(city);
        assertEquals("Póvoa de Varzim", city.getName());
        assertEquals(new Position(5, 5), city.getPosition());
        assertEquals(blocks, city.getBlocks());
    }

    // Testa se o método retorna null ao criar cidade com nome inválido
    @Test
    void testCreateCity_WithInvalidName_ShouldReturnNull() {
        List<CityBlock> blocks = List.of(new CityBlock(new Position(1, 2)));
        int size = 10;

        City city = CityRepository.createCity(5, 5, "", blocks, size);

        assertNull(city);
    }

    // Testa se o método retorna null ao criar cidade com lista de blocos inválida (vazia)
    @Test
    void testCreateCity_WithInvalidBlocks_ShouldReturnNull() {
        List<CityBlock> blocks = new ArrayList<>();
        int size = 10;

        City city = CityRepository.createCity(5, 5, "InvalidCity", blocks, size);

        assertNull(city);
    }

    // Testa se o método retorna null ao criar cidade com posição fora dos limites
    @Test
    void testCreateCity_WithInvalidPosition_ShouldReturnNull() {
        List<CityBlock> blocks = List.of(new CityBlock(new Position(0, 0)));
        int size = 5;

        City city = CityRepository.createCity(10, 10, "OutOfBounds", blocks, size);

        assertNull(city);
    }
}
