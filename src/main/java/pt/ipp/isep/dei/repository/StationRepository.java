package pt.ipp.isep.dei.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.StationType;

public class StationRepository {


    private List<Station> stations = new ArrayList<>();

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

    private StationType getStationTypeFromName(String name) {
        char prefix = name.charAt(0);
        return switch (prefix) {
            case 'D' -> StationType.DEPOT;
            case 'S' -> StationType.STATION;
            case 'T' -> StationType.TERMINAL;
            default -> throw new IllegalArgumentException("Invalid station prefix: " + name);
        };
    }

    public List<Station> getStations() {
        return stations;
    }
}
