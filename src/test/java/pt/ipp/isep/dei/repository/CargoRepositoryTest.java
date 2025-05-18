package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Cargo;
import pt.ipp.isep.dei.domain.ResourceType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CargoRepositoryTest {

    private CargoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CargoRepository();
    }

    @Test
    void testAddCargo_AddsSuccessfully() {
        Cargo coal = new Cargo(ResourceType.COAL, ResourceType.COAL.getValue()); // getvalue added to get the corresponding value on (ResourceType.java)
        repository.addCargo(coal);

        List<Cargo> cargoes = repository.getCargoes();
        assertEquals(1, cargoes.size());
        assertTrue(cargoes.contains(coal));
    }

    @Test
    void testAddCargo_DuplicateNotAdded() {
        Cargo oil = new Cargo(ResourceType.OIL, ResourceType.OIL.getValue());
        repository.addCargo(oil);
        repository.addCargo(oil); // Tenta adicionar o mesmo objeto novamente

        List<Cargo> cargoes = repository.getCargoes();
        assertEquals(1, cargoes.size());
    }

    @Test
    void testAddCargo_NullNotAdded() {
        repository.addCargo(null);
        List<Cargo> cargoes = repository.getCargoes();
        assertEquals(0, cargoes.size());
    }

    @Test
    void testGetCargoes_DefensiveCopy() {
        Cargo steel = new Cargo(ResourceType.STEEL, ResourceType.STEEL.getValue());
        repository.addCargo(steel);

        List<Cargo> cargoes = repository.getCargoes();
        cargoes.clear(); // Modifica a lista retornada, mas não deve afetar o repositório

        assertEquals(1, repository.getCargoes().size());
    }

    @Test
    void testGetCargoForResourceType_FindsCorrectCargo() {
        Cargo grain = new Cargo(ResourceType.GRAIN, ResourceType.GRAIN.getValue());
        Cargo autos = new Cargo(ResourceType.AUTOS, ResourceType.AUTOS.getValue());
        repository.addCargo(grain);
        repository.addCargo(autos);

        Cargo found = repository.getCargoForResourceType(ResourceType.GRAIN);
        assertNotNull(found);
        assertEquals(ResourceType.GRAIN, found.getType());
    }

    @Test
    void testGetCargoForResourceType_NotFoundReturnsNull() {
        repository.addCargo(new Cargo(ResourceType.WOOL, ResourceType.WOOL.getValue()));

        Cargo result = repository.getCargoForResourceType(ResourceType.SUGAR);
        assertNull(result);
    }

    @Test
    void testGetCargoForResourceType_EmptyRepoReturnsNull() {
        Cargo result = repository.getCargoForResourceType(ResourceType.PASSENGERS);
        assertNull(result);
    }

    @Test
    void testMultipleCargoTypesAreHandled() {
        ResourceType[] types = { ResourceType.COFFEE, ResourceType.MILK, ResourceType.FOOD };
        for (ResourceType type : types) {
            repository.addCargo(new Cargo(type, type.getValue()));
        }

        for (ResourceType type : types) {
            Cargo cargo = repository.getCargoForResourceType(type);
            assertNotNull(cargo);
            assertEquals(type, cargo.getType());
        }
    }
}
