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

    // Testa se um Cargo é adicionado corretamente
    @Test
    void testAddCargo_AddsSuccessfully() {
        Cargo coal = new Cargo(ResourceType.COAL, ResourceType.COAL.getValue());
        repository.addCargo(coal);

        List<Cargo> cargoes = repository.getCargoes();
        assertEquals(1, cargoes.size());
        assertTrue(cargoes.contains(coal));
    }

    // Testa se um Cargo duplicado não é adicionado
    @Test
    void testAddCargo_DuplicateNotAdded() {
        Cargo oil = new Cargo(ResourceType.OIL, ResourceType.OIL.getValue());
        repository.addCargo(oil);
        repository.addCargo(oil);

        List<Cargo> cargoes = repository.getCargoes();
        assertEquals(1, cargoes.size());
    }

    // Testa se null não é adicionado
    @Test
    void testAddCargo_NullNotAdded() {
        repository.addCargo(null);
        assertEquals(0, repository.getCargoes().size());
    }

    // Testa se a lista retornada é uma cópia (não pode ser modificada externamente)
    @Test
    void testGetCargoes_DefensiveCopy() {
        Cargo steel = new Cargo(ResourceType.STEEL, ResourceType.STEEL.getValue());
        repository.addCargo(steel);

        List<Cargo> cargoes = repository.getCargoes();
        cargoes.clear();

        assertEquals(1, repository.getCargoes().size());
    }

    // Testa se encontra corretamente um Cargo pelo ResourceType
    @Test
    void testGetCargoForResourceType_FindsCorrectCargo() {
        Cargo grain = new Cargo(ResourceType.GRAIN, ResourceType.GRAIN.getValue());
        repository.addCargo(grain);

        Cargo found = repository.getCargoForResourceType(ResourceType.GRAIN);
        assertNotNull(found);
        assertEquals(ResourceType.GRAIN, found.getType());
    }

    // Testa se retorna null ao procurar um tipo que não existe
    @Test
    void testGetCargoForResourceType_NotFoundReturnsNull() {
        repository.addCargo(new Cargo(ResourceType.WOOL, ResourceType.WOOL.getValue()));

        Cargo result = repository.getCargoForResourceType(ResourceType.SUGAR);
        assertNull(result);
    }

    // Testa se retorna null quando o repositório está vazio
    @Test
    void testGetCargoForResourceType_EmptyRepoReturnsNull() {
        assertNull(repository.getCargoForResourceType(ResourceType.PASSENGERS));
    }

    // Testa se múltiplos cargos com tipos diferentes são corretamente armazenados e encontrados
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

    //  Testa se dois cargos diferentes com mesmo ResourceType não são tratados como duplicados
    @Test
    void testAddCargo_SameTypeDifferentInstance() {
        Cargo c1 = new Cargo(ResourceType.COAL, 20);
        Cargo c2 = new Cargo(ResourceType.COAL, 35);
        repository.addCargo(c1);
        repository.addCargo(c2);

        assertEquals(2, repository.getCargoes().size(), "Deveria permitir se forem instâncias diferentes");
    }

    //  Stress test leve com muitos cargos
    @Test
    void testAddManyCargoes() {
        for (int i = 0; i < 1000; i++) {
            repository.addCargo(new Cargo(ResourceType.WOOL, i));
        }
        assertEquals(1000, repository.getCargoes().size());
    }
}

