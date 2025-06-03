package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Line;
import pt.ipp.isep.dei.domain.Station;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for loading and managing {@link Line} objects.
 * Provides functionality to load lines from a CSV file, retrieve all lines,
 * and filter electrified lines.
 */
public class LineRepository {

    /** Internal list that stores the loaded lines. */
    private final List<Line> lines = new ArrayList<>();

    /**
     * Loads railway lines from a CSV file and associates them with station objects.
     * Each line in the file must contain: start station name, end station name,
     * electrification status (1 for electrified, else non-electrified), and distance.
     *
     * @param linesFile   the path to the CSV file containing line data
     * @param stationList the list of pre-loaded stations to associate with lines
     * @return the list of loaded {@link Line} objects
     */
    public List<Line> loadLines(String linesFile, List<Station> stationList) {
        lines.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(linesFile))) {
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                String[] parts = lineStr.split(";");
                if (parts.length < 4) continue;

                String startName = parts[0].trim();
                String endName = parts[1].trim();
                boolean electrified = parts[2].trim().equals("1");
                int distance = Integer.parseInt(parts[3].trim());

                Station startStation = findStationByName(stationList, startName);
                Station endStation = findStationByName(stationList, endName);

                if (startStation != null && endStation != null) {
                    lines.add(new Line(startStation, endStation, electrified, distance));
                } else {
                    System.err.printf("Could not find stations for line: %s -> %s%n", startName, endName);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading lines file: " + e.getMessage());
        }

        return lines;
    }


    public List<Line> loadLinesInt(String linesFile, List<Station> stationList) {
        lines.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(linesFile))) {
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                String[] parts = lineStr.split(";");
                if (parts.length < 4) continue;

                String startRaw = parts[0].trim(); // e.g., "S10"
                String endRaw = parts[1].trim();   // e.g., "S25"

                // Extract numeric part just for processing if needed
                int startNumber = Integer.parseInt(startRaw.substring(1));
                int endNumber = Integer.parseInt(endRaw.substring(1));

                // Use full names to find the stations
                Station startStation = findStationByName(stationList, startRaw);
                Station endStation = findStationByName(stationList, endRaw);

                boolean electrified = parts[2].trim().equals("1");
                int distance = Integer.parseInt(parts[3].trim());

                if (startStation != null && endStation != null) {
                    lines.add(new Line(startStation, endStation, electrified, distance));
                } else {
                    System.err.printf("Could not find stations for line: %s -> %s%n", startRaw, endRaw);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading lines file: " + e.getMessage());
        }

        return lines;
    }


    /**
     * Searches for a station in a list by its name, ignoring case.
     *
     * @param stations the list of stations to search
     * @param name     the name of the station to find
     * @return the {@link Station} if found; otherwise, {@code null}
     */
    private Station findStationByName(List<Station> stations, String name) {
        for (Station s : stations) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Filters and returns only the electrified lines from a given list.
     *
     * @param allLines the list of lines to filter
     * @return a list containing only electrified {@link Line} objects
     */
    public List<Line> getElectrifiedLines(List<Line> allLines) {
        List<Line> electrified = new ArrayList<>();
        for (Line line : allLines) {
            if (line.isElectrified()) {
                electrified.add(line);
            }
        }
        return electrified;
    }

    /**
     * Returns the internal list of loaded lines.
     *
     * @return the list of {@link Line} objects stored in the repository
     */
    public List<Line> getLines() {
        return lines;
    }
}
