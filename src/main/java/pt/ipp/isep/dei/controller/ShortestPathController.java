package pt.ipp.isep.dei.controller;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.LineRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.StationRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShortestPathController {

    private StationRepository stationRepo;
    private LineRepository lineRepo;
    private GraphC graph;
    private List<Station> stations;
    private List<Line> lines;
    private int lastPathCost = 0;
    private String startStationName;
    private String endStationName;
    private List<String> waypointsNames;

    public ShortestPathController() {
        this.stationRepo = Repositories.getInstance().getStationRepository();
        this.lineRepo = Repositories.getInstance().getLineRepository();
        this.graph = new GraphC();
        this.waypointsNames = new ArrayList<>();
    }

    public void loadRouteFromFile(String filePath) {
        startStationName = null;
        endStationName = null;
        waypointsNames.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                String[] stationNames = line.split(";");
                if (stationNames.length >= 2) {
                    startStationName = stationNames[0].trim();
                    endStationName = stationNames[stationNames.length - 1].trim();
                    for (int i = 1; i < stationNames.length - 1; i++) {
                        waypointsNames.add(stationNames[i].trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // follows your existing loadStations style
        }
    }


    public String getStartStation() {
        return this.startStationName;
    }

    public String getEndStation() {
        return this.endStationName;
    }

    public List<String> getWaypoints() {
        return this.waypointsNames;
    }

    public void setFiles(String stationsFile, String linesFile) {
        this.stations = stationRepo.loadStations(stationsFile);
        this.lines = lineRepo.loadLines(linesFile, stations);
        graph.buildGraph(stations, lines);
    }

    public List<Station> findShortestPathWithWaypoints(String startName, String endName, List<String> waypointNames) {
        Station start = getStationByName(startName);
        Station end = getStationByName(endName);
        if (start == null || end == null) return new ArrayList<>();

        List<Station> waypoints = new ArrayList<>();
        for (String name : waypointNames) {
            Station wp = getStationByName(name);
            if (wp != null) waypoints.add(wp);
        }

        List<Station> fullPath = new ArrayList<>();
        Station current = start;

        for (int i = 0; i <= waypoints.size(); i++) {
            Station next = (i < waypoints.size()) ? waypoints.get(i) : end;
            List<Station> segment = dijkstra(current, next);
            if (segment.isEmpty()) return new ArrayList<>();
            if (!fullPath.isEmpty()) segment.remove(0); // avoid duplication
            fullPath.addAll(segment);
            current = next;
        }

        return fullPath;
    }

    public List<String> getShortestPathAsNames(String start, String end, List<String> waypoints) {
        List<Station> path = findShortestPathWithWaypoints(start, end, waypoints);
        List<String> names = new ArrayList<>();
        lastPathCost = 0;

        if (path == null || path.isEmpty()) return names;

        for (int i = 0; i < path.size(); i++) {
            names.add(path.get(i).getName());

            if (i < path.size() - 1) {
                int segmentCost = graph.getDistance(path.get(i), path.get(i + 1));
                lastPathCost += segmentCost;
            }
        }

        return names;
    }

    public List<Station> dijkstra(Station start, Station end) {
        List<Station> visited = new ArrayList<>();
        List<Station> unvisited = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();
        List<Station> previous = new ArrayList<>();

        for (Station s : stations) {
            unvisited.add(s);
            distances.add(s.equals(start) ? 0 : Integer.MAX_VALUE);
            previous.add(null);
        }

        while (!unvisited.isEmpty()) {
            int minIndex = getMinDistanceIndex(unvisited, distances);
            if (minIndex == -1) break;

            Station current = unvisited.get(minIndex);
            int currentDist = distances.get(stations.indexOf(current));

            if (current.equals(end)) break;

            unvisited.remove(minIndex);

            for (Station neighbor : graph.getNeighbors(current)) {
                if (visited.contains(neighbor)) continue;

                int edgeWeight = graph.getDistance(current, neighbor);
                int altDist = currentDist + edgeWeight;
                int neighborIndex = stations.indexOf(neighbor);

                if (altDist < distances.get(neighborIndex)) {
                    distances.set(neighborIndex, altDist);
                    previous.set(neighborIndex, current);
                }
            }

            visited.add(current);
        }

        return reconstructPath(previous, start, end);
    }

    public List<Station> reconstructPath(List<Station> prev, Station start, Station end) {
        List<Station> path = new ArrayList<>();
        Station step = end;

        while (step != null && !step.equals(start)) {
            path.add(0, step);
            step = prev.get(stations.indexOf(step));
        }

        if (step == null) return new ArrayList<>();
        path.add(0, start);
        return path;
    }

    public int getMinDistanceIndex(List<Station> unvisited, List<Integer> distances) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < unvisited.size(); i++) {
            int idx = stations.indexOf(unvisited.get(i));
            int dist = distances.get(idx);
            if (dist < min) {
                min = dist;
                minIndex = i;
            }
        }

        return minIndex;
    }

    public Station getStationByName(String name) {
        for (Station s : stations) {
            if (s.getName().equalsIgnoreCase(name.trim())) return s;
        }
        return null;
    }

    public List<Station> getStations() {
        return this.stations;
    }

    public int getLastPathCost() {
        return lastPathCost;
    }

    /**
     * Visualizes the railway graph using the GraphStream library.
     * Stations are shown as nodes and lines as edges, with electrified lines colored red.
     */
    public void visualizeGraph() {
        if (stations == null || lines == null) {
            System.out.println("Cannot visualize: graph data not loaded.");
            return;
        }

        org.graphstream.graph.Graph visualGraph = new SingleGraph("Railway Network");
        System.setProperty("org.graphstream.ui", "swing");

        // Add stations as nodes
        for (Station station : stations) {
            String nodeId = station.getName();
            if (visualGraph.getNode(nodeId) == null) {
                Node node = visualGraph.addNode(nodeId);
                node.setAttribute("ui.label", nodeId);
            }
        }

        List<String> addedEdges = new ArrayList<>();
        int edgeId = 0;

        for (Line line : lines) {
            String from = line.getStart().getName();
            String to = line.getEnd().getName();

            // Create a unique edge key ignoring direction
            String edgeKey = (from.compareTo(to) < 0) ? from + "--" + to : to + "--" + from;

            // Add edge only if not already added
            if (!addedEdges.contains(edgeKey)) {
                String edgeName = "E" + edgeId++;
                Edge edge = visualGraph.addEdge(edgeName, from, to, false);
                String color = line.isElectrified() ? "red" : "black";
                edge.setAttribute("ui.style", "fill-color: " + color + ";");

                addedEdges.add(edgeKey);
            }
        }

        visualGraph.setAttribute("ui.stylesheet",
                "node { fill-color: gray; size: 20px; text-alignment: above; text-size: 14px; }");
        visualGraph.display();
    }


}

