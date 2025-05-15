package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a railway graph composed of stations (nodes) and lines (edges).
 * Provides methods to build and query the graph structure.
 */
public class GraphC {
    private List<Station> stations = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();

    /**
     * Builds the graph by setting the list of stations and lines.
     * Clears any existing data before adding the new stations and lines.
     *
     * @param stations list of stations (nodes) in the graph
     * @param lines    list of lines (edges) connecting the stations
     */
    public void buildGraph(List<Station> stations, List<Line> lines) {
        this.stations.clear();
        for (Station s : stations) {
            this.stations.add(s);
        }

        this.lines.clear();
        for (Line l : lines) {
            this.lines.add(l);
        }
    }

    /**
     * Returns the list of stations in the graph.
     *
     * @return list of stations
     */
    public List<Station> getStations() {
        return stations;
    }

    /**
     * Returns the list of lines in the graph.
     *
     * @return list of lines
     */
    public List<Line> getLines() {
        return lines;
    }

    /**
     * Returns all lines (connections) that start or end at the specified station.
     *
     * @param station the station whose connections are requested
     * @return list of lines connected to the station
     */
    public List<Line> getConnections(Station station) {
        List<Line> connections = new ArrayList<>();
        for (Line line : lines) {
            if (line.getStart().equals(station) || line.getEnd().equals(station)) {
                connections.add(line);
            }
        }
        return connections;
    }

    /**
     * Checks whether the graph contains the specified station.
     *
     * @param station the station to check for existence in the graph
     * @return true if the station exists in the graph, false otherwise
     */
    public boolean containsStation(Station station) {
        for (Station s : stations) {
            if (s.equals(station)) {
                return true;
            }
        }
        return false;
    }
}
