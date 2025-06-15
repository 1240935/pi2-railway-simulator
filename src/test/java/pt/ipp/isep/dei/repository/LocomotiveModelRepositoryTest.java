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
    // Verifica que a lista não contém valores nulos
    @Test
    void testGetLocomotiveModels_containsNoNulls() {
        LocomotiveModelRepository repo = new LocomotiveModelRepository();
        List<LocomotiveModel> models = repo.getLocomotiveModels();
        assertFalse(models.contains(null), "The returned list should not contain null values.");
    }
    // Verifica que a lista retornada não permite modificações
    @Test
    void testGetLocomotiveModels_isUnmodifiable() {
        LocomotiveModelRepository repo = new LocomotiveModelRepository();
        List<LocomotiveModel> models = repo.getLocomotiveModels();

        assertThrows(UnsupportedOperationException.class, () -> models.add(LocomotiveModel.values()[0]),
                "The returned list should be unmodifiable.");
    }

}
