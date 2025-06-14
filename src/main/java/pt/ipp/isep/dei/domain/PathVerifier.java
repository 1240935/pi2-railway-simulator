package pt.ipp.isep.dei.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for verifying if a valid travel path exists between stations
 * of a certain type, using a specific locomotive type, based on the railway graph.
 */
public class PathVerifier implements Serializable {

    /**
     * Verifies if a valid path exists between stations of the given type using the specified locomotive type.
     *
     * @param graph        the railway graph containing stations and connections
     * @param locoType     the type of locomotive used for travel (e.g., ELECTRIC)
     * @param stationType  the type of station that must be reachable (e.g., TERMINAL)
     * @return true if a valid path exists between at least two stations of the specified type; false otherwise
     */
    public boolean verifyPath(GraphC graph, LocomotiveType locoType, StationType stationType) {
        List<Station> stations = graph.getStations();

        List<Station> candidateStations = new ArrayList<>();
        for (Station s : stations) {
            if (s.getType() == stationType) {
                candidateStations.add(s);
            }
        }

        for (Station start : candidateStations) {
            if (bfs(graph, locoType, stationType, start, candidateStations)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Performs a Breadth-First Search (BFS) from the starting station to find
     * a reachable station of the same target type that satisfies locomotive constraints.
     *
     * @param graph              the railway graph
     * @param locoType           the locomotive type being used
     * @param targetStationType  the type of station to reach
     * @param start              the station from which to begin the search
     * @param candidates         the list of stations of the target type
     * @return true if another station of the same type is reachable; false otherwise
     */
    private boolean bfs(GraphC graph, LocomotiveType locoType, StationType targetStationType,
                        Station start, List<Station> candidates) {
        List<Station> queue = new ArrayList<>();
        List<Station> visited = new ArrayList<>();

        int head = 0;

        queue.add(start);
        visited.add(start);

        while (head < queue.size()) {
            Station current = queue.get(head);
            head++;

            if (current != start && current.getType() == targetStationType) {
                return true;
            }

            List<Line> connections = graph.getConnections(current);
            for (Line line : connections) {
                Station neighbor = line.getStart().equals(current) ? line.getEnd() : line.getStart();

                if (visited.contains(neighbor)) continue;

                if (locoType == LocomotiveType.ELECTRIC && !line.isElectrified()) {
                    continue;
                }

                visited.add(neighbor);
                queue.add(neighbor);
            }
        }

        return false;
    }
}
