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
 */
public class StationRepository {

    /** Internal list of loaded stations. */
    private List<Station> stations = new ArrayList<>();

    /**
     * Loads station data from the specified CSV file.
     * The file is expected to contain one line with station names separated by semicolons.
     * The station type is inferred from the first character of the name:
     * <ul>
     *   <li>'D' → DEPOT</li>
     *   <li>'S' → STATION</li>
     *   <li>'T' → TERMINAL</li>
     * </ul>
     *
     * @param stationsFile the path to the CSV file containing station names
     * @return the list of loaded {@link Station} objects
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
     * Determines the {@link StationType} based on the first character of the station name.
     *
     * @param name the name of the station
     * @return the corresponding {@link StationType}
     * @throws IllegalArgumentException if the prefix is not recognized
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
     * Returns the list of stations loaded from the last call to {@link #loadStations(String)}.
     *
     * @return the list of {@link Station} objects
     */
    public List<Station> getStations() {
        return stations;
    }
}
