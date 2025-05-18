package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.IndustryChanger;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IndustryChangerRepositoryTest {

    private final IndustryChangerRepository repository = new IndustryChangerRepository();

    @Test
    void testGetAvailableIndustryChanger_ReturnsAllValues() {
        ArrayList<IndustryChanger> available = repository.getAvailableIndustryChanger();
        assertNotNull(available);
        assertEquals(IndustryChanger.values().length, available.size());
        for (IndustryChanger changer : IndustryChanger.values()) {
            assertTrue(available.contains(changer));
        }
    }
}
