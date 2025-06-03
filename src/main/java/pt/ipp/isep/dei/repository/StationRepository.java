package pt.ipp.isep.dei.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.StationType;

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

    public List<Station> loadStationsInt(String stationsFile) {
        stations.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(stationsFile))) {
            String line = br.readLine();
            if (line != null) {
                String[] stationNames = line.split(",");
                for (String name : stationNames) {
                    name = name.trim(); // e.g., "S10"
                    int stationNumber = Integer.parseInt(name.substring(1)); // e.g., 10
                    // Optionally use stationNumber for something
                    StationType type = getStationTypeFromName(name); // or from stationNumber if needed
                    stations.add(new Station(name, type));
                }
            }
        } catch (IOException | NumberFormatException e) {
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




}
