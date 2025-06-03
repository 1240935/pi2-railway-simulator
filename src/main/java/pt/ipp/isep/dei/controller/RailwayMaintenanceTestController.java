package pt.ipp.isep.dei.controller;

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
public class RailwayMaintenanceTestController {

    private List<Station> stations;
    private List<Line> lines;
    private MaintenanceType maintenanceType;
    private StationRepository stationRepo;
    private LineRepository lineRepo;
    private GraphC graph;
    private String stationsFile;
    private String linesFile;
    private Station selectedStartStation;


    public RailwayMaintenanceTestController() {
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
        this.stations = stationRepo.loadStationsInt(stationsFile);
        this.lines = lineRepo.loadLinesInt(linesFile, stations);
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
}

