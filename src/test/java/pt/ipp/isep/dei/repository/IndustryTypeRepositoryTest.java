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
}
