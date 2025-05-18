package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private Station startingStation;
    private Station endingStation;
    private List<Station> stops;
    private Locomotive locomotive;
    private List<Cargo> cargo;

    public Route(Station startingStation, Station endingStation, Locomotive locomotive, List<Cargo> cargo){
        this.startingStation = startingStation;
        this.endingStation = endingStation;
        this.locomotive = locomotive;
        this.cargo = cargo;
    }

    public Route(Station startingStation, Station endingStation, List<Station> stops, Locomotive locomotive, List<Cargo> cargo){
        this.startingStation = startingStation;
        this.endingStation = endingStation;
        this.stops = stops;
        this.cargo = cargo;
        this.locomotive = locomotive;
    }




    /**
     * Finds possible starting stations for a maintenance route (Eulerian path)
     * based on the current railway graph.
     * <p>
     * An Eulerian path exists if:
     * - All stations have even degree (Eulerian circuit: start anywhere), OR
     * - Exactly two stations have odd degree (Eulerian path: must start at one of them)
     *
     * @param graph the railway graph containing stations and lines
     * @return a list of possible starting stations if a path is possible;
     * null if no such path exists
     */
    public List<Station> findEulerianPath(GraphC graph) {
        List<Station> stations = graph.getStations();
        List<Line> lines = graph.getLines();

        // List to store stations with an odd number of connected lines
        List<Station> oddDegreeStations = new ArrayList<>();

        // For each station, count how many lines are connected to it
        for (Station current : stations) {
            int degree = 0;

            // Count lines connected to the current station
            for (int j = 0; j < lines.size(); j++) {
                Line line = lines.get(j);
                if (line.getStart().equals(current) || line.getEnd().equals(current)) {
                    degree++;
                }
            }

            // If the number of connections (degree) is odd, add to list
            if (degree % 2 != 0) {
                oddDegreeStations.add(current);
            }
        }

        // Determine possible starting stations based on the number of odd-degree nodes
        if (oddDegreeStations.isEmpty()) {
            // Eulerian circuit: any station can be a start
            return stations;
        } else if (oddDegreeStations.size() == 2) {
            // Eulerian path: must start at one of the odd-degree stations
            return oddDegreeStations;
        } else {
            // No Eulerian path is possible
            return null;
        }

    }
}
