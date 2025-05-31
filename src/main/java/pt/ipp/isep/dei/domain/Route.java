package pt.ipp.isep.dei.domain;

import java.util.ArrayList;
import java.util.List;

public class Route {

    /**
     * The station where the route starts.
     */
    private Station startingStation;

    /**
     * The station where the route ends.
     */
    private Station endingStation;

    /**
     * List of intermediate stations (stops) between start and end.
     */
    private List<Station> stops;

    /**
     * The locomotive assigned to this route.
     */
    private Locomotive locomotive;

    /**
     * The list of cargo items to be transported on this route.
     */
    private List<Cargo> cargo;

    /**
     * Constructs a Route with specified starting station, ending station,
     * locomotive, and cargo list. Initializes the stops list as empty.
     *
     * @param startingStation the station where the route begins
     * @param endingStation the station where the route ends
     * @param locomotive the locomotive assigned to the route
     * @param cargo the cargo to be transported on the route
     */
    public Route(Station startingStation, Station endingStation, Locomotive locomotive, List<Cargo> cargo){
        this.startingStation = startingStation;
        this.endingStation = endingStation;
        this.locomotive = locomotive;
        this.cargo = cargo;
        this.stops = new ArrayList<>();
    }

    /**
     * Default constructor for Route.
     */
    public Route(){
    }

    /**
     * Returns the starting station of this route.
     *
     * @return the starting station
     */
    public Station getStartingStation() {
        return startingStation;
    }

    /**
     * Sets the starting station of this route.
     *
     * @param startingStation the station to set as start
     */
    public void setStartingStation(Station startingStation) {
        this.startingStation = startingStation;
    }

    /**
     * Returns the ending station of this route.
     *
     * @return the ending station
     */
    public Station getEndingStation() {
        return endingStation;
    }

    /**
     * Sets the ending station of this route.
     *
     * @param endingStation the station to set as end
     */
    public void setEndingStation(Station endingStation) {
        this.endingStation = endingStation;
    }

    /**
     * Returns the list of intermediate stops along this route.
     *
     * @return list of stop stations
     */
    public List<Station> getStops() {
        return stops;
    }

    /**
     * Sets the list of intermediate stops for this route.
     *
     * @param stops list of stations to set as stops
     */
    public void setStops(List<Station> stops) {
        this.stops = stops;
    }

    /**
     * Returns the locomotive assigned to this route.
     *
     * @return the locomotive
     */
    public Locomotive getLocomotive() {
        return locomotive;
    }

    /**
     * Sets the locomotive assigned to this route.
     *
     * @param locomotive the locomotive to assign
     */
    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    /**
     * Returns the list of cargo items transported on this route.
     *
     * @return list of cargo
     */
    public List<Cargo> getCargo() {
        return cargo;
    }

    /**
     * Sets the list of cargo items for this route.
     *
     * @param cargo list of cargo items to assign
     */
    public void setCargo(List<Cargo> cargo) {
        this.cargo = cargo;
    }

    /**
     * Returns a list of all stations along the route, including the
     * starting station, all intermediate stops, and the ending station.
     *
     * @return list of stations in order along the route
     */
    public List<Station> getRouteStations() {
        List<Station> stations = new ArrayList<>();
        stations.add(startingStation);
        if (stops != null) {
            stations.addAll(stops);
        }
        stations.add(endingStation);
        return stations;
    }

    /**
     * Returns a string representation of this route, including
     * the starting station, ending station, stops, locomotive, and cargo.
     *
     * @return string representation of the route
     */
    @Override
    public String toString() {
        if (stops!= null) {
            return "Route:" +
                    "\n  Starting Station: " + startingStation +
                    "\n  Ending Station: " + endingStation +
                    "\n  Stops Stations: " + stops +
                    "\n  Locomotive: " + locomotive +
                    "\n  Cargo: " + cargo;
        } else {
            return "Route:" +
                    "\n  Starting Station: " + startingStation +
                    "\n  Ending Station: " + endingStation +
                    "\n  Locomotive: " + locomotive +
                    "\n  Cargo: " + cargo;
        }
    }

    /**
     * Finds possible starting stations for a maintenance route (Eulerian path)
     * based on the current railway graph.
     * <p>
     * An Eulerian path exists if:
     * <ul>
     *   <li>All stations have even degree (Eulerian circuit: can start anywhere), OR</li>
     *   <li>Exactly two stations have odd degree (Eulerian path: must start at one of them)</li>
     * </ul>
     *
     * @param graph the railway graph containing stations and lines
     * @return a list of possible starting stations if an Eulerian path or circuit is possible;
     *         null if no such path exists
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
            for (Line line : lines) {
                if (line.getStart().equals(current) || line.getEnd().equals(current)) {
                    degree++;
                }
            }

            // If the number of connections (degree) is odd, addScenario to list
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
