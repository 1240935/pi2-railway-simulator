package pt.ipp.isep.dei.controller;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.LineRepository;
import pt.ipp.isep.dei.repository.Repositories;
import pt.ipp.isep.dei.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the Railway Line Maintenance feature.
 * Allows the player to select maintenance type and view possible starting stations
 * for a route that passes through each railway line exactly once.
 */
public class RailwayMaintenanceController {

    private List<Station> stations;
    private List<Line> lines;
    private MaintenanceType maintenanceType;
    private StationRepository stationRepo;
    private LineRepository lineRepo;
    private GraphC graph;
    private String stationsFile;
    private String linesFile;
    private Station selectedStartStation;


    public RailwayMaintenanceController() {
        this.stationRepo = Repositories.getInstance().getStationRepository();
        this.lineRepo = Repositories.getInstance().getLineRepository();
        this.graph = new GraphC();
    }

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
     * Sets the type of maintenance: Full (all lines) or Electrified.
     *
     * @param type the maintenance type
     */
    public void setMaintenanceType(MaintenanceType type) {
        this.maintenanceType = type;
    }

    public void loadData() {
        // Load stations and lines from files using repositories
        this.stations = stationRepo.loadStations(stationsFile);
        this.lines = lineRepo.loadLines(linesFile, stations);
    }

    /**
     * Attempts to find valid starting stations for a maintenance route.
     * If no such route is possible, return an empty list.
     *
     * @return list of valid starting stations or empty list if not possible
     */
    public List<Station> getPossibleStartingStations() {
        List<Line> relevantLines;

        // Choose lines based on a maintenance type
        if (maintenanceType == MaintenanceType.FULL) {
            relevantLines = lines;
        } else {
            relevantLines = lineRepo.getElectrifiedLines(lines);
        }

        // Rebuild the graph with selected stations and lines
        graph.buildGraph(stations, relevantLines);

        // Find an Eulerian path using Route class
        Route routeFinder = new Route();
        List<Station> possibleStarts = routeFinder.findEulerianPath(graph);

        if (possibleStarts == null) {
            return new ArrayList<>(); // return an empty list as warning signal
        }
        return possibleStarts;

    }

    /**
     * Returns the names of possible starting stations for the maintenance route.
     * If no valid route exists, returns an empty list.
     *
     * @return list of station names
     */
    public List<String> getPossibleStartingStationNames() {
        List<Station> possibleStations = getPossibleStartingStations();
        List<String> stationNames = new ArrayList<>();

        for (Station s : possibleStations) {
            stationNames.add(s.getName());
        }

        return stationNames;
    }

    /**
     * Sets the selected starting station if it's among the valid options.
     *
     * @param stationName name of the selected station
     * @return true if selection is valid and stored; false otherwise
     */
    public boolean setSelectedStartStation(String stationName) {
        List<Station> possible = getPossibleStartingStations();

        for (Station s : possible) {
            if (s.getName().equalsIgnoreCase(stationName.trim())) {
                this.selectedStartStation = s;
                return true;
            }
        }

        return false; // Invalid choice
    }

    /**
     * Gets the selected start station (may be null if not set).
     *
     * @return selected Station object or null
     */
    public Station getSelectedStartStation() {
        return selectedStartStation;
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

