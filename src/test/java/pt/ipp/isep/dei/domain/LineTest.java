package pt.ipp.isep.dei.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LineTest {

    private Line line;
    private Station startStation;
    private Station endStation;

    @BeforeEach
    public void setUp() {
        // Criar duas estações fictícias para os testes
        startStation = new Station("StartStation", StationType.STATION);
        endStation = new Station("EndStation", StationType.TERMINAL);

        // Criar a linha com as estações, eletrificação e distância
        line = new Line(startStation, endStation, true, 12.5);
    }

    @Test
    public void testGetStart() {
        assertEquals(startStation, line.getStart(), "Deve retornar a estação inicial correta");
    }

    @Test
    public void testGetEnd() {
        assertEquals(endStation, line.getEnd(), "Deve retornar a estação final correta");
    }

    @Test
    public void testIsElectrified() {
        assertTrue(line.isElectrified(), "Deve retornar true para linha eletrificada");
    }

    @Test
    public void testGetDistance() {
        assertEquals(12.5, line.getDistance(), 0.0001, "Deve retornar a distância correta");
    }
}
