package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a railway graph composed of stations (nodes) and lines (edges).
 * Provides methods to build and query the graph structure.
 */
public class GraphC implements Serializable {
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
     * Returns a list of neighboring stations directly connected to the given station.
     * Only considers lines currently loaded in the graph.
     *
     * @param station the station whose neighbors to find
     * @return a list of adjacent stations
     */
    public List<Station> getNeighbors(Station station) {
        List<Station> neighbors = new ArrayList<>();

        for (Line line : this.lines) {
            Station start = line.getStart();
            Station end = line.getEnd();

            if (station.equals(start)) {
                if (!neighbors.contains(end)) {
                    neighbors.add(end);
                }
            } else if (station.equals(end)) {
                if (!neighbors.contains(start)) {
                    neighbors.add(start);
                }
            }
        }

        return neighbors;
    }

    /**
     * Returns the distance between two directly connected stations.
     * If no direct connection exists, returns -1.
     *
     * @param a first station
     * @param b second station
     * @return distance between them if connected; otherwise -1
     */
    public int getDistance(Station a, Station b) {
        for (Line line : this.lines) {
            Station start = line.getStart();
            Station end = line.getEnd();

            if ((start.equals(a) && end.equals(b)) || (start.equals(b) && end.equals(a))) {
                return line.getDistance(); // assuming Line has getDistance()
            }
        }
        return -1; // no direct connection
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
