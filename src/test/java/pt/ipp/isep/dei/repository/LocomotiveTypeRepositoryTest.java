package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.LocomotiveType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocomotiveTypeRepositoryTest {

    // Verifica se a lista retornada não pode ser modificada em termos de conteúdo
    @Test
    void testGetLocomotiveTypes_isBackedByEnumAndModifiable() {
        LocomotiveTypeRepository repository = new LocomotiveTypeRepository();
        List<LocomotiveType> types = repository.getLocomotiveTypes();

        // Tenta modificar a lista (deverá lançar uma exceção se for fixa em tamanho)
        assertThrows(UnsupportedOperationException.class, () -> types.add(LocomotiveType.ELECTRIC), "Should not allow adding elements to the returned list.");
    }
    // Verifica se a lista de tipos de locomotiva não está vazia
    @Test
    void testGetLocomotiveTypes_notEmpty() {
        LocomotiveTypeRepository repository = new LocomotiveTypeRepository();
        List<LocomotiveType> types = repository.getLocomotiveTypes();

        assertFalse(types.isEmpty(), "The list of locomotive types should not be empty.");
    }
    // Verifica se todos os valores definidos no enum estão contidos na lista retornada
    @Test
    void testGetLocomotiveTypes_returnsAllEnumValues() {
        LocomotiveTypeRepository repository = new LocomotiveTypeRepository();
        List<LocomotiveType> types = repository.getLocomotiveTypes();

        assertNotNull(types, "The returned list should not be null.");

        LocomotiveType[] enumValues = LocomotiveType.values();
        assertEquals(enumValues.length, types.size(),
                "The number of types in the list should match the enum count.");

        for (LocomotiveType type : enumValues) {
            assertTrue(types.contains(type),
                    "The list should contain the enum value: " + type.name());
        }
    }

}
