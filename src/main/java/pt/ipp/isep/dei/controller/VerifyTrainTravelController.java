package pt.ipp.isep.dei.controller;

import java.util.List;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.Repositories;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;

public class VerifyTrainTravelController {
    private String stationsFile;
    private String linesFile;
    private LocomotiveType locomotiveType;
    private StationType stationType;
    private List<Station> stations;
    private List<Line> lines;
    private GraphC graph;

    public void setFiles(String stationsFile, String linesFile) {
        this.stationsFile = stationsFile;
        this.linesFile = linesFile;
    }

    public void setType(LocomotiveType type) {
        this.locomotiveType = type;
    }

    public void setStationType(StationType type) {
        this.stationType = type;
    }

    public GraphC buildGraphFromFiles(String stationFile, String lineFile) {
        Repositories repos = Repositories.getInstance(stationFile, lineFile);
        this.stations = repos.getStationRepository().getStations();
        this.lines = repos.getLineRepository().getLines();

        this.graph = new GraphC();
        this.graph.buildGraph(this.stations, this.lines);
        return this.graph;
    }

    public boolean verify() {
        if (graph == null) {
            System.out.println("Cannot verify: graph not built.");
            return false;
        }
        return new PathVerifier().verifyPath(graph, locomotiveType, stationType);
    }


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
            Node node = visualGraph.addNode(nodeId);
            node.setAttribute("ui.label", nodeId);
        }

        // Add lines as edges
        int edgeId = 0;
        for (Line line : lines) {
            String from = line.getStart().getName();
            String to = line.getEnd().getName();

            if (visualGraph.getNode(from) == null || visualGraph.getNode(to) == null) {
                continue;
            }

            String edgeName = "E" + edgeId++;
            Edge edge = visualGraph.addEdge(edgeName, from, to, true);
            String color = line.isElectrified() ? "red" : "black";
            edge.setAttribute("ui.style", "fill-color: " + color + ";");
        }

        visualGraph.setAttribute("ui.stylesheet",
                "node { fill-color: gray; size: 20px; text-alignment: above; text-size: 14px; }");
        visualGraph.display();
    }
}
