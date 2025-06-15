package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.IndustryChanger;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IndustryChangerRepositoryTest {

    private final IndustryChangerRepository repository = new IndustryChangerRepository();

    /**
     * Testa se o método retorna todos os valores definidos no enum IndustryChanger.
     */
    @Test
    void testGetAvailableIndustryChanger_ReturnsAllValues() {
        ArrayList<IndustryChanger> available = repository.getAvailableIndustryChanger();

        assertNotNull(available, "The returned list should not be null.");
        assertEquals(IndustryChanger.values().length, available.size(),
                "The list size should match the number of enum values.");
        for (IndustryChanger changer : IndustryChanger.values()) {
            assertTrue(available.contains(changer),
                    "The list should contain: " + changer);
        }
    }

    /**
     * Testa se a lista retornada é independente (ou seja, modificações externas não afetam a lista interna).
     */
    @Test
    void testReturnedListIsIndependent() {
        ArrayList<IndustryChanger> original = repository.getAvailableIndustryChanger();
        original.clear();

        ArrayList<IndustryChanger> again = repository.getAvailableIndustryChanger();

        assertFalse(again.isEmpty(), "Modifying the returned list should not affect the original data.");
    }

    /**
     * Testa se a lista retornada não contém valores nulos.
     */
    @Test
    void testNoNullValuesInList() {
        ArrayList<IndustryChanger> list = repository.getAvailableIndustryChanger();
        assertFalse(list.contains(null), "The list should not contain null values.");
    }
}
