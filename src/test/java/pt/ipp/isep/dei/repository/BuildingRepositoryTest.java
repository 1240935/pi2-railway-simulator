package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Building;
import pt.ipp.isep.dei.domain.BuildingType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildingRepositoryTest {

    private BuildingRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BuildingRepository();
    }

    // Testa se o repositório está vazio inicialmente
    @Test
    void testGetBuildings_EmptyInitially() {
        List<Building> result = repository.getBuildings();
        assertTrue(result.isEmpty(), "Expected no buildings initially");
    }

    // Testa a adição de um único building e a sua recuperação
    @Test
    void testAddSingleBuilding() {
        Building building = new Building(BuildingType.SMALL_CAFE);
        repository.addBuilding(building);

        List<Building> result = repository.getBuildings();
        assertEquals(1, result.size());
        assertSame(building, result.get(0));
        assertEquals(BuildingType.SMALL_CAFE, result.get(0).getBuildingType());
    }

    // Testa a adição de vários buildings e a ordem de inserção
    @Test
    void testAddMultipleBuildings() {
        Building b1 = new Building(BuildingType.SMALL_CAFE);
        Building b2 = new Building(BuildingType.LARGE_CAFE);
        Building b3 = new Building(BuildingType.CUSTOMS);

        repository.addBuilding(b1);
        repository.addBuilding(b2);
        repository.addBuilding(b3);

        List<Building> result = repository.getBuildings();
        assertEquals(3, result.size());
        assertSame(b1, result.get(0));
        assertSame(b2, result.get(1));
        assertSame(b3, result.get(2));
    }

    // Testa se é lançada exceção ao tentar adicionar null
    @Test
    void testAddBuilding_NullReference() {
        assertThrows(NullPointerException.class, () -> repository.addBuilding(null));
    }

    // Testa se é possível modificar a lista retornada diretamente de fora
    @Test
    void testGetBuildings_ModifyListOutside() {
        Building building = new Building(BuildingType.SMALL_CAFE);
        repository.addBuilding(building);

        List<Building> buildings = repository.getBuildings();
        buildings.clear(); // Tenta limpar de fora

        // Verifica se ainda está preservado no repositório
        assertEquals(1, repository.getBuildings().size(), "Lista interna deve permanecer intacta");
    }

    // Testa a criação de um building válido com o método estático createBuilding
    @Test
    void testCreateBuilding_ValidType() {
        Building building = BuildingRepository.createBuilding(BuildingType.SMALL_HOTEL);
        assertNotNull(building);
        assertEquals(BuildingType.SMALL_HOTEL, building.getBuildingType());
    }

    // Testa se o repositório permite a inserção do mesmo building mais de uma vez
    @Test
    void testAddSameBuildingMultipleTimes() {
        Building building = new Building(BuildingType.CUSTOMS);
        repository.addBuilding(building);
        repository.addBuilding(building);

        List<Building> result = repository.getBuildings();
        assertEquals(2, result.size(), "Deve permitir duplicados");
    }
}
