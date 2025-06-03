package pt.ipp.isep.dei.controller;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.*;
import pt.ipp.isep.dei.repository.LineRepository;
import pt.ipp.isep.dei.repository.Repositories;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import pt.ipp.isep.dei.repository.StationRepository;

/**
 * Controller class responsible for loading station and line data,
 * building the railway graph, verifying train travel possibility,
 * and visualizing the graph.
 */
public class VerifyTrainTravelTestController {
    private String stationsFile;
    private String linesFile;
    private LocomotiveType locomotiveType;
    private StationType stationType;
    private List<Station> stations;
    private List<Line> lines;
    private StationRepository stationRepo;
    private LineRepository lineRepo;
    private GraphC graph;

    public VerifyTrainTravelTestController() {
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

    public void loadData() {
        // Load stations and lines from files using repositories
        this.stations = stationRepo.loadStationsInt(stationsFile);
        this.lines = lineRepo.loadLinesInt(linesFile, stations);
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
}

