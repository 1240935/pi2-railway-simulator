package pt.ipp.isep.dei.controller;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;

/**
 * Controller class responsible for loading station and line data,
 * building the railway graph, verifying train travel possibility,
 * and visualizing the graph.
 */
public class VerifyTrainTravelController {
    private String stationsFile;
    private String linesFile;
    private LocomotiveType locomotiveType;
    private StationType stationType;
    private List<Station> stations;
    private List<Line> lines;
    private GraphC graph;

    /**
     * Sets the file paths for stations and lines CSV files.
     *
     * @param stationsFile path to the stations CSV file
     * @param linesFile    path to the lines CSV file
     */
    public void setFiles(String stationsFile, String linesFile) {
        this.stationsFile = stationsFile;
        this.linesFile = linesFile;
    }

    /**
     * Sets the locomotive type to be considered for travel verification.
     *
     * @param type locomotive type (ELECTRIC, DIESEL, STEAM)
     */
    public void setType(LocomotiveType type) {
        this.locomotiveType = type;
    }

    /**
     * Sets the station type to target during travel verification.
     *
     * @param type station type (DEPOT, STATION, TERMINAL)
     */
    public void setStationType(StationType type) {
        this.stationType = type;
    }

    public void setLocomotiveTypeByOption(int option) {
        this.locomotiveType = switch (option) {
            case 1 -> LocomotiveType.ELECTRIC;
            case 2 -> LocomotiveType.DIESEL;
            case 3 -> LocomotiveType.STEAM;
            default -> throw new IllegalArgumentException("Invalid locomotive type option");
        };
    }

    public void setStationTypeByOption(int option) {
        this.stationType = switch (option) {
            case 1 -> StationType.DEPOT;
            case 2 -> StationType.STATION;
            case 3 -> StationType.TERMINAL;
            default -> throw new IllegalArgumentException("Invalid station type option");
        };
    }


    /**
     * Loads stations and lines from the given CSV files and builds the railway graph.
     *
     * @param stationFile path to the stations CSV file
     * @param lineFile    path to the lines CSV file
     * @return built GraphC object representing the railway network
     */
    public GraphC buildGraphFromFiles(String stationFile, String lineFile) {
        Repositories repos = Repositories.getInstance(stationFile, lineFile);
        this.stations = repos.getStationRepository().getStations();
        this.lines = repos.getLineRepository().getLines();

        this.graph = new GraphC();
        this.graph.buildGraph(this.stations, this.lines);
        return this.graph;
    }

    /**
     * Verifies if there is a valid travel path according to the selected locomotive and station types.
     *
     * @return true if travel is possible, false otherwise
     */
    public boolean verify() {
        if (graph == null) {
            System.out.println("Cannot verify: graph not built.");
            return false;
        }
        return new PathVerifier().verifyPath(graph, locomotiveType, stationType);
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
