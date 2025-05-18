package pt.ipp.isep.dei.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphCTest {

    @Test
    void buildGraph_ShouldSetStationsAndLines() {
        Station station1 = new Station("Station1", StationType.STATION);
        Station station2 = new Station("Station2", StationType.TERMINAL);

        Line line1 = new Line(station1, station2, true, 100.0);

        GraphC graph = new GraphC();
        graph.buildGraph(Arrays.asList(station1, station2), Arrays.asList(line1));

        List<Station> stations = graph.getStations();
        List<Line> lines = graph.getLines();

        assertEquals(2, stations.size());
        assertTrue(stations.contains(station1));
        assertTrue(stations.contains(station2));

        assertEquals(1, lines.size());
        assertTrue(lines.contains(line1));
    }

    @Test
    void getConnections_ShouldReturnAllLinesConnectedToStation() {
        Station station1 = new Station("Station1", StationType.DEPOT);
        Station station2 = new Station("Station2", StationType.STATION);
        Station station3 = new Station("Station3", StationType.TERMINAL);

        Line line1 = new Line(station1, station2, true, 100.0);
        Line line2 = new Line(station3, station1, false, 150.0);
        Line line3 = new Line(station2, station3, true, 200.0);

        GraphC graph = new GraphC();
        graph.buildGraph(Arrays.asList(station1, station2, station3), Arrays.asList(line1, line2, line3));

        List<Line> connections = graph.getConnections(station1);

        assertEquals(2, connections.size());
        assertTrue(connections.contains(line1));
        assertTrue(connections.contains(line2));
    }

    @Test
    void containsStation_ShouldReturnTrueIfStationExists() {
        Station station1 = new Station("Station1", StationType.STATION);
        Station station2 = new Station("Station2", StationType.TERMINAL);

        GraphC graph = new GraphC();
        graph.buildGraph(Arrays.asList(station1), List.of());

        assertTrue(graph.containsStation(station1));
        assertFalse(graph.containsStation(station2));
    }
}
