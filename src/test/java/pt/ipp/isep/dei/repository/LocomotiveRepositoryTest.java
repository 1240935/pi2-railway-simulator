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
}
