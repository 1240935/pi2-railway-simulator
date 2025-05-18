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
        assertEquals(ResourceType.values().length, resourceTypes.size());
        for (ResourceType type : ResourceType.values()) {
            assertTrue(resourceTypes.contains(type));
        }
    }
}
