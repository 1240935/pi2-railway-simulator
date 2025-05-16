package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.Line;
import pt.ipp.isep.dei.domain.Station;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineRepository {

    private final List<Line> lines = new ArrayList<>();

    /**
     * Loads lines from the given file and links them to the station objects from the provided list.
     *
     * @param linesFile     Path to the CSV file with line data.
     * @param stationList   List of station objects previously created.
     * @return The loaded list of Line objects.
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
                double distance = Double.parseDouble(parts[3].trim());

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

    /**
     * Finds a station object in the list based on its name.
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
     * Returns only the electrified lines from the given list.
     *
     * @param allLines List of all lines
     * @return List of electrified lines
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

    public List<Line> getLines() {
        return lines;
    }
}
