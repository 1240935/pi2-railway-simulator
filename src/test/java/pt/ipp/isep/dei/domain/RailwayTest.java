package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RailwayTest {

    @Test
    public void testGetStart() {
        Station start = new Station("StartStation", StationType.TERMINAL);
        Station end = new Station("EndStation", StationType.DEPOT);
        Railway railway = new Railway(start, end, RailwayType.SINGLE);

        assertEquals(start, railway.getStart());
    }

    @Test
    public void testGetEnd() {
        Station start = new Station("StartStation", StationType.TERMINAL);
        Station end = new Station("EndStation", StationType.DEPOT);
        Railway railway = new Railway(start, end, RailwayType.SINGLE);

        assertEquals(end, railway.getEnd());
    }

    @Test
    public void testGetType() {
        Station start = new Station("StartStation", StationType.TERMINAL);
        Station end = new Station("EndStation", StationType.DEPOT);
        Railway railway = new Railway(start, end, RailwayType.ELECDOUBLE);

        assertEquals(RailwayType.ELECDOUBLE, railway.getType());
    }
}
