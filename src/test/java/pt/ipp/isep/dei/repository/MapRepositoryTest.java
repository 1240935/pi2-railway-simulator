package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Map;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapRepositoryTest {

    private MapRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MapRepository();
    }

    // Testa se adicionar um mapa aumenta corretamente o tamanho da lista
    @Test
    void testAddMap_increasesSize() {
        Map map = new Map(10, "Test Map", 23);
        repository.addMap(map);

        ArrayList<Map> maps = repository.getMaps();

        assertEquals(1, maps.size());
        assertTrue(maps.contains(map));
    }

    // Testa se a lista de mapas está vazia logo após a criação do repositório
    @Test
    void testGetMaps_initiallyEmpty() {
        ArrayList<Map> maps = repository.getMaps();
        assertNotNull(maps);
        assertTrue(maps.isEmpty());
    }

    // Testa se o método getMaps devolve uma referência direta (e não uma cópia)
    @Test
    void testGetMaps_returnsReferenceToInternalList() {
        Map map = new Map(10, "Test Map", 24);
        repository.addMap(map);

        ArrayList<Map> maps = repository.getMaps();
        maps.clear(); // Altera diretamente a lista interna

        assertTrue(repository.getMaps().isEmpty());
    }

    // Testa se a criação de um mapa com parâmetros válidos funciona corretamente
    @Test
    void testCreateMap_validParameters_returnsMap() {
        Map map = MapRepository.createMap(10, "Portugal", 25);
        assertNotNull(map, "Map should be created successfully with valid parameters.");
    }

    // Testa se a criação falha quando o nome é inválido (vazio)
    @Test
    void testCreateMap_invalidName_returnsNull() {
        Map map = MapRepository.createMap(10, "", 25);
        assertNull(map, "Map creation should fail with invalid name.");
    }

    // Testa se a criação falha quando o tamanho é inválido (negativo)
    @Test
    void testCreateMap_invalidSize_returnsNull() {
        Map map = MapRepository.createMap(-5, "Test", 25);
        assertNull(map, "Map creation should fail with invalid size.");
    }

    // Testa se a criação falha quando a escala é inválida (negativa)
    @Test
    void testCreateMap_invalidScale_returnsNull() {
        Map map = MapRepository.createMap(10, "Test", -1);
        assertNull(map, "Map creation should fail with invalid scale.");
    }

    // Testa se o processo de serialização e deserialização de um mapa funciona corretamente
    @Test
    void testSaveAndLoadMap_serializationWorks() {
        Map map = new Map(15, "Europe", 50);
        String filePath = "test_map.ser";

        boolean saved = repository.saveMapToSerializedFile(map, filePath);
        assertTrue(saved, "Map should be successfully serialized.");

        Map loadedMap = repository.loadMapFromSerializedFile(filePath);
        assertNotNull(loadedMap, "Loaded map should not be null.");
        assertEquals(map.getName(), loadedMap.getName());
        assertEquals(map.getSize(), loadedMap.getSize());
        assertEquals(map.getScale(), loadedMap.getScale());
    }
}
