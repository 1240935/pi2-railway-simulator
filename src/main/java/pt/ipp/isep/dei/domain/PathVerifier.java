package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

public class PathVerifier {

    public boolean verifyPath(GraphC graph, LocomotiveType locoType, StationType stationType) {
        List<Station> stations = graph.getStations();

        // Collect stations of the target type
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

    private boolean bfs(GraphC graph, LocomotiveType locoType, StationType targetStationType,
                        Station start, List<Station> candidates) {
        List<Station> queue = new ArrayList<>();
        List<Station> visited = new ArrayList<>();

        int head = 0; // Points to the current element in queue

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