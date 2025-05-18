package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Industry;
import pt.ipp.isep.dei.domain.IndustryType;
import pt.ipp.isep.dei.domain.Position;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IndustryRepositoryTest {

    @Test
    public void testAddIndustry() {
        IndustryRepository repo = new IndustryRepository();

        Position pos = new Position(5, 10);
        Industry industry = new Industry(IndustryType.CEMENT_PLANT, pos);

        repo.addIndustry(industry);

        List<Industry> industries = repo.getIndustries();

        assertEquals(1, industries.size());
        assertEquals(IndustryType.CEMENT_PLANT, industries.get(0).getType());

        assertEquals(5, industries.get(0).getPosition().getxCoordinate());
        assertEquals(10, industries.get(0).getPosition().getyCoordinate());
    }
}
