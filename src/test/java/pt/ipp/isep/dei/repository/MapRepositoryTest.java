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

    @Test
    void testAddMap_increasesSize() {
        Map map = new Map("Test Map", 10);
        repository.addMap(map);

        ArrayList<Map> maps = repository.getMaps();

        assertEquals(1, maps.size());
        assertTrue(maps.contains(map));
    }

    @Test
    void testGetMaps_initiallyEmpty() {
        ArrayList<Map> maps = repository.getMaps();
        assertNotNull(maps);
        assertTrue(maps.isEmpty());
    }

    @Test
    void testGetMaps_returnsReferenceToInternalList() {
        Map map = new Map("Test Map", 10);
        repository.addMap(map);

        ArrayList<Map> maps = repository.getMaps();
        maps.clear();

        // Depois de limpar a lista retornada, o repositório deve ficar vazio
        assertTrue(repository.getMaps().isEmpty());
    }
}
