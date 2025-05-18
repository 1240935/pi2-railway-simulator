package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Line;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.StationType;
import pt.ipp.isep.dei.domain.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LineRepositoryTest {

    private LineRepository lineRepository;
    private List<Station> stations;

    @BeforeEach
    public void setUp() {
        lineRepository = new LineRepository();

        stations = new ArrayList<>();
        stations.add(new Station("StationA", StationType.STATION));
        stations.get(0).setPosition(new Position(0, 0));

        stations.add(new Station("StationB", StationType.STATION));
        stations.get(1).setPosition(new Position(10, 10));
    }

    @Test
    public void testLoadLines() {
        // Prepare a temporary file with line data
        String testFilePath = "test_lines.csv";

        // Create test file content
        // Format: startName;endName;electrified(1 or 0);distance
        // Example: StationA;StationB;1;15.5
        try (java.io.PrintWriter writer = new java.io.PrintWriter(testFilePath)) {
            writer.println("StationA;StationB;1;15.5");
            writer.println("StationB;StationA;0;15.5");
        } catch (Exception e) {
            fail("Failed to create test file");
        }

        List<Line> lines = lineRepository.loadLines(testFilePath, stations);

        assertEquals(2, lines.size());

        Line firstLine = lines.get(0);
        assertEquals("StationA", firstLine.getStart().getName());
        assertEquals("StationB", firstLine.getEnd().getName());
        assertTrue(firstLine.isElectrified());
        assertEquals(15.5, firstLine.getDistance(), 0.001);

        Line secondLine = lines.get(1);
        assertEquals("StationB", secondLine.getStart().getName());
        assertEquals("StationA", secondLine.getEnd().getName());
        assertFalse(secondLine.isElectrified());
        assertEquals(15.5, secondLine.getDistance(), 0.001);

        // Cleanup
        new java.io.File(testFilePath).delete();
    }

    @Test
    public void testGetElectrifiedLines() {
        List<Line> allLines = new ArrayList<>();

        Line electrifiedLine = new Line(stations.get(0), stations.get(1), true, 20.0);
        Line nonElectrifiedLine = new Line(stations.get(1), stations.get(0), false, 20.0);

        allLines.add(electrifiedLine);
        allLines.add(nonElectrifiedLine);

        List<Line> electrifiedLines = lineRepository.getElectrifiedLines(allLines);

        assertEquals(1, electrifiedLines.size());
        assertTrue(electrifiedLines.contains(electrifiedLine));
        assertFalse(electrifiedLines.contains(nonElectrifiedLine));
    }
}
