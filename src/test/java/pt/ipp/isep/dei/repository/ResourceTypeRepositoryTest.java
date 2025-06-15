package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTypeRepositoryTest {

    // Verifica se todos os valores do enum ResourceType estão incluídos na lista retornada
    @Test
    void getAvailableResourceTypes_returnsAllResourceTypes() {
        ResourceTypeRepository repository = new ResourceTypeRepository();
        ArrayList<ResourceType> resourceTypes = repository.getAvailableResourceTypes();
        assertEquals(ResourceType.values().length, resourceTypes.size());
        for (ResourceType type : ResourceType.values()) {
            assertTrue(resourceTypes.contains(type));
        }
    }
    // Verifica se a lista retornada pode ser modificada (ex: remover elementos)
    @Test
    void getAvailableResourceTypes_listIsModifiable() {
        ResourceTypeRepository repository = new ResourceTypeRepository();
        ArrayList<ResourceType> resourceTypes = repository.getAvailableResourceTypes();

        assertDoesNotThrow(() -> resourceTypes.remove(0), "Returned list should be modifiable");
    }

    // Verifica se chamadas consecutivas retornam listas independentes (modificações em uma não afetam a outra)
    @Test
    void getAvailableResourceTypes_returnsIndependentCopy() {
        ResourceTypeRepository repository = new ResourceTypeRepository();

        ArrayList<ResourceType> firstCall = repository.getAvailableResourceTypes();
        firstCall.clear(); // Modifica a primeira lista

        ArrayList<ResourceType> secondCall = repository.getAvailableResourceTypes();

        assertEquals(ResourceType.values().length, secondCall.size(), "Each call should return a new independent list");
    }
}
