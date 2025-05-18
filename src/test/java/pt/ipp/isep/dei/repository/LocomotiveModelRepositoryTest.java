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

        // Verifica se a lista não é nula
        assertNotNull(models);

        // Verifica se a lista contém o mesmo número de elementos que o enum LocomotiveModel
        assertEquals(LocomotiveModel.values().length, models.size());

        // Verifica se a lista contém todos os valores do enum
        for (LocomotiveModel model : LocomotiveModel.values()) {
            assertTrue(models.contains(model));
        }
    }
}
