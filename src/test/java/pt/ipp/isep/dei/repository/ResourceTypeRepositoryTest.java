package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTypeRepositoryTest {

    @Test
    void getAvailableResourceTypes_returnsAllResourceTypes() {
        ResourceTypeRepository repository = new ResourceTypeRepository();

        ArrayList<ResourceType> resourceTypes = repository.getAvailableResourceTypes();

        // Verifica se o tamanho da lista é igual ao número de enum constants
        assertEquals(ResourceType.values().length, resourceTypes.size());

        // Verifica se todos os ResourceType estão na lista
        for (ResourceType type : ResourceType.values()) {
            assertTrue(resourceTypes.contains(type));
        }
    }
}
