package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.StationType;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationRepositoryTest {

    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationRepository = new StationRepository();
    }

    @Test
    void testLoadStations_validFile_success() throws IOException {
        Path tempFile = Files.createTempFile("stations", ".csv");
        Files.writeString(tempFile, "DDepot1;SStation2;TTerminal3");

        List<Station> stations = stationRepository.loadStations(tempFile.toString());

        assertEquals(3, stations.size());
        assertEquals("DDepot1", stations.get(0).getName());
        assertEquals(StationType.DEPOT, stations.get(0).getType());

        assertEquals("SStation2", stations.get(1).getName());
        assertEquals(StationType.STATION, stations.get(1).getType());

        assertEquals("TTerminal3", stations.get(2).getName());
        assertEquals(StationType.TERMINAL, stations.get(2).getType());

        Files.deleteIfExists(tempFile);
    }

    @Test
    void testLoadStations_invalidPrefix_throwsException() throws IOException {
        Path tempFile = Files.createTempFile("stations_invalid", ".csv");
        Files.writeString(tempFile, "XUnknown");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            stationRepository.loadStations(tempFile.toString());
        });

        assertTrue(exception.getMessage().contains("Invalid station prefix"));
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testLoadStations_fileNotFound_returnsEmptyList() {
        List<Station> stations = stationRepository.loadStations("nonexistent_file.csv");
        assertEquals(0, stations.size());
    }

    @Test
    void testAddStation_manualAdd() {
        Station station = new Station("SStationX", StationType.STATION);
        stationRepository.addStation(station);

        List<Station> stations = stationRepository.getStations();
        assertEquals(1, stations.size());
        assertEquals("SStationX", stations.get(0).getName());
        assertEquals(StationType.STATION, stations.get(0).getType());
    }
}
