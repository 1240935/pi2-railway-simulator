package pt.ipp.isep.dei.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.*;

/**
 * Repository responsible for loading and storing {@link Station} objects from a CSV file.
 * <p>
 * Stations are expected to be defined in a single CSV line with station names separated by semicolons.
 * The type of a station is inferred from the first character of its name:
 * <ul>
 *   <li>'D' for DEPOT</li>
 *   <li>'S' for STATION</li>
 *   <li>'T' for TERMINAL</li>
 * </ul>
 * </p>
 */
public class StationRepository {

    /** Internal list that holds the loaded stations. */
    private List<Station> stations = new ArrayList<>();

    /**
     * Loads station data from the specified CSV file.
     * The file should contain exactly one line with station names separated by semicolons.
     * For each station name, the station type is inferred by the first character.
     *
     * @param stationsFile the path to the CSV file containing the station names
     * @return a {@link List} of loaded {@link Station} objects
     */
    public List<Station> loadStations(String stationsFile) {
        stations.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(stationsFile))) {
            String line = br.readLine();
            if (line != null) {
                String[] stationNames = line.split(";");
                for (String name : stationNames) {
                    StationType type = getStationTypeFromName(name);
                    stations.add(new Station(name, type));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }

    /**
     * Determines the {@link StationType} based on the first character of the given station name.
     * <p>
     * The mapping is as follows:
     * <ul>
     *   <li>'D' → DEPOT</li>
     *   <li>'S' → STATION</li>
     *   <li>'T' → TERMINAL</li>
     * </ul>
     * </p>
     *
     * @param name the name of the station
     * @return the inferred {@link StationType}
     * @throws IllegalArgumentException if the name prefix does not correspond to a known type
     */
    private StationType getStationTypeFromName(String name) {
        char prefix = name.charAt(0);
        return switch (prefix) {
            case 'D' -> StationType.DEPOT;
            case 'S' -> StationType.STATION;
            case 'T' -> StationType.TERMINAL;
            default -> throw new IllegalArgumentException("Invalid station prefix: " + name);
        };
    }

    /**
     * Returns the list of stations currently stored in the repository.
     * This list reflects the last successful load from a CSV file or any manually added stations.
     *
     * @return the list of {@link Station} objects
     */
    public List<Station> getStations() {
        return stations;
    }

    /**
     * Adds a new {@link Station} to the repository's internal list.
     *
     * @param station the {@link Station} object to addScenario
     */
    public void addStation(Station station) {
        stations.add(station);
    }

    /**
     * Creates a new {@link Station} with the provided parameters after validating them.
     * If any validation fails, prints an error message and returns null.
     * If creation succeeds, the station is added to the repository.
     *
     * @param position the position of the station
     * @param name the name of the station
     * @param center the station center
     * @param type the station type
     * @param demandedCargoes list of demanded cargoes
     * @param suppliedCargoes list of supplied cargoes
     * @param map the map where the station is located
     * @param scenario the scenario context
     * @return the created {@link Station} or null if creation failed
     */
    public static Station createStation(Position position, String name, StationCenter center,
                                        StationType type, List<Cargo> demandedCargoes,
                                        List<Cargo> suppliedCargoes, Map map, Scenario scenario) {

        if (!position.checkPosition(position, map.getSize())) {
            System.out.println("Invalid position.");
            return null;
        }

        if (!Station.checkOverbuilding(position, map, scenario)) {
            System.out.println("Cannot build here. Overbuilding detected.");
            return null;
        }

        if (!Station.checkName(name)) {
            System.out.println("Invalid name.");
            return null;
        }

        if (!Station.checkCenter(center)) {
            System.out.println("Center is out of bounds.");
            return null;
        }

        if (!Station.checkType(type)) {
            System.out.println("Invalid station type.");
            return null;
        }

        Station station = new Station(name, center, position, type, demandedCargoes, suppliedCargoes);
        System.out.println("Station created successfully!");
        Repositories.getInstance().getStationRepository().addStation(station);
        return station;
    }
}
