package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.IndustryType;
import pt.ipp.isep.dei.domain.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IndustryRepositoryTest {

    private IndustryRepository repo;

    // Inicializa o repositório antes de cada teste
    @BeforeEach
    void setUp() {
        repo = new IndustryRepository();
    }

    // Verifica que o repositório começa vazio
    @Test
    void testGetIndustries_ReturnsEmptyListInitially() {
        List<Industry> industries = repo.getIndustries();
        assertTrue(industries.isEmpty(), "Repository should start with an empty list of industries.");
    }

    // Verifica que a indústria adicionada tem o tipo correto
    @Test
    void testAddIndustry_ShouldAddCorrectIndustryType() {
        Position pos = new Position(5, 10);
        Industry industry = new Industry(IndustryType.CEMENT_PLANT, pos);
        repo.addIndustry(industry);

        List<Industry> industries = repo.getIndustries();
        assertEquals(1, industries.size(), "Repository should contain one industry.");
        assertEquals(IndustryType.CEMENT_PLANT, industries.get(0).getType(), "Industry type should match the one added.");
    }

    // Verifica que a posição da indústria foi corretamente armazenada
    @Test
    void testAddIndustry_ShouldStoreCorrectPosition() {
        Position pos = new Position(5, 10);
        Industry industry = new Industry(IndustryType.CEMENT_PLANT, pos);
        repo.addIndustry(industry);

        Position storedPos = repo.getIndustries().get(0).getPosition();
        assertEquals(5, storedPos.getxCoordinate(), "X coordinate should be 5.");
        assertEquals(10, storedPos.getyCoordinate(), "Y coordinate should be 10.");
    }

    // Verifica que a criação de uma indústria com parâmetros válidos retorna um objeto válido
    @Test
    void testCreateIndustry_WithValidParameters_ReturnsIndustry() {
        Industry created = IndustryRepository.createIndustry(IndustryType.STEEL_MILL, 2, 3, 100);
        assertNotNull(created, "Valid parameters should result in a non-null Industry.");
        assertEquals(IndustryType.STEEL_MILL, created.getType(), "Industry type should match input.");
        assertEquals(2, created.getPosition().getxCoordinate(), "X coordinate should be 2.");
        assertEquals(3, created.getPosition().getyCoordinate(), "Y coordinate should be 3.");
    }

    // Verifica que a criação de uma indústria com parâmetros inválidos retorna null
    @Test
    void testCreateIndustry_WithInvalidParameters_ReturnsNull() {
        Industry invalid = IndustryRepository.createIndustry(null, -5, -5, 0);
        assertNull(invalid, "Invalid parameters should result in null Industry.");
    }
}
