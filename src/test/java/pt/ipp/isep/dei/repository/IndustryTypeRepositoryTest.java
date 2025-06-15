package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.IndustryType;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IndustryTypeRepositoryTest {

    @Test
    void testGetIndustryTypes_NotNull() {
        IndustryTypeRepository repo = new IndustryTypeRepository();
        ArrayList<IndustryType> types = repo.getIndustryTypes();
        assertNotNull(types, "The returned list shouldnt be null.");
    }

    @Test
    void testGetIndustryTypes_NotEmpty() {
        IndustryTypeRepository repo = new IndustryTypeRepository();
        ArrayList<IndustryType> types = repo.getIndustryTypes();
        assertFalse(types.isEmpty(), "The IndustryType List shouldn't be empty.");
    }

    @Test
    void testGetIndustryTypes_ContainsAllIndustryTypes() {
        IndustryTypeRepository repo = new IndustryTypeRepository();
        ArrayList<IndustryType> types = repo.getIndustryTypes();

        // Verifica se todos os IndustryType estão na lista retornada
        for (IndustryType type : IndustryType.values()) {
            assertTrue(types.contains(type), "The list should contain the IndustryType: " + type);
        }
    }
    // Verifica que modificar a lista retornada não afeta futuras chamadas
    @Test
    void testReturnedListIsIndependent() {
        IndustryTypeRepository repo = new IndustryTypeRepository();
        ArrayList<IndustryType> first = repo.getIndustryTypes();
        first.clear(); // Tentativa de limpar

        ArrayList<IndustryType> second = repo.getIndustryTypes();
        assertFalse(second.isEmpty(), "Clearing returned list should not affect internal state.");
    }
    // Verifica que a lista não contém valores nulos
    @Test
    void testGetIndustryTypes_ContainsNoNulls() {
        IndustryTypeRepository repo = new IndustryTypeRepository();
        ArrayList<IndustryType> types = repo.getIndustryTypes();

        assertFalse(types.contains(null), "The returned list should not contain null values.");
    }

}
