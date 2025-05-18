package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.LocomotiveModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocomotiveModelRepositoryTest {

    @Test
    void testGetLocomotiveModels_returnsAllModels() {
        LocomotiveModelRepository repo = new LocomotiveModelRepository();
        List<LocomotiveModel> models = repo.getLocomotiveModels();
        assertNotNull(models);
        assertEquals(LocomotiveModel.values().length, models.size());
        for (LocomotiveModel model : LocomotiveModel.values()) {
            assertTrue(models.contains(model));
        }
    }
}
