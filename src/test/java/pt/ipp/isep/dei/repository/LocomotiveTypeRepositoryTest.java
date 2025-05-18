package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.LocomotiveType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocomotiveTypeRepositoryTest {

    @Test
    void testGetLocomotiveTypes_returnsAllEnumValues() {
        LocomotiveTypeRepository repository = new LocomotiveTypeRepository();
        List<LocomotiveType> types = repository.getLocomotiveTypes();
        assertNotNull(types);
        LocomotiveType[] enumValues = LocomotiveType.values();
        assertEquals(enumValues.length, types.size());
        for (LocomotiveType type : enumValues) {
            assertTrue(types.contains(type));
        }
    }
}
