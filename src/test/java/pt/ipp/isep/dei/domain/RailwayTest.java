package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RailwayTest {

    // Testa se a estação de início é corretamente retornada
    @Test
    public void testGetStart() {
        Station start = new Station("StartStation", StationType.TERMINAL);
        Station end = new Station("EndStation", StationType.DEPOT);
        Railway railway = new Railway(start, end, RailwayType.SINGLE, 23.00);

        assertEquals(start, railway.getStart());
    }

    // Testa se a estação de fim é corretamente retornada
    @Test
    public void testGetEnd() {
        Station start = new Station("StartStation", StationType.TERMINAL);
        Station end = new Station("EndStation", StationType.DEPOT);
        Railway railway = new Railway(start, end, RailwayType.SINGLE, 23.00);

        assertEquals(end, railway.getEnd());
    }

    // Testa se o tipo da ferrovia é corretamente retornado
    @Test
    public void testGetType() {
        Station start = new Station("StartStation", StationType.TERMINAL);
        Station end = new Station("EndStation", StationType.DEPOT);
        Railway railway = new Railway(start, end, RailwayType.ELECDOUBLE,23.00);

        assertEquals(RailwayType.ELECDOUBLE, railway.getType());
    }

    // Testa se o construtor aceita corretamente todos os tipos de RailwayType
    @Test
    public void testRailwayWithAllTypes() {
        Station start = new Station("Start", StationType.TERMINAL);
        Station end = new Station("End", StationType.DEPOT);
        double distance = 23.00;

        for (RailwayType type : RailwayType.values()) {
            Railway railway = new Railway(start, end, type, distance);
            assertEquals(type, railway.getType(), "Railway type does not match the expected value");
        }
    }

    // Testa se é possível criar uma ferrovia com a mesma estação como início e fim
    @Test
    public void testSameStationAsStartAndEnd() {
        Station same = new Station("SameStation", StationType.TERMINAL);
        Railway railway = new Railway(same, same, RailwayType.SINGLE, 23.00);

        assertEquals(same, railway.getStart());
        assertEquals(same, railway.getEnd());
    }

    // Testa se a ferrovia mantém as referências originais às estações (imutabilidade)
    @Test
    public void testRailwayIsImmutable() {
        Station start = new Station("Alpha", StationType.DEPOT);
        Station end = new Station("Beta", StationType.TERMINAL);
        Railway railway = new Railway(start, end, RailwayType.SINGLE, 23.00);

        assertSame(start, railway.getStart());
        assertSame(end, railway.getEnd());
    }
}
