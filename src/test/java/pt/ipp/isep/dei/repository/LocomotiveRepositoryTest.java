package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Locomotive;
import pt.ipp.isep.dei.domain.LocomotiveModel;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LocomotiveRepositoryTest {

    private LocomotiveRepository repository;

    @BeforeEach
    void setUp() {
        repository = new LocomotiveRepository();
    }

    @Test
    void testAddLocomotiveAndGetAll() {
        Locomotive loco1 = new Locomotive(1, LocomotiveModel.BOBO);
        Locomotive loco2 = new Locomotive(2, LocomotiveModel.EE);

        repository.addLocomotive(loco1);
        repository.addLocomotive(loco2);

        ArrayList<Locomotive> allLocomotives = repository.getAll();

        assertEquals(2, allLocomotives.size());
        assertTrue(allLocomotives.contains(loco1));
        assertTrue(allLocomotives.contains(loco2));
    }

    @Test
    void testNextID_whenEmpty_shouldReturn1() {
        assertEquals(1, repository.nextID());
    }

    @Test
    void testNextID_withExistingLocomotives_shouldReturnMaxPlusOne() {
        Locomotive loco1 = new Locomotive(1, LocomotiveModel.BOBO);
        Locomotive loco2 = new Locomotive(5, LocomotiveModel.EE);
        Locomotive loco3 = new Locomotive(3, LocomotiveModel.A3);

        repository.addLocomotive(loco1);
        repository.addLocomotive(loco2);
        repository.addLocomotive(loco3);

        assertEquals(6, repository.nextID());
    }
    // Verifica que a lista retornada não afeta a lista interna do repositório
    @Test
    void testGetAll_returnsIndependentList() {
        Locomotive loco = new Locomotive(1, LocomotiveModel.BOBO);
        repository.addLocomotive(loco);

        ArrayList<Locomotive> copy = repository.getAll();
        copy.clear(); // modificar a cópia

        ArrayList<Locomotive> original = repository.getAll();
        assertEquals(1, original.size(), "Modifying the returned list should not affect the repository's internal list.");
    }
    // Verifica que a locomotiva é criada corretamente com dados válidos
    @Test
    void testCreateLocomotive_validData_returnsLocomotive() {
        Locomotive locomotive = LocomotiveRepository.createLocomotive(LocomotiveModel.A3, 10);
        assertNotNull(locomotive, "Locomotive should be created with valid model and ID.");
        assertEquals(10, locomotive.getId());
        assertEquals(LocomotiveModel.A3, locomotive.getLocomotiveModel());
    }
    // Verifica que a criação falha com ID inválido
    @Test
    void testCreateLocomotive_invalidId_returnsNull() {
        Locomotive locomotive = LocomotiveRepository.createLocomotive(LocomotiveModel.A3, -1);
        assertNull(locomotive, "Locomotive creation should fail with invalid ID.");
    }
    // Verifica que a criação falha com modelo nulo
    @Test
    void testCreateLocomotive_nullModel_returnsNull() {
        Locomotive locomotive = LocomotiveRepository.createLocomotive(null, 5);
        assertNull(locomotive, "Locomotive creation should fail with null model.");
    }
}
