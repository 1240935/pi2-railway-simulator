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

        // Verifica se não é nulo
        assertNotNull(available);

        // Verifica se o tamanho bate com o número de enums
        assertEquals(IndustryChanger.values().length, available.size());

        // Verifica se a lista contém todos os enums
        for (IndustryChanger changer : IndustryChanger.values()) {
            assertTrue(available.contains(changer));
        }
    }
}
