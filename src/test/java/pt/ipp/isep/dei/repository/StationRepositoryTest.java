package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Station;
import pt.ipp.isep.dei.domain.StationType;

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

    /**
     * Testa se o carregamento de um ficheiro CSV válido funciona corretamente.
     * Verifica se as estações e os tipos são atribuídos como esperado.
     */
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

    /**
     * Testa se um prefixo inválido numa estação lança uma exceção.
     */
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

    /**
     * Testa o comportamento quando o ficheiro fornecido não existe.
     * Deve retornar uma lista vazia e não lançar exceções.
     */
    @Test
    void testLoadStations_fileNotFound_returnsEmptyList() {
        List<Station> stations = stationRepository.loadStations("nonexistent_file.csv");
        assertEquals(0, stations.size());
    }

    /**
     * Testa se adicionar uma estação manualmente funciona corretamente.
     */
    @Test
    void testAddStation_manualAdd() {
        Station station = new Station("SStationX", StationType.STATION);
        stationRepository.addStation(station);

        List<Station> stations = stationRepository.getStations();
        assertEquals(1, stations.size());
        assertEquals("SStationX", stations.get(0).getName());
        assertEquals(StationType.STATION, stations.get(0).getType());
    }

    /**
     * Testa se o carregamento falha quando há nomes vazios ou com espaços.
     * Espera-se uma exceção devido à entrada inválida.
     */
    @Test
    void testLoadStations_ignoresEmptyNamesAndTrimsSpaces() throws IOException {
        Path tempFile = Files.createTempFile("stations_spaces", ".csv");
        Files.writeString(tempFile, "  DDepot1  ;   ; SStation2 ;TTerminal3 ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            stationRepository.loadStations(tempFile.toString());
        });

        assertTrue(exception.getMessage().contains("Invalid station prefix"));
        Files.deleteIfExists(tempFile);
    }

    /**
     * Testa se o método loadStations limpa a lista anterior de estações.
     * A nova estação deve substituir as anteriores.
     */
    @Test
    void testLoadStations_overwritesPreviousData() throws IOException {
        Station station = new Station("SInitial", StationType.STATION);
        stationRepository.addStation(station);
        assertEquals(1, stationRepository.getStations().size());

        Path tempFile = Files.createTempFile("stations_overwrite", ".csv");
        Files.writeString(tempFile, "DDepotNew");

        List<Station> loaded = stationRepository.loadStations(tempFile.toString());

        assertEquals(1, loaded.size(), "Lista deve ser sobrescrita");
        assertEquals("DDepotNew", loaded.get(0).getName());
        Files.deleteIfExists(tempFile);
    }

    /**
     * Testa se o método getStations() retorna uma referência direta à lista interna.
     * Ao modificar a lista externa, a interna também deve ser afetada.
     */
    @Test
    void testGetStations_returnsInternalListReference() {
        Station station = new Station("TTerminalX", StationType.TERMINAL);
        stationRepository.addStation(station);

        List<Station> retrieved = stationRepository.getStations();
        retrieved.clear();

        assertEquals(0, stationRepository.getStations().size(), "getStations() retorna a referência direta");
    }
}
